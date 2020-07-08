package community.community.dto;

import community.community.exception.ErrorCode;
import community.community.exception.MyException;

public class ResultDTO {
    private String message;
    private Integer code;

    public static ResultDTO errorOf(MyException myException) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(myException.getCode());
        resultDTO.setMessage(myException.getMessage());
        return resultDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static ResultDTO errorOf(ErrorCode errorCode) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(errorCode.getCode());
        resultDTO.setMessage(errorCode.getMessage());
        return resultDTO;
    }
}
