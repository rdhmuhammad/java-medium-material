package com.github.mediummaterial.exception;

public class InvalidDataException extends RuntimeException{

    public InvalidDataException(){
        super();
    }

    public InvalidDataException(String msg){
        super(msg);
    }
}
