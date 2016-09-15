package com.company;

public class ExitException extends Exception{
    public ExitException() {
    }

    public ExitException(String message){
        super(message);
    }
}
