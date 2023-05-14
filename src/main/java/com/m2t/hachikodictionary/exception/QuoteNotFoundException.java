package com.m2t.hachikodictionary.exception;

public class QuoteNotFoundException extends RuntimeException{
    public QuoteNotFoundException(String message) {
        super(message);
    }
}
