package com.fertilizer.payload.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ErrorMsg {
    public String statusCode;
    public String message;
}
