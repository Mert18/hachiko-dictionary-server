package com.m2t.hachikodictionary.exception;

public class WordAlreadyExistsException extends RuntimeException{
    public WordAlreadyExistsException(String message) {
        super(message);
    }
}
