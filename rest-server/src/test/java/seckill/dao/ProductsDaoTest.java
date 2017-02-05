package seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import seckill.entity.Product;

import java.util.Date;
import java.util.List;

/**
 * Created by taowang on 1/17/2017.
 */

/**
 * integration of JUnit4 and Spring in order to load Spring container when JUnit4 runs
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context-dao.xml")
public class ProductsDaoTest {
    @Autowired
    private ProductsDao productsDao;

    @Test
    public void reduceNum() throws Exception {
        long productId = 1000;
        Date date = new Date();
        System.out.println(productsDao.reduceNum(productId, date));
    }

    @Test
    public void queryProduct() throws Exception {
        for (long productId = 1000; productId < 1006; productId++) {
            System.out.println(productsDao.queryProduct(productId));
        }
    }

    @Test
    public void queryAll() throws Exception {
        List<Product> products = productsDao.queryAll(1, 10);
        for (Product product : products) {
            System.out.println(product);
        }
    }

}