package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.dto.ArticleDTO;
import community.community.mapper.ArticleMapper;
import community.community.mapper.UserMapper;
import community.community.model.Article;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
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

        List<ArticleDTO> articleDTOList = articleService.list();
        model.addAttribute("articleDTOList", articleDTOList);
        return "index";
    }
}
