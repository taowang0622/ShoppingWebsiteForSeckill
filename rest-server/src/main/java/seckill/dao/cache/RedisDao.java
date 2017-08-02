package seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import seckill.entity.Product;

public class RedisDao {
    //logging!!!!
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    //A schema for protostuff to deserialize the byte stream to Product object!!!!
    private RuntimeSchema<Product> schema = RuntimeSchema.createFrom(Product.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    /****************The behavior is pretty like hash table's*********************/

    public Product getProduct(long productId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "product:" + productId;
                //"Serializing object" means to convert an object's state into a byte array/stream!!!
                //key.getBytes() returns a byte array===>In this case, using standard JAVA object serialization!!
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //"Deserializing object" means to convert a byte array/stream to an object!!!!
                    //In this case, we're gonna use protostuff to do that instead of standard JAVA way!!!
                    //protostiff is for POJO, getters and setters necessary for applying the specified SCHEMA!!!
                    //protostuff is much faster than standard way!!
                    //for serialization, the resulting byte stream by protostuff is much smaller than that by standard way!!
                    Product product = schema.newMessage();  //At first creating an empty Product object!!!
                    ProtostuffIOUtil.mergeFrom(bytes, product, schema);
                    return product;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putProduct(Product product) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "product:" + product.getId();
                //serializing the Product object to the byte stream!!
                byte[] bytes = ProtostuffIOUtil.toByteArray(product, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //time-out caching
                int timeout = 60 * 60; //in seconds, so it's one hour!!
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
