package community.community.Service;

import community.community.dto.ArticleDTO;
import community.community.dto.PageDTO;
import community.community.mapper.ArticleMapper;
import community.community.mapper.UserMapper;
import community.community.model.Article;
import community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;

    public PageDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        List<Article> articleList = articleMapper.list(offset, size);
        for (Article article: articleList) {
            User user = userMapper.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        pageDTO.setArticleDTOList(articleDTOList);
        Integer count  = articleMapper.count();
        pageDTO.setPage(count, page, size);


        return pageDTO;
    }

    public PageDTO list(User user, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        List<Article> articleList = articleMapper.findByUserID(user.getId(), offset, size);
        for (Article article: articleList) {
            //User user = userMapper.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        pageDTO.setArticleDTOList(articleDTOList);
        Integer count  = articleMapper.countByUserID(user.getId());
        pageDTO.setPage(count, page, size);
        return pageDTO;
    }

    public ArticleDTO getArticleDTOByID(Integer id) {
        Article article = articleMapper.getByID(id);
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        User user = userMapper.findById(article.getCreator());
        articleDTO.setUser(user);
        return articleDTO;
    }

    public void createOrUpdate(Article article) {
        if (article.getId() == null) {
            articleMapper.create(article);
        }
        else {
            article.setGmtModified(System.currentTimeMillis());
            articleMapper.update(article);
        }
    }

    public void incViewByID(Integer id) {
        Article article = articleMapper.getByID(id);
        article.setViewCount(article.getViewCount()+1);
        articleMapper.update(article);
    }
}
