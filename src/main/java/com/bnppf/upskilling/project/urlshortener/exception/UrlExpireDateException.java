package com.bnppf.upskilling.project.urlshortener.exception;

import com.bnppf.upskilling.project.urlshortener.controller.RedirectController;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UrlExpireDateException extends ResponseEntityExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<UrlExpError> customHandleNotFound
            (Exception ex, WebRequest request) {

        UrlExpError errors = new UrlExpError();
        errors.setError("Url Date expired");
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

}
}
