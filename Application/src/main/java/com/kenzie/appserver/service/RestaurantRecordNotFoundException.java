package com.kenzie.appserver.service;

public class RestaurantRecordNotFoundException extends RuntimeException {
    public RestaurantRecordNotFoundException() {
        super("Could not find record");
    }
}
