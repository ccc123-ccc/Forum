package com.example.demo.DTO;

import com.example.demo.exception.CustomizeException;
import com.example.demo.exception.ICustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO ();
        resultDTO.setCode (code);
        resultDTO.setMessage (message);
        return resultDTO;
    }
    public static ResultDTO errorOf(ICustomizeErrorCode errorCode){
      return errorOf (errorCode.getCode (), errorCode.getMessage ());
    }

    public static ResultDTO errorOf (CustomizeException e) {
        return errorOf (e.getCode (), e.getMessage ());
    }
}
