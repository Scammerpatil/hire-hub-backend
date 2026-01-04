package com.hirehub.SpringServer.UtilityClasses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
    private int statusCode;
    private String response;
    private Object data;
}
