package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.ReviewRecord;

public class ReviewRecordNotFoundException extends RuntimeException{
    public ReviewRecordNotFoundException() {
        super("Could not find record");
    }
}
