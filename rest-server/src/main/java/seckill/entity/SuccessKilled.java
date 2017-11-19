package seckill.entity;

import java.util.Date;

/**
 * Created by taowang on 1/16/2017.
 */
public class SuccessKilled {
    private Long productId;
    private Long userPhone;
    private Byte state;
    private Date createTime;
    private Product product; //reference to the seckilled product object!!!!

    public Product getProduct() {
        return product;
    }
    

    ////Getters for serializing the SuccessKilled objects to JSON format!!!
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "productId=" + productId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
