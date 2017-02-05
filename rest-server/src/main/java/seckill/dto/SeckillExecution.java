package seckill.dto;

import seckill.entity.SuccessKilled;
import seckill.enums.SeckillStateEnum;

/**
 * Created by taowang on 1/21/2017.
 */
public class SeckillExecution {
    private long productId;
    private int stateCode; //The state code for the seckill execution result!
    private String stateInfo; //detailed info for the state code
    private SuccessKilled successKilled; //When execution is successful, return an SuccessKilled object to the client side

    /**
     * Used when seckill fails
     *
     * @param state
     */
    public SeckillExecution(SeckillStateEnum state) {
        this.stateCode = state.getStateCode();
        this.stateInfo = state.getStateInfo();
    }

    /**
     * Used when seckill succeeds
     * @param productId
     * @param state
     * @param successKilled
     */
    public SeckillExecution(long productId, SeckillStateEnum state, SuccessKilled successKilled) {
        this.productId = productId;
        this.stateCode = state.getStateCode();
        this.stateInfo = state.getStateInfo();
        this.successKilled = successKilled;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "productId=" + productId +
                ", stateCode=" + stateCode +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
