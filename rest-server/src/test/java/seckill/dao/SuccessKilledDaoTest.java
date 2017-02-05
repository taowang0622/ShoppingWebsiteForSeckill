package seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import seckill.entity.Product;
import seckill.entity.SuccessKilled;

/**
 * Created by taowang on 1/18/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context-dao.xml")
public class SuccessKilledDaoTest {
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long productId = 1000L;
        long userPhone = 6552205644L;
        System.out.println(successKilledDao.insertSuccessKilled(productId, userPhone));
    }

    @Test
    public void querySuccessKilled() throws Exception {
        long productId = 1000L;
        long userPhone = 6552205644L;

        SuccessKilled successKilled = successKilledDao.querySuccessKilled(productId, userPhone);
        System.out.println(successKilled);

        Product product = successKilled.getProduct();
        System.out.println(product);
    }

}