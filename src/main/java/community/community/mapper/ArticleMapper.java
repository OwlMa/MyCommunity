package community.community.mapper;

import community.community.model.Article;
import community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title, body, gmt_create, gmt_modified, creator, comment_count, view_count, like_count, tags) values(#{title}, #{body}, #{gmtCreate}, #{gmtModified}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tags})")
    public void create(Article article);

    @Select("select * from article")
    List<Article> list();

}
