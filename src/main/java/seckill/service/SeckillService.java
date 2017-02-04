package seckill.service;

/**
 * Created by taowang on 1/21/2017.
 */

import org.springframework.stereotype.Service;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.entity.Product;
import seckill.entity.SuccessKilled;
import seckill.exception.DataModifiedException;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillClosedException;
import seckill.exception.SeckillException;

import java.util.List;

/**
 * Business Interface==>design from the client's perspective
 * Three points to note:
 * 1.methods should be specific, no need to care about how to implement them right now
 * 2.parameters should be simple and straightforward, like trying not to use map
 * 3.The type of the return value should be user-friendly, and can return allowed exceptions
 */
public interface SeckillService {
    /**
     * Get the list 0f all seckill products
     * @return
     */
    List<Product> getProductList();

    Product getProductById(long productId);

    /**
     * Returning URL when the specified seckill starts, otherwise returning current server time and seckill start time
     * @param productId
     * @return
     */
    Exposer exposeSeckillUrl(long productId);

    /**
     *
     * @param seckillId
     * @param phoneNum
     * @param md5
     * @return returning exceptions is because this operation could fail!!!!!
     * @throws SeckillException
     * @throws RepeatSeckillException
     * @throws SeckillClosedException
     */
    SeckillExecution executeSeckill(long seckillId, long phoneNum, String md5)
            throws SeckillException, RepeatSeckillException, SeckillClosedException, DataModifiedException;
}
