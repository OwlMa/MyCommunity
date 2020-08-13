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
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResultDTO post(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
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
        return ResultDTO.success();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO subComments(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CommentExceptionCode.NOT_LOGIN);
        }
        List<CommentDTO> commentDTOS = commentService.listById(id, CommentTypeEnum.COMMENT.getCode());
        return ResultDTO.success(commentDTOS);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/comment/{id}", method = RequestMethod.DELETE)
    public ResultDTO deleteComment(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        commentService.deleteByID(id, user);
        return ResultDTO.success();
    }
}
