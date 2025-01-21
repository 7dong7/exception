package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if(ex instanceof IllegalArgumentException) {
            // IllegalArgumentException 예외가 발생하면 무조건 400 으로 처리 하겠다
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

                return new ModelAndView(); // 빈 ModelAndView 를 반환하면 정상적인 흐름으로 서블릿에 리턴된다
            }

        } catch (IOException e) {
            log.error("resolver exception", e);
        }

        return null;
    }

}
