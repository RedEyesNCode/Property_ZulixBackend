package com.redeyesncode.estatespring.realestatebackend.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomStatusCodeModel extends StatusCodeModel {
    private Object data;

    public CustomStatusCodeModel(String status, int code, String message, Object data) {
        super(status, code, message);
        this.data = data;
    }

    public CustomStatusCodeModel(String status, int code, Object data) {
        super(status, code);
        this.data = data;
    }
}
