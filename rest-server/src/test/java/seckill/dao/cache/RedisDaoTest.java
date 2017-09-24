package seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import seckill.dao.ProductsDao;
import seckill.entity.Product;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/application-context-dao.xml"})
public class RedisDaoTest {
    private final long id = 1000;

    @Autowired
    RedisDao redisDao;

    @Autowired
    ProductsDao productsDao;

//    @Test
//    public void getProduct() throws Exception {
//    }
//
//    @Test
//    public void putProduct() throws Exception {
//    }

    @Test
    public void testProduct() throws Exception{
        //get and put
        Product product = redisDao.getProduct(id);
        if (product == null) {
            product = productsDao.queryProduct(id);
            if (product != null) {
                String result = redisDao.putProduct(product);
                System.out.println(result);
                product = redisDao.getProduct(id);
                System.out.println(product);
            }
        }
    }

}