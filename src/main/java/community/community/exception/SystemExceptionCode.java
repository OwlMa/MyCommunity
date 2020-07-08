package community.community.exception;

public enum SystemExceptionCode implements ErrorCode{

    SERVER_ERROR(500, "You good, My fault"),
    CLIENT_ERROR(400, "Sorry but it is your fault, try another link");

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

    SystemExceptionCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
