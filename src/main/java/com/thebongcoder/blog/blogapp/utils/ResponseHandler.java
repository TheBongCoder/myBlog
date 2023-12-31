package com.thebongcoder.blog.blogapp.utils;

import java.util.Date;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {

    @Autowired
    private MessageSource messageSource;

    private ResponseHandler() {
    }

    public ResponseEntity<Object> generateResponse(Object response, String messageCode, boolean isSuccess, HttpStatus httpStatus) {
        TreeMap<String, Object> responseMap = new TreeMap<>();
        responseMap.put("data", response);
//        responseMap.put("message", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
        responseMap.put("message", messageCode);
        responseMap.put("isSuccess", isSuccess);
        responseMap.put("timeStamp", new Date().getTime());
        return new ResponseEntity<>(responseMap, httpStatus);
    }


}
