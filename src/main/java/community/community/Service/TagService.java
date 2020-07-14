package community.community.Service;

import community.community.mapper.TagMapper;
import community.community.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.BitSet;

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
                BitSet bitSet = new BitSet(bytes.length * 8);
                int index = 0;
                for (int i = 0; i < bytes.length; i++) {
                    for (int j = 7; j >= 0; j--) {
                        bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true : false);
                    }
                }
                BitSet bitSet1 = new BitSet();
                bitSet1.set(ArticleId);
                bitSet.or(bitSet1);
                bytes = bitSet.toByteArray();
                oldTag.setStream(bytes);
                tagMapper.update(oldTag);
            }
        }
    }
}
