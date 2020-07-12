package community.community.Controller;

import community.community.Service.CommentService;
import community.community.dto.CommentDTO;
import community.community.dto.ResultDTO;
import community.community.enums.CommentTypeEnum;
import community.community.exception.CommentExceptionCode;
import community.community.mapper.CommentMapper;
import community.community.model.Comment;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CommentExceptionCode.NOT_LOGIN);
        }
        if (commentDTO.getContent() == "") {
            return ResultDTO.errorOf(CommentExceptionCode.COMMENT_INVALID);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setCommentator(8);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        commentService.insert(comment);
        Map<Object, Object> objectMap = new HashMap<>();
        objectMap.put("code", 200);
        objectMap.put("message", "success!");
        return objectMap;
    }
}
