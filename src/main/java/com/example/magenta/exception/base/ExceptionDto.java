package com.example.magenta.exception.base;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class ExceptionDto {
    private String code;
    private String message;
    private ZonedDateTime time;
}
