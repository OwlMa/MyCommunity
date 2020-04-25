package community.community.mapper;

import community.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id, type, commentator, gmt_create, like_count, content) values(#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{likeCount}, #{content})")
    void insert(Comment comment);
}
