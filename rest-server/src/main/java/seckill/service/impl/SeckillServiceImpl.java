package seckill.service.impl;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seckill.dao.ProductsDao;
import seckill.dao.SuccessKilledDao;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.entity.Product;
import seckill.entity.SuccessKilled;
import seckill.enums.SeckillStateEnum;
import seckill.exception.DataModifiedException;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillClosedException;
import seckill.exception.SeckillException;
import seckill.service.SeckillService;
import seckill.util.Md5;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass()); //For logging!!!!

    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private ProductsDao productsDao;

    private String salt = "semnjco87@E#df*&(*nsdsdfd"; //Secret for md5 hashing function

    public List<Product> getProductList() {
        return productsDao.queryAll(0, 10);
    }

    public Product getProductById(long productId) {
        return productsDao.queryProduct(productId);
    }

    public Exposer exposeSeckillUrl(long productId) {
        Product product = productsDao.queryProduct(productId);

        //The queried product does not exist!
        if (product == null) {
            return new Exposer(false, productId);
        }

        Date startTime = product.getStartTime();
        Date endTime = product.getEndTime();
        Date currentTime = new Date();

        //Seckill has not started yet
        //If seckill has not started yet, returning the start time, end time and current time to client side for calibrating!!!!
        if (startTime.getTime() > currentTime.getTime() || currentTime.getTime() > endTime.getTime()) {
            return new Exposer(false, startTime.getTime(), endTime.getTime(), currentTime.getTime(), productId);
        }

        //Seckill has started
        //If seckill has started, no need to return the start time, end time and current time to the client side!!!!
        String md5 = Md5.getMd5String(String.valueOf(productId), salt);
        return new Exposer(true, productId, md5);
    }

    @Transactional
    public SeckillExecution executeSeckill(long productId, long userPhone, String md5) throws SeckillException, RepeatSeckillException, SeckillClosedException {
        if (md5 != null && !md5.equals(Md5.getMd5String(String.valueOf(productId), salt))) {
            throw new DataModifiedException("Data Modified");
        }

        //While accessing data base, some issues, like connection timeout, disconnection, may occur.
        //At that time, this method as a single transaction must rollback!!!
        try {
            Date currentTime = new Date();

            int numOfInsertedRows = successKilledDao.insertSuccessKilled(productId, userPhone); //"INSERT" ahead of "UPDATE" to reduce the time of holding the lock
            if (numOfInsertedRows <= 0) {
                //For rolling back as expected, It's the programmer's responsibility to throw an exception when unwanted thing happens!!
                throw new RepeatSeckillException("Repeated seckill!");
            } else {
                int numOfUpddatedRows = productsDao.reduceNum(productId, currentTime); //"UPDATE" that will add an exclusive row-level lock
                if (numOfUpddatedRows <= 0) {
                    //For rolling back as expected, It's the programmer's responsibility to throw an exception when unwanted thing happens!!
                    throw new SeckillClosedException("Seckill has been closed!");
                } else {
                    //Seckill successful!!!
                    //commit this transaction!!
                    SuccessKilled successKilled = successKilledDao.querySuccessKilled(productId, userPhone); //"SELECT..WHERE.." is a nonlocking read
                    return new SeckillExecution(productId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillClosedException e1) {
            throw e1;
        } catch (RepeatSeckillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            //TRICK: Doing so will ensure all exceptions including compile time exceptions/checked exceptions
            //will be converted into runtime exceptions, and this means whenever and whichever kind of exception occurs,
            //this method as a transaction will rollback!!!!!!!
            throw new SeckillException("Seckill inner error: " + e.getMessage());
        }
    }

    public SeckillExecution executeSeckillProcedure(long productId, long phoneNum, String md5) {
        if (md5 != null && !md5.equals(Md5.getMd5String(String.valueOf(productId), salt))) {
            return new SeckillExecution(SeckillStateEnum.DATA_MODIFIED);
        }

        Date killTime = new Date();

        //The type of value is Object that enable keys to be mapped to values with different types
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productId", productId);
        paramMap.put("phoneNum", phoneNum);
        paramMap.put("killTime", killTime);
        paramMap.put("result", null);
        //The reason of using HashMap is because after the execution of the procedure is done, I can get the value of the result
        try {
            productsDao.killByProcedure(paramMap);
            //Obtain result
            //            int result = (Integer) paramMap.get("result");
            int result = MapUtils.getInteger(paramMap, "result", -2); //Using MapUtils to convert is more efficient than the above!!
            if (result == 1) {
                SuccessKilled sk = successKilledDao.querySuccessKilled(productId, phoneNum);
                return new SeckillExecution(productId, SeckillStateEnum.SUCCESS, sk);
            } else {
                return new SeckillExecution(SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            //inner server error!!!
            return new SeckillExecution(SeckillStateEnum.INNER_ERROR);
        }
    }

//    public static void main(String[] args) {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("str", "hello world");
//        map.put("int", 100);
//        System.out.println(map.get("str"));
//        System.out.println(map.get("int"));
//    }
}
