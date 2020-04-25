package community.community.Controller;

import community.community.dto.CommentDTO;
import community.community.mapper.CommentMapper;
import community.community.model.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setCommentator(8);
        comment.setGmtCreate(System.currentTimeMillis());
        commentMapper.insert(comment);
        return null;
    }
}
