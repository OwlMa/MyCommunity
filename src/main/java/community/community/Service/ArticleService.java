package community.community.Service;

import community.community.dto.ArticleDTO;
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

    public List<ArticleDTO> list() {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        List<Article> articleList = articleMapper.list();
        for (Article article: articleList) {
            User user = userMapper.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }
}
