package com.senla.hotel.exception;

public class FileRepositoryException extends RuntimeException {
    
    public FileRepositoryException(String message) {
        super(message);
    }
    
    public FileRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
