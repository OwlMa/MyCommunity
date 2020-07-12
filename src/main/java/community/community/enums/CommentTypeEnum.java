package community.community.enums;

public enum CommentTypeEnum {
    ARTICLE(1),
    COMMENT(2),
    SUCCESS(200);
    //SUCCESS(3);
    private Integer code;

    CommentTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
    public static boolean isCommentType(Integer c) {
        if (c == CommentTypeEnum.ARTICLE.getCode() || c == CommentTypeEnum.COMMENT.getCode()) {
            return true;
        }
        return false;
    }
}
