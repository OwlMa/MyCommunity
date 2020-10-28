package community.community.redis;

import community.community.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisProvider {
    @Autowired
    private RedisTemplate redisTemplate;

    public void createRedisArticle(Article article) {
        String redisID = "Article:" + article.getId();
        final HashOperations articleHash = redisTemplate.opsForHash();
        Map<String, String> map = new HashMap<>();
        map.put("title", article.getTitle());
        map.put("body", article.getBody());
        map.put("creator", "Null");
        articleHash.putAll(redisID, map);
    }

    public Article getArticle(Integer id) {
        String redisID = "Article:" + String.valueOf(id);
        final HashOperations articleHash = redisTemplate.opsForHash();
        Map<String, String> map = articleHash.entries(redisID);
        if (map.size() == 0) return null;
        Article article = new Article();
        article.setId(id);
        article.setBody(map.get("body"));
        article.setTitle(map.get("title"));
        return article;
    }
}
