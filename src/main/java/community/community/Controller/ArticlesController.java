package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.Service.CommentService;
import community.community.dto.ArticleDTO;
import community.community.dto.CommentDTO;
import community.community.dto.ResultDTO;
import community.community.enums.CommentTypeEnum;
import community.community.model.Article;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @ResponseBody
    @RequestMapping(value = "/delete/article/{id}", method = RequestMethod.DELETE)
    public ResultDTO deleteArticle(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        articleService.deleteByID(id, user);
        return ResultDTO.success();
    }
}
