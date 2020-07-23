package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.Service.TagService;
import community.community.dto.PageDTO;
import community.community.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagsController {
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/tags")
    public String tags(Model model) {
        List<TagDTO> tagDTOList = tagService.getAllTags();
        model.addAttribute("tagList", tagDTOList);
        return "tags";
    }

    @GetMapping("/tags/{id}")
    public String tag(@PathVariable(name = "id") Integer id, Model model,
                      @RequestParam(name = "page", defaultValue = "1") Integer page,
                      @RequestParam(name = "size", defaultValue = "5") Integer size) {
        PageDTO pageDTO = articleService.list(id, page, size);
        String label = tagService.getNameById(id);
        model.addAttribute("currPage", pageDTO);
        model.addAttribute("pages", pageDTO.getPages());
        model.addAttribute("Articles", pageDTO.getArticleDTOList());
        model.addAttribute("label", label);
        model.addAttribute("labelID", id);
        return "index";
    }
}
