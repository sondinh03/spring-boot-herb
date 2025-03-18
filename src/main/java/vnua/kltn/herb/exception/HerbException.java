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
}
