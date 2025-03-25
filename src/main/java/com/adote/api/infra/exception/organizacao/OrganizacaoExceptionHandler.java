package com.adote.api.infra.exception.organizacao;

import com.adote.api.core.exceptions.oraganizacao.CnpjAlreadyExistsException;
import com.adote.api.core.exceptions.oraganizacao.OrganizacaoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrganizacaoExceptionHandler {

    @ExceptionHandler(OrganizacaoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(OrganizacaoNotFoundException ex) {
        OrganizacaoExceptionHandler.ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CnpjAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CnpjAlreadyExistsException ex) {
        OrganizacaoExceptionHandler.ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    public static class ErrorResponse {
        private final int status;
        private final String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

}
