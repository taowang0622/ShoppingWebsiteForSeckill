package seckill.dto;

/**
 * Created by taowang on 1/27/2017.
 */

/**
 * This dto is for encapsulating all the response body content into JSON type!!!
 * @param <T>
 */
public class SeckillResult<T> {
    private boolean successful;
    private T data;
    private String error;

    //Successful
    public SeckillResult(boolean successful, T data) {
        this.successful = successful;
        this.data = data;
    }

    //Fail
    public SeckillResult(boolean successful, String error) {
        this.successful = successful;
        this.error = error;
    }
    public SeckillResult(boolean successful, T data, String error) {
        this.successful = successful;
        this.data = data;
        this.error = error;
    }

    //Getters for serializing the SeckillResult objects to JSON files!!!!!!!!
    public boolean isSuccessful() {
        return successful;
    }
    public T getData() {
        return data;
    }
    public String getError() {
        return error;
    }
}
