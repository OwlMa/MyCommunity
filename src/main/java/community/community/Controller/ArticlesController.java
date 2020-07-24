package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.Service.CommentService;
import community.community.dto.ArticleDTO;
import community.community.dto.CommentDTO;
import community.community.enums.CommentTypeEnum;
import community.community.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ArticlesController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/{id}")
    public String article(@PathVariable(name = "id") Integer id,
                          Model model) {
        articleService.incViewByID(id);
        ArticleDTO articleDTO = articleService.getArticleDTOByID(id);
        String[] tags = articleDTO.getTags().split(",");
        List<Article> relatedArticles = articleService.selectRelated(tags, id);
        List<CommentDTO> commentDTOList = commentService.listById(id, CommentTypeEnum.ARTICLE.getCode());
        model.addAttribute("articles", articleDTO);
        model.addAttribute("comments", commentDTOList);
        model.addAttribute("tags", tags);
        model.addAttribute("relatedArticles", relatedArticles);
        return "articles";
    }
}
