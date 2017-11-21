package seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import seckill.entity.Product;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductsDao {
    /**
     * reduce # of the product specified by id
     * @param productId
     * @return the number of lines affected, if it's 0, that means this operation fails
     */
    int reduceNum(@Param("productId") long productId, @Param("killTime") Date killTime);

    /**
     *
     * @param productId
     * @return
     */
    Product queryProduct(long productId);

    /**
     * find a list of products according to passed offset and limit
     * @param offset
     * @param limit
     * @return
     */
    List<Product> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * Execute seckill by the stored procedure!!
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);
}
