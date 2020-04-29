package com.bk.community.dto;

import com.bk.community.exception.CustomizeException;
import com.bk.community.exception.ICustomizeErrorCode;
import lombok.Data;

/**
 * @author : bear
 * @date : 2020-04-27 13:47
 **/

@Data
public class ResultDTO {

    private Integer code;
    private String message;

    public static ResultDTO errOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }


    public static ResultDTO errOf(ICustomizeErrorCode errorCode) {
        return errOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errOf(CustomizeException e) {
        return errOf(e.getCode(), e.getMessage());
    }


    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
