package community.community.exception;

public enum ArticleExceptionCode implements ErrorCode{
    ARTICLE_NOT_EXIST(2001, "This article dose not exist"),
    ARTICLE_CREATOR_NOT_VALID(2002, "The current user does not have the access to delete this article");


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    ArticleExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
