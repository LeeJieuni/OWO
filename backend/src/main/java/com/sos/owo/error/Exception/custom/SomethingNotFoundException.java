package com.sos.owo.error.Exception.custom;

import com.sos.owo.error.Exception.EntityNotFoundException;

public class SomethingNotFoundException extends EntityNotFoundException {
    public SomethingNotFoundException(String value){
        super(value+" is not found");
    }
}
