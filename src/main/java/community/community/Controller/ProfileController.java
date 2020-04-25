package community.community.Controller;

import community.community.Service.ArticleService;
import community.community.dto.PageDTO;
import community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("articles".equals(action)) {
            model.addAttribute("section", "articles");
            model.addAttribute("sectionName", "My Articles");
            PageDTO pageDTO = articleService.list(user, page, size);
            model.addAttribute("currPage", pageDTO);
            model.addAttribute("pages", pageDTO.getPages());
            model.addAttribute("Articles", pageDTO.getArticleDTOList());
        }
        else if ("comments".equals(action)) {
            model.addAttribute("section", "comments");
            model.addAttribute("sectionName", "Comments");
        }
        return "profile";
    }
}
