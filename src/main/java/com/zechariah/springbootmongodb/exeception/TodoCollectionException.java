package com.zechariah.springbootmongodb.exeception;

public class TodoCollectionException extends Exception{
    public static final Long serialVersionUID = 1L;

    public TodoCollectionException(String message) {
        super(message);
    }

    public static String todoNotFound(String id){
        return "Todo with "+id+" was Not Found";
    }

    public static String todoAlreadyExist(){
        return "Todo given already Exist";
    }
}
