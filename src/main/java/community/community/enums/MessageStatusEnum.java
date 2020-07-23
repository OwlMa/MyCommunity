package community.community.enums;

public enum MessageStatusEnum {
    UNREAD(0, "Unread"),
    READ(1, "read");
    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    MessageStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
