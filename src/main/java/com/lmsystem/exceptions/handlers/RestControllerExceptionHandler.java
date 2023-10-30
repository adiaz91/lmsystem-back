package com.lmsystem.exceptions.handlers;



import com.lmsystem.exceptions.InvalidEntityException;
import com.lmsystem.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    private static final String MESSAGE = "message";


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, String> resourceNotFoundException(ResourceNotFoundException ex) {
        log.info(ex.getMessage(), ex);
        return Map.of(MESSAGE, ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> exception(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Map.of(MESSAGE, "An internal server error has occurred");
    }

    @ExceptionHandler(value = {InvalidEntityException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> invalidEntityException(InvalidEntityException ex) {
        log.info(ex.getMessage(), ex);
        return Map.of(MESSAGE, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        BindingResult result = ex.getBindingResult();

        var errors = result.getFieldErrors()
                .stream()
                .map(e->e.getField()+" : "+e.getDefaultMessage())
                .collect(Collectors.joining(" "));
        return Map.of(MESSAGE, errors);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        String constName="";
        if(ex.getCause() instanceof ConstraintViolationException cex){
            try {
                constName = cex.getConstraintName().toLowerCase();
            }catch (Exception ex1){
                log.error(ex.getMessage(), ex1);
            }
        }
        String message="Invalid entity";
        if(constName.contains("students_email")){
            message="The email is already in use";
        }else if(constName.contains("courses_name")){
            message="Another course has this name";
        }else if(constName.contains("course_registration")){
            message="Student already registered to the course";
        }
        log.error(ex.getMessage(), ex);
        return Map.of(MESSAGE, message);
    }

}
