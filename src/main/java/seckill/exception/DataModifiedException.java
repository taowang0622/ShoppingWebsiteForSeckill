package seckill.exception;

/**
 * Created by taowang on 2/3/2017.
 */
public class DataModifiedException extends RuntimeException {
    public DataModifiedException(String message) {
        super(message);
    }

    public DataModifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}
