package com.kenzie.appserver.service;

public class RatingOutOfBoundException extends RuntimeException {
    public RatingOutOfBoundException() {
        super("Rating is out of bounds");
    }
}
