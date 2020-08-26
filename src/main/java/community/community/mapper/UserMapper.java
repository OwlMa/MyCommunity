package community.community.mapper;

import community.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modified, avatar_url) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    public void insert(User user);

    @Select("select * from user where token = #{token}")
    public User findByCookie(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountID(@Param("accountId") Integer accountId);

    @Update("update user set name = #{name}, token = #{token}, avatar_url = #{avatarUrl}, gmt_modified = #{gmtModified} where id = #{id} and account_id = #{accountId}")
    void update(User user);

    @Select("select * from user where name = #{username}")
    User findByName(String username);
}
