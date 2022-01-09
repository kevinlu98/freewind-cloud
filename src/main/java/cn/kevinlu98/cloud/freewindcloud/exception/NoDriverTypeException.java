package cn.kevinlu98.cloud.freewindcloud.exception;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 22:31
 * Email: kevinlu98@qq.com
 * Description:
 */
public class NoDriverTypeException extends Exception {
    public NoDriverTypeException() {
    }

    public NoDriverTypeException(String message) {
        super(message);
    }

    public NoDriverTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDriverTypeException(Throwable cause) {
        super(cause);
    }

    public NoDriverTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
