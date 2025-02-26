package com.dixie.exception;

public class ImagerPostNotFoundException extends Exception { ;

    public ImagerPostNotFoundException() {
        super("Sorry, the requested post(s) is not found");
    }
}
