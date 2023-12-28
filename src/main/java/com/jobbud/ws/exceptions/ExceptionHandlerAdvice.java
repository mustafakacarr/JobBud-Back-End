package com.jobbud.ws.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest) {
        ApiError apiError = new ApiError(400, "Validation Error", httpServletRequest.getServletPath());
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;

    }



    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ApiError handleOtherExceptions(IllegalArgumentException illegalArgumentException, HttpServletRequest httpServletRequest) {
            ApiError apiError = new ApiError(400, illegalArgumentException.getMessage(), httpServletRequest.getServletPath());

            return apiError;

        }
        @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
        public ApiError handleOtherExceptions(NotFoundException notFoundException, HttpServletRequest httpServletRequest) {
            ApiError apiError = new ApiError(404, notFoundException.getMessage(), httpServletRequest.getServletPath());

            return apiError;

        }

}
