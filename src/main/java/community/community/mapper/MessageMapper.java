package community.community.mapper;

import community.community.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MessageMapper {

    @Select("insert into message(sender, receiver, outer_id, type, gmt_create, status) values(#{sender}, #{receiver}, #{outer_id}, #{type}, #{gmtCreate}, #{status})")
    void insert(Message message);
}
