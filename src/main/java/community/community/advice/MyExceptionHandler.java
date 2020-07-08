package community.community.advice;

import community.community.dto.ResultDTO;
import community.community.exception.ErrorCode;
import community.community.exception.MyException;
import community.community.exception.SystemExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handleControllerException(HttpServletRequest request, Throwable
            ex, Model model) {
//        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //return json
            if (ex instanceof MyException) {
                return ResultDTO.errorOf((MyException) ex);
            } else {
                ex.printStackTrace();
                return ResultDTO.errorOf(SystemExceptionCode.SERVER_ERROR);
            }
        } else {
            //return the html error page
            if (ex instanceof MyException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                ex.printStackTrace();
                model.addAttribute("message", "Undefined Error");
            }
        }
        return new ModelAndView("error");
    }
}
