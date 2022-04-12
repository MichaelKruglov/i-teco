package ru.iteco.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.iteco.exceptions.PointsOperationException;

public class CustomExceptionHandler {

    @ExceptionHandler(value = PointsOperationException.class)
    public ResponseEntity<String> handleBlogAlreadyExistsException(PointsOperationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleBlogAlreadyExistsException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
