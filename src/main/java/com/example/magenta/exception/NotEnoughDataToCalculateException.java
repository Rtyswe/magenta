package com.example.magenta.exception;

import com.example.magenta.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class NotEnoughDataToCalculateException extends BaseException {
    public NotEnoughDataToCalculateException() {
        super(HttpStatus.NOT_FOUND, "Недостаточно информации для вычисления расстояния");
    }

    public NotEnoughDataToCalculateException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
