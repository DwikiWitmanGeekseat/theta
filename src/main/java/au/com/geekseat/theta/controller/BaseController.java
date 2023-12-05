package au.com.geekseat.theta.controller;

import au.com.geekseat.theta.error.BadRequestException;
import au.com.geekseat.theta.error.ForbiddenRequestException;
import au.com.geekseat.theta.model.BaseModel;
import au.com.geekseat.theta.service.BaseService;
import au.com.geekseat.theta.service.IdentityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class BaseController<T extends BaseModel, E extends BaseService> {

    Logger LOGGER = LogManager.getLogger(BaseController.class);

    protected final E service;

    public BaseController(E service) {
        this.service = service;
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<T> add(@RequestBody @Valid T entity) {
        return (ResponseEntity<T>) ResponseEntity.ok(service.save(entity));
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<T>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/edit")
    public ResponseEntity<T> edit(@RequestBody @Valid T entity) {
        return (ResponseEntity<T>) ResponseEntity.ok(service.update(entity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@RequestParam("id") Long id) {
        return (ResponseEntity<T>) ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleAllUncaughtException(Exception exception, WebRequest request) {
        LOGGER.error(exception.getMessage());
        return "Oops something went wrong, please contact your administrator";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return "Server cannot process your request";
    }

}
