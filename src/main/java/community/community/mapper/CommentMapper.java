package community.community.mapper;

import community.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id, type, commentator, gmt_create, content, gmt_modified) values(#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{content}, #{gmtModified})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{Id}")
    Comment selectByID(Integer Id);

    @Select("select * from comment where parent_id = #{id} and type = #{type}")
    List<Comment> listById(Integer id, Integer type);
}
