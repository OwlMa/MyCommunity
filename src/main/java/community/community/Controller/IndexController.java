package community.community.Controller;

import community.community.mapper.UserMapper;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie != null && cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByCookie(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
            }
        }
        return "index";
    }
}
