package community.community.exception;

public enum CommentExceptionCode implements ErrorCode {
    NOT_LOGIN(1001, "Please login first then comment"),
    TARGET_MISS(1002, "Object you want comment does not exist."),
    TYPE_WRONG(2003, "The type is not allowed"),
    COMMENT_NOT_FOUND(2004, "This comment does not exist."),
    COMMENT_INVALID(2005, "Comment can not be empty");

    private String message;
    private Integer code;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CommentExceptionCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
