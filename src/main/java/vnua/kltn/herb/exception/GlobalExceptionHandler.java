package vnua.kltn.herb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vnua.kltn.herb.response.HerbResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HerbException.class)
    public ResponseEntity<HerbResponse<Object>> handleHerbException(HerbException ex) {
        // Lấy message từ exception
        String errorMessage = ex.getMessage();
        int errorCode = ex.getCode();

        // Tạo response
        HerbResponse<Object> response = new HerbResponse<>(errorCode, errorMessage);

        // Determine HTTP status based on error code or use a default
        HttpStatus status = HttpStatus.BAD_REQUEST; // Default status

        // You can add logic here to determine the appropriate HTTP status
        // based on the error code if needed

        return new ResponseEntity<>(response, status);
    }
}
