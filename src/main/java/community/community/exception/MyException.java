package community.community.exception;

public class MyException extends RuntimeException{
    private ErrorCode errorCode;

    public MyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
    public Integer getCode() {
        return errorCode.getCode();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
