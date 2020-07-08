package community.community.exception;

public enum ArticleExceptionCode implements ErrorCode{
    ARTICLE_NOT_EXIST(2001, "This article dose not exist");


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
