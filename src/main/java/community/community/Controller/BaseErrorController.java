package community.community.Controller;

import community.community.exception.SystemExceptionCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class BaseErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }
    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpStatus status = this.getStatus(request);
        if (status.is4xxClientError()) {
            model.addAttribute("message", SystemExceptionCode.CLIENT_ERROR.getMessage());
        }
        else if (status.is5xxServerError()) {
            model.addAttribute("message", SystemExceptionCode.SERVER_ERROR.getMessage());
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
