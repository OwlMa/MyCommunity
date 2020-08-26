package community.community.Service;

import community.community.Provider.AWSProvider;
import community.community.dto.ArticleDTO;
import community.community.dto.CommentDTO;
import community.community.dto.PageDTO;
import community.community.enums.CommentTypeEnum;
import community.community.exception.ArticleExceptionCode;
import community.community.exception.MyException;
import community.community.mapper.ArticleMapper;
import community.community.mapper.UserMapper;
import community.community.model.Article;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AWSProvider awsProvider;

    public PageDTO<ArticleDTO> list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        List<Article> articleList = articleMapper.list(offset, size);
        for (Article article: articleList) {
            User user = userMapper.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        pageDTO.setDTOList(articleDTOList);
        Integer count  = articleMapper.count();
        pageDTO.setPage(count, page, size);


        return pageDTO;
    }

    public PageDTO<ArticleDTO> list(User user, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        List<Article> articleList = articleMapper.findByUserID(user.getId(), offset, size);
        for (Article article: articleList) {
            //User user = userMapper.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        pageDTO.setDTOList(articleDTOList);
        Integer count  = articleMapper.countByUserID(user.getId());
        pageDTO.setPage(count, page, size);
        return pageDTO;
    }

    /**
     * list the articleDTOs in a page by tagId, if an article has been deleted, remove it
     * from the tags bit
     * @param tagId
     * @param page
     * @param size
     * @return
     */
    public PageDTO<ArticleDTO> list(Integer tagId, Integer page, Integer size) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        List<Integer> articleIdList = tagService.getArticlesID(tagId);
        int lower = size * (page - 1);
        int upper = size * page;
        int countArticles = 0;
        for (Integer articleID: articleIdList) {
            countArticles++;
            if (countArticles <= lower || countArticles > upper) {
                continue;
            }
            Article article = articleMapper.getByID(articleID);
            if (article == null) {
                //if this article has been deleted, delete the article in the tags
                tagService.deleteByArticleID(tagId, articleID);
                countArticles--;
                continue;
            }
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            User user = userMapper.findById(article.getCreator());
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        pageDTO.setDTOList(articleDTOList);
        Integer count  = tagService.countById(tagId);
        pageDTO.setPage(count, page, size);
        return pageDTO;
    }


    public ArticleDTO getArticleDTOByID(Integer id) {
        Article article = articleMapper.getByID(id);
        if (article == null) {
            throw new MyException(ArticleExceptionCode.ARTICLE_NOT_EXIST);
        }
        article.setBody(getArticleContentIfURL(article));
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        User user = userMapper.findById(article.getCreator());
        articleDTO.setUser(user);
        return articleDTO;
    }

    /**
     * if the body of this article is an url direct to the s3 bucket, then return the content from s3 bucket
     * otherwise return the original content in the database of this article
     * @param article
     * @return
     */
    public String getArticleContentIfURL(Article article) {
        String content = article.getBody();
        if (content.length() > 3) {
            String head = content.substring(0, 3);
            if (head.equals("AWS") && content.charAt(3) == '?') {
                String[] str = content.split("\\?");
                content = awsProvider.getArticleContent(str[1]);
            }
        }
        return content;
    }

    @Transactional
    public void createOrUpdate(Article article) {
        if (article.getId() == null) {
            article.setGmtCreate(System.currentTimeMillis());
            article.setGmtModified(System.currentTimeMillis());
            articleMapper.create(article);
            Integer id = articleMapper.selectLastInsertId();
            tagService.createOrUpdate(article.getTags(), id);
        }
        else {
            Article original = articleMapper.getByID(article.getId());
            article.setCommentCount(original.getCommentCount());
            article.setViewCount(original.getViewCount());
            article.setViewCount(original.getLikeCount());
            article.setGmtModified(System.currentTimeMillis());
            articleMapper.update(article);
        }
    }

    public void incViewByID(Integer id) {
        Article article = articleMapper.getByID(id);
        if (article == null) {
            throw new MyException(ArticleExceptionCode.ARTICLE_NOT_EXIST);
        }
        article.setViewCount(article.getViewCount()+1);
        articleMapper.update(article);
    }

    public List<Article> selectRelated(String[] tags, Integer id) {
        StringBuffer sb = new StringBuffer();
        for (String tag: tags) {
            sb.append(tag);
            sb.append("|");
        }
        sb.deleteCharAt(sb.length()-1);
        List<Article> result = new LinkedList<>();
        List<Article> relatedArticles = articleMapper.selectRelated(sb.toString(), id);
        int count = 0;
        for (Article article: relatedArticles) {
            result.add(article);
            if (++count >= 5) {
                break;
            }
        }
        return result;
    }

    @Transactional
    public void deleteByID(Integer id, User user) {
        Article article = articleMapper.getByID(id);
        if (article == null) {
            throw new MyException(ArticleExceptionCode.ARTICLE_NOT_EXIST);
        }
        if (article.getCreator() != user.getId()) {
            throw new MyException(ArticleExceptionCode.ARTICLE_CREATOR_NOT_VALID);
        }
        //delete the articles in the aws
        String content = article.getBody();
        if (content.length() > 3) {
            String head = content.substring(0, 3);
            if (head.equals("AWS") && content.charAt(3) == '?') {
                String[] str = content.split("\\?");
                awsProvider.deleteArticles(str[1]);
            }
        }
        //delete the comments
        List<CommentDTO> commentDTOS= commentService.listById(id, CommentTypeEnum.ARTICLE.getCode());
        for (CommentDTO commentDTO: commentDTOS) {
            commentService.deleteByID(commentDTO.getId(), user);
        }
        articleMapper.deleteByID(id);
    }
}
