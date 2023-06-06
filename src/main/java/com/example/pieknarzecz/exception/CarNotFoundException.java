package com.example.pieknarzecz.exception;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(String messages){
        super(messages);
    }
}
