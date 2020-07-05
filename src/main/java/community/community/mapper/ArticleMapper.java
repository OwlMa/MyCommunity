package community.community.mapper;

import community.community.model.Article;
import community.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title, body, gmt_create, gmt_modified, creator, tags) values(#{title}, #{body}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tags})")
    public void create(Article article);

    @Select("select * from article limit #{offset}, #{size}")
    List<Article> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from article")
    Integer count();

    @Select("select count(1) from article where creator = #{id}")
    Integer countByUserID(@Param(value = "id") Integer id);

    @Select("select * from article where creator = #{id} limit #{offset}, #{size}")
    List<Article> findByUserID(@Param(value = "id") Integer id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from article where id = #{id}")
    Article getByID(@Param(value = "id") Integer id);

    @Update("update article set title = #{title}, body = #{body}, tags = #{tags}, gmt_modified = #{gmtModified}, view_count = #{viewCount} where id = #{id}")
    void update(Article article);
}
