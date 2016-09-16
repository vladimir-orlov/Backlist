package com.company.comands;

public class ExitException extends Exception{
    public ExitException() {
    }

    public ExitException(String message){
        super(message);
    }
}
