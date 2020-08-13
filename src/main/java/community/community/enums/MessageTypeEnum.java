package community.community.enums;

public enum MessageTypeEnum {
    LIKE(1, "like"),
    REPLY_ARTICLE(2, "reply the article"),
    REPLY_COMMENT(3, "reply the message"),
    ARTICLE_DELETED(4, "This article has been deleted"),
    COMMENT_DELETED(5, "This comment has been deleted");
    private int status;
    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    MessageTypeEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
