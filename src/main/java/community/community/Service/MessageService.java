package community.community.Service;

import community.community.dto.MessageDTO;
import community.community.dto.PageDTO;
import community.community.enums.CommentTypeEnum;
import community.community.enums.MessageStatusEnum;
import community.community.enums.MessageTypeEnum;
import community.community.exception.CommentExceptionCode;
import community.community.exception.MyException;
import community.community.mapper.ArticleMapper;
import community.community.mapper.CommentMapper;
import community.community.mapper.MessageMapper;
import community.community.mapper.UserMapper;
import community.community.model.Article;
import community.community.model.Comment;
import community.community.model.Message;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    public PageDTO<MessageDTO> list(User user, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<MessageDTO> messageDTOList = new ArrayList<>();
        PageDTO<MessageDTO> pageDTO = new PageDTO<>();
        List<Message> messageList = messageMapper.getAllByReceiverID(user.getId(), offset, size);
        for (Message message: messageList) {
            MessageDTO messageDTO = new MessageDTO();
            BeanUtils.copyProperties(message, messageDTO);
            User sender = userMapper.findById(message.getSender());
            messageDTO.setSenderUser(sender);

            if (message.getType() == MessageTypeEnum.REPLY_ARTICLE.getStatus()) {
                //if this message is a comment for an article
                Article article = articleMapper.getByID(message.getOuterId());
                if (article == null) {
                    article = ArticleDelete();
                    messageDTO.setType(MessageTypeEnum.ARTICLE_DELETED.getStatus());
                }
                messageDTO.setArticle(article);
            } else if (message.getType() == MessageTypeEnum.REPLY_COMMENT.getStatus()) {
                //message for secondary comment
                Comment comment = commentMapper.selectByID(message.getOuterId());
                if (comment == null) {//this comment has been deleted
                    comment = new Comment();
                    comment.setType(CommentTypeEnum.DELETE.getCode());
                    messageDTO.setType(MessageTypeEnum.COMMENT_DELETED.getStatus());
                }
                Article article = null;
                if (comment.getType() == CommentTypeEnum.ARTICLE.getCode()) {
                    article = articleMapper.getByID(comment.getParentId());
                    if (article == null) {
                        article = ArticleDelete();
                        messageDTO.setType(MessageTypeEnum.ARTICLE_DELETED.getStatus());
                    }
                } else if (comment.getType() == CommentTypeEnum.COMMENT.getCode()){//reply to the comment
//                    Comment parentComment = commentMapper.selectByID(comment.getParentId());
//                    if (parentComment == null || parentComment.getType() != CommentTypeEnum.ARTICLE.getCode()) {
//                        throw new MyException(CommentExceptionCode.TARGET_MISS);
//                    } else {
//                        article = articleMapper.getByID(parentComment.getParentId());
//                        if (article == null) {
//                            article = ArticleDelete();
//                        }
//                    }
                } else if (comment.getType() == CommentTypeEnum.DELETE.getCode()) {
                    article = ArticleDelete();
                }
                messageDTO.setArticle(article);
            }

            if (message.getStatus() == MessageStatusEnum.UNREAD.getCode()) {
                messageDTO.setUnread(true);
            } else {
                messageDTO.setUnread(false);
            }
            messageDTOList.add(messageDTO);
        }
        Collections.reverse(messageDTOList);
        pageDTO.setDTOList(messageDTOList);
        Integer count = messageMapper.countByReceiver(user.getId());
        pageDTO.setPage(count, page, size);
        return pageDTO;
    }

    private Article ArticleDelete() {
        Article article = new Article();
        article.setTitle("This article has been deleted");
        return article;
    }

    public Integer getUnreadCount(List<MessageDTO> dtoList) {
        Integer count = 0;
        for (MessageDTO messageDTO: dtoList) {
            if (messageDTO.isUnread()) {
                count++;
            }
        }
        return count;
    }

    public void setReadById(Integer id) {
        Message message = messageMapper.getMessageById(id);
        message.setStatus(MessageStatusEnum.READ.getCode());
        messageMapper.update(message);
    }

    public Integer getUnreadCountByUser(Integer id) {
        Integer count = messageMapper.countByReceiverIDAndStatus(id, MessageStatusEnum.UNREAD.getCode());
        return count;
    }
}
