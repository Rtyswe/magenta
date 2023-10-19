package com.example.magenta.exception.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handle(BaseException e) {
        String code = Arrays.stream(
                StringUtils.splitByCharacterTypeCamelCase(
                        e.getClass().getSimpleName())
                ).filter(part -> !StringUtils.equals(part, "Exception"))
                .map(StringUtils::upperCase)
                .collect(Collectors.joining("_")
        );

        return ResponseEntity.status(e.getStatus())
                .body(
                        ExceptionDto.builder()
                                .message(e.getMessage())
                                .code(code)
                                .time(ZonedDateTime.now())
                                .build()
                );
    }
}
