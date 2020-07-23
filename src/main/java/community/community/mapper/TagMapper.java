package community.community.mapper;

import community.community.model.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TagMapper {
    @Insert("insert into tags(name, stream) values(#{name}, #{stream})")
    void create(Tag newTag);

    @Select("select * from tags where name = #{name}")
    Tag selectByName(String name);

    @Update("update tags set stream = #{stream} where id = #{id}")
    void update(Tag oldTag);

    @Select("select count(1) from tags where name = #{name}")
    Integer countByName(String name);

    @Select("select * from tags")
    List<Tag> getAll();

    @Select("select * from tags where id = #{id}")
    Tag getByTagId(Integer id);
}
