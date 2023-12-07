package co.flexidev.theta.config;

import co.flexidev.theta.error.BadRequestException;
import co.flexidev.theta.error.ForbiddenRequestException;
import co.flexidev.theta.model.BaseErrorModel;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

// Global @Controller exception catcher
@ControllerAdvice
public class ControllerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerInterceptor.class);

    private HttpServletRequest request;

    public ControllerInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity badRequestException(BadRequestException e) {
        return ResponseEntity
                .badRequest()
                .body(createError(e, e.getValue()));
    }

    @ExceptionHandler(ForbiddenRequestException.class)
    protected ResponseEntity forbiddenRequestException(ForbiddenRequestException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(createError(e, e.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    // Modified Spring Exception
    // handle error on null/empty required request body or parameter
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResponseEntity
                .badRequest()
                .body(createError(e, e.getMessage()));
    }

    // handle @Valid error to be more informative on response
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        //Get all errors
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getObjectName() + "." + x.getField() + " " + x.getDefaultMessage())
                .sorted()
                .collect(Collectors.toList());

        return ResponseEntity
                .badRequest()
                .body(createError(e, errors));
    }

    private BaseErrorModel createError(Exception exception, Object errorMessage) {
        return createError(exception, errorMessage, HttpStatus.BAD_REQUEST.value());
    }

    private BaseErrorModel createError(Exception exception, Object errorMessage, Integer status) {
        LOGGER.error("Caught {} with message : {}", exception.getClass().getSimpleName(), errorMessage);
        return new BaseErrorModel(status, errorMessage, request.getContextPath() + request.getServletPath());
    }
}