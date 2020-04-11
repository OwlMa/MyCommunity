package community.community.Controller;

import community.community.mapper.ArticleMapper;
import community.community.mapper.UserMapper;
import community.community.model.Article;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String submit(@RequestParam("title") String title,
                         @RequestParam("body") String body,
                         @RequestParam("tags") String tags,
                         HttpServletRequest request,
                         Model model) {
        User user = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie != null && cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByCookie(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }
        if (user == null) {
            model.addAttribute("error", "Login Please");
            return "publish";
        }
        Article article = new Article();
        article.setTitle(title);
        article.setBody(body);
        article.setTags(tags);
        article.setCreator(user.getId());
        article.setGmtCreate(System.currentTimeMillis());
        article.setGmtModified(System.currentTimeMillis());
        articleMapper.create(article);

        return "redirect:/";
    }


}
