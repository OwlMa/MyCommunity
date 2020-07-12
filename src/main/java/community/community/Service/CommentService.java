package community.community.Service;

import community.community.dto.CommentDTO;
import community.community.enums.CommentTypeEnum;
import community.community.exception.ArticleExceptionCode;
import community.community.exception.CommentExceptionCode;
import community.community.exception.MyException;
import community.community.mapper.ArticleMapper;
import community.community.mapper.CommentMapper;
import community.community.mapper.UserMapper;
import community.community.model.Article;
import community.community.model.Comment;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new MyException(CommentExceptionCode.TARGET_MISS);
        }
        if (comment.getType() == null || !CommentTypeEnum.isCommentType(comment.getType())) {
            throw new MyException(CommentExceptionCode.TYPE_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.ARTICLE.getCode()) {
            //comment on the article
            Article article = articleMapper.getByID(comment.getParentId());
            if (article == null) {
                throw new MyException(ArticleExceptionCode.ARTICLE_NOT_EXIST);
            }
            commentMapper.insert(comment);
            article.setCommentCount(article.getCommentCount()+1);
            articleMapper.update(article);
        } else {
            //for the secondary comment
            Comment dbComment = commentMapper.selectByID(comment.getParentId());
            System.out.println(dbComment);
            if (dbComment == null) {
                throw new MyException(CommentExceptionCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }
    }

    public List<CommentDTO> listById(Integer id, Integer type) {
        List<CommentDTO> commentDTOS = new LinkedList<>();
        List<Comment> comments = commentMapper.listById(id, type);
        Map<Integer, User> commentator = new HashMap<>();
        for (Comment comment: comments) {
            User user = null;
            if (!commentator.containsKey(comment.getCommentator())) {
                user = userMapper.findById(comment.getCommentator());
                commentator.put(comment.getCommentator(), user);
            }
            else {
                user = commentator.get(comment.getCommentator());
            }
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(user);
            commentDTO.setGmtModified(System.currentTimeMillis());
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
