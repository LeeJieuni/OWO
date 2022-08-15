package com.sos.owo.error.Exception.custom;

import com.sos.owo.error.Exception.InvalidValueException;

public class SomethingNullException extends InvalidValueException {
    public SomethingNullException(String r){
        super(r + "is null");
    }

}
