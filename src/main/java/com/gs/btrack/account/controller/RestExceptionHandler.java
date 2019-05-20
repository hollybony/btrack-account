/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.btrack.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.notFound;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author LENOVO
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity userNotFound(UserNotFoundException ex, WebRequest request) {
        log.debug("handling UserNotFoundException...");
        return notFound().build();
    }
    
}
