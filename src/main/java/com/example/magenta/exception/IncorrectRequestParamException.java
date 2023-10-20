package com.example.magenta.exception;

import com.example.magenta.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class IncorrectRequestParamException extends BaseException {

    public IncorrectRequestParamException() {
        super(HttpStatus.BAD_REQUEST, "Неправильные параметры");
    }

}
