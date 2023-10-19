package com.example.magenta.exception;

import com.example.magenta.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class EmptyFileException extends BaseException {
    public EmptyFileException() {
        super(HttpStatus.NO_CONTENT, "Пустой файл");
    }
}
