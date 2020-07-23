package community.community.enums;

public enum MessageTypeEnum {
    LIKE(1, "like"),
    REPLY_ARTICLE(2, "reply the article"),
    REPLY_COMMENT(3, "reply the message");
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
