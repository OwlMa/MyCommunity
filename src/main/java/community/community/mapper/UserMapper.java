package community.community.mapper;

import community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modified) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    public void insert(User user);

    @Select("select * from user where token = #{token}")
    public User findByCookie(@Param("token") String token);
}
