package seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.entity.Product;
import seckill.enums.SeckillStateEnum;
import seckill.exception.DataModifiedException;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillClosedException;
import seckill.exception.SeckillException;
import seckill.service.SeckillService;

import java.util.List;


/**
 * Created by taowang on 1/21/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/application-context-dao.xml", "classpath:spring/application-context-service.xml"})
public class SeckillServiceImplTest {

    @Autowired
    SeckillService seckillService;

    @Test
    public void getProductList() throws Exception {
        List<Product> productList = seckillService.getProductList();
        System.out.println(productList);
    }

    @Test
    public void getProductById() throws Exception {
        Product product = seckillService.getProductById(1001);
        System.out.println(product);
    }

    /**
     * integration test
     */
    @Test
    public void seckillLogic(){
//        long productId = 1000L;
//        Long userPhone = 12345567L;
//        Exposer exposer = seckillService.exposeSeckillUrl(productId);
//        System.out.println("MD5: " + exposer.getMd5());
//        SeckillExecution execution;
//        try {
//            execution = seckillService.executeSeckill(exposer.getProductId(), userPhone, exposer.getMd5());
//        } catch (RepeatSeckillException e1) {
//            execution = new SeckillExecution(SeckillStateEnum.REPEATED_SECKILL);
//        } catch (SeckillClosedException e2) {
//            execution = new SeckillExecution(SeckillStateEnum.END);
//        } catch (DataModifiedException e3) {
//            execution = new SeckillExecution(SeckillStateEnum.DATA_MODIFIED);
//        } catch (SeckillException e4) {
//            execution = new SeckillExecution(SeckillStateEnum.INNER_ERROR);
//            System.out.println(e4.getMessage());
//        }
//
//        System.out.println(execution);
    }

    @Test
    public void exposeSeckillUrl() throws Exception {

    }

    @Test
    public void executeSeckill() throws Exception {

    }

    @Test
    public void executeSeckillProcedure() throws Exception {
        long productId = 1001L;
        long phoneNum = 64755065233L;
        Exposer exposer = seckillService.exposeSeckillUrl(1001);
        if (exposer.isStarted()) {
            String md5 = exposer.getMd5();
            SeckillExecution exe = seckillService.executeSeckillProcedure(productId, phoneNum, md5);
            System.out.println(exe.getStateInfo());
        }
    }

}