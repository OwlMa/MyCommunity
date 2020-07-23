package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.dto.PageDTO;
import community.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {

        PageDTO pageDTO = articleService.list(page, size);
        model.addAttribute("currPage", pageDTO);
        model.addAttribute("pages", pageDTO.getPages());
        model.addAttribute("Articles", pageDTO.getArticleDTOList());
        model.addAttribute("label", null);
        return "index";
    }

}
