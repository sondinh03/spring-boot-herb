package vnua.kltn.herb.exception;

import lombok.Getter;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;

@Getter
public class HerbException extends Exception {
    private final int code;
    private final String message;

    public HerbException(ErrorCodeEnum errorEnum) {
        this.code = errorEnum.getErrorCode();
        this.message = errorEnum.getMessage();
    }

    public HerbException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HerbException(ErrorCodeEnum errorEnum, Throwable cause) {
        super(errorEnum.getMessage(), cause);
        this.code = errorEnum.getErrorCode();
        this.message = errorEnum.getMessage();
    }

    public HerbException(ErrorCodeEnum errorEnum, String message) {
        this.code = errorEnum.getErrorCode();
        this.message = message;
    }
}
