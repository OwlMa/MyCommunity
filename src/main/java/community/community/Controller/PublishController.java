package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.dto.ArticleDTO;
import community.community.model.Article;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String submit(@RequestParam("title") String title,
                         @RequestParam("body") String body,
                         @RequestParam("tags") String tags,
                         @RequestParam(value = "id", required = false) Integer id,
                         HttpServletRequest request,
                         Model model) {
        User user = (User) request.getSession().getAttribute("user");
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
        article.setId(id);
        articleService.createOrUpdate(article);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model) {
        ArticleDTO articleDTOByID = articleService.getArticleDTOByID(id);
        model.addAttribute("title", articleDTOByID.getTitle());
        model.addAttribute("body", articleDTOByID.getBody());
        model.addAttribute("tags", articleDTOByID.getTags());
        model.addAttribute("id", articleDTOByID.getId());
        return "publish";
    }


}
