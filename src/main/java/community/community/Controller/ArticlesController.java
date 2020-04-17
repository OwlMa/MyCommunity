package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.dto.ArticleDTO;
import community.community.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticlesController {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles/{id}")
    public String article(@PathVariable(name = "id") Integer id,
                          Model model) {
        ArticleDTO articleDTO = articleService.getArticleDTOByID(id);
        model.addAttribute("articles", articleDTO);
        return "articles";
    }
}
