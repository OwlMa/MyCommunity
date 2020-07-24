package community.community.mapper;

import community.community.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MessageMapper {

    @Insert("insert into message(sender, receiver, outer_id, type, gmt_create, status) values(#{sender}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{status})")
    void insert(Message message);

    @Select("select * from message where receiver = #{receiverID} limit #{offset}, #{size}")
    List<Message> getAllByReceiverID(Integer receiverID, Integer offset, Integer size);

    @Select("select count(1) from message where receiver = #{receiverID}")
    Integer countByReceiver(Integer receiverID);

    @Select("select * from message where id = #{id}")
    Message getMessageById(Integer id);

    @Update("update message set sender = #{sender}, receiver = #{receiver}, outer_id = #{outerId}, type = #{type}, gmt_create = #{gmtCreate}, status = #{status} where id = #{id}")
    void update(Message message);

    @Select("select count(1) from message where receiver = #{receiverID} and status = #{status}")
    Integer countByReceiverIDAndStatus(Integer receiverID, Integer status);
}
