package seckill.entity;

import java.util.Date;

/**
 * Created by taowang on 1/15/2017.
 */
public class Product {
    private Long id;
    private String productName;
    private Integer productNum;
    private Double productPrice;
    private Date createTime;
    private Date startTime;
    private Date endTime;

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productNum=" + productNum +
                ", productPrice=" + productPrice +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }


    //Getters for serializing the Product objects to JSON format!!!
    public String getProductName() {
        return productName;
    }

    public int getProductNum() {
        return productNum;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}