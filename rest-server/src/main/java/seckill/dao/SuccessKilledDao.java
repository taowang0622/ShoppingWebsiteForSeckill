package seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import seckill.entity.SuccessKilled;

/**
 * Created by taowang on 1/16/2017.
 */
public interface SuccessKilledDao {
    /**
     * This can prevent the user from purchase the same item more than once
     * @param productId
     * @param userPhone
     * @return # of lines affected, and if it's 0, that means the insertion fails
     */
    int insertSuccessKilled(@Param("productId") long productId, @Param("userPhone") long userPhone); //productId + userPhone is the primary key

    /**
     * get the successkilled record according to the primary key "productId + userPhone"
     * @param productId
     * @param userPhone
     * @return
     */
    SuccessKilled querySuccessKilled(@Param("productId") long productId, @Param("userPhone") long userPhone);
}
