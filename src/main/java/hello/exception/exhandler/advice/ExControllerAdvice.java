package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // 해당 컨트롤러에서 해당하는 오류가 발생할 경우에 @ExceptionHandler 가 자동으로 캐치하고 처리한다
    // RestController 의 경우 자동으로 json 형태로 반환한다
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    // @ExceptionHandler(UserException.class) 안의 오류와 파라미터 오류가 같은 경우 어노테이션 오류 생략 가능
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        // 위에서 내가 정의하지 않은 모든 오류는 최상위 객체인 Exception 이 처리한다
        log.error("[exceptionHandler] es", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
