package pl.sb.projekt.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(
            final NotFoundException ex, final WebRequest request) {
        final ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<ErrorMessage> sqlIntegrityConstraintViolationException(
            final DataIntegrityViolationException ex, final WebRequest request) {
        final ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            final MethodArgumentNotValidException ex, final WebRequest request) {
        final ErrorMessage message = new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                new Date(),
                "Cause: " + ex.getFieldError(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}