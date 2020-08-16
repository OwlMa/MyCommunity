package community.community.Service;

import community.community.dto.TagDTO;
import community.community.mapper.TagMapper;
import community.community.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    public void createOrUpdate(String tags, Integer ArticleId) {
        String[] tag = tags.split(",");
        for (String t: tag) {
            Integer count = tagMapper.countByName(t);
            if (count == 0) {
                Tag newTag = new Tag();
                newTag.setName(t);
                BitSet bitSet = new BitSet();
                bitSet.set(ArticleId);
                byte[] bytes = bitSet.toByteArray();
                newTag.setStream(bytes);
                tagMapper.create(newTag);
            } else {
                Tag oldTag = tagMapper.selectByName(t);
                byte[] bytes = oldTag.getStream();
                BitSet bitSet = BitSet.valueOf(bytes);
//                int index = 0;
//                for (int i = 0; i < bytes.length; i++) {
//                    for (int j = 7; j >= 0; j--) {
//                        bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true : false);
//                    }
//                }
                bitSet.set(ArticleId);
                bytes = bitSet.toByteArray();
                oldTag.setStream(bytes);
                tagMapper.update(oldTag);
            }
        }
    }

    public List<TagDTO> getAllTags() {
        List<Tag> tagList = tagMapper.getAll();
        List<TagDTO> tagDTOList = new LinkedList<>();
        for (Tag tag: tagList) {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tag.getId());
            tagDTO.setName(tag.getName());
            int count = countById(tag.getId());
            tagDTO.setNums(count);
            String desc = "There are " + tagDTO.getNums() + " articles in " + tagDTO.getName() + " Section";
            tagDTO.setDescription(desc);
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;
    }

    public List<Integer> getArticlesID(Integer id) {
        List<Integer> list = new LinkedList<>();
        Tag tag = tagMapper.getByTagId(id);
        byte[] bytes = tag.getStream();
        BitSet bitSet = BitSet.valueOf(bytes);
        int index = 0;
        while (index < bitSet.length()) {
            if (bitSet.get(index)) {
                list.add(index);
            }
            index++;
        }
//        for (int i = 0; i < bytes.length; i++) {
//            for (int j = 7; j >= 0; j--) {
//                if (((bytes[i] & (1 << j)) >> j) == 1) {
//                    bitSet.set(index, true);
//                    list.add(index++);
//                }
//                else {
//                    bitSet.set(index++, false);
//                }
//            }
//        }

        return list;
    }

    public Integer countById(Integer id) {
        Tag tag = tagMapper.getByTagId(id);
        byte[] bytes = tag.getStream();
        BitSet bitSet = new BitSet(bytes.length * 8);
        int index = 0;
        int count = 0;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                if ((bytes[i] & (1 << j)) >> j == 1) {
                    bitSet.set(index++, true);
                    count++;
                }
                else {
                    bitSet.set(index++, false);
                }
            }
        }
        return count;
    }

    public String getNameById(Integer id) {
        Tag tag = tagMapper.getByTagId(id);
        return tag.getName();
    }

    /**
     * delete the article in the specific tags
     * @param tagID, articleID
     * @param articleID
     */
    public void deleteByArticleID(Integer tagID, Integer articleID) {
        Tag tag = tagMapper.getByTagId(tagID);
        byte[] bytes = tag.getStream();
        BitSet bitSet = BitSet.valueOf(bytes);
        bitSet.set(articleID, false);
        bytes = bitSet.toByteArray();
        tag.setStream(bytes);
        tagMapper.update(tag);
    }
}
