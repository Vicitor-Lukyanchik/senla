package com.senla.hotel.handler;

import com.senla.hotel.exception.FileException;
import com.senla.hotel.handler.dto.ErrorDetails;
import com.senla.hotel.handler.dto.ErrorObject;
import com.senla.hotel.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleFileExceptionException(FileException fileException) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(fileException.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleFileExceptionException(BadCredentialsException fileException) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(fileException.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleServiceException(ServiceException serviceException) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMessage(serviceException.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        log.error(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, System.currentTimeMillis(), errorList);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
    }
}
