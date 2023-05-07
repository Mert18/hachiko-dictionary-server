package com.m2t.hachikodictionary.exception;

public class WordNotFoundException extends RuntimeException{
    public WordNotFoundException(String message) {
        super(message);
    }
}
