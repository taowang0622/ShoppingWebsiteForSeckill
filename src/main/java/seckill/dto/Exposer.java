package seckill.dto;

/**
 * Created by taowang on 1/21/2017.
 */

import java.util.Date;

/**
 * Intended to expose seckill interfaces to clients
 */
public class Exposer {
    private boolean started;
//    private Date startTime;  It's better to be lightweight for dto
    private long startTime;
    private long endTime;
    private long currentTimeOfServer;
    private long productId;
    //For handling the situation where users forge seckill execution URL!!!!!
    private String md5;

    /**
     * Used when the product corresponding passed productId is not present. At this point, started is false
     * @param started
     * @param productId
     */
    public Exposer(boolean started, long productId) {
        this.started = started;
        this.productId = productId;
    }

    /**
     * Used when seckill starts, md5 represents a token
     * @param started
     * @param productId
     * @param md5
     */
    public Exposer(boolean started, long productId, String md5) {
        this.started = started;
        this.productId = productId;
        this.md5 = md5;
    }

    /**
     * Used when seckill has not started yet
     * @param started
     * @param startTime
     * @param endTime
     * @param currentTimeOfServer
     * @param productId
     */
    public Exposer(boolean started, long startTime, long endTime, long currentTimeOfServer, long productId) {
        this.started = started;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentTimeOfServer = currentTimeOfServer;
        this.productId = productId;
    }



    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCurrentTimeOfServer() {
        return currentTimeOfServer;
    }

    public void setCurrentTimeOfServer(long currentTimeOfServer) {
        this.currentTimeOfServer = currentTimeOfServer;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "started=" + started +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", currentTimeOfServer=" + currentTimeOfServer +
                ", productId=" + productId +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
