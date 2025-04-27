package com.adote.api.infra.exception.formulario;

import com.adote.api.core.exceptions.formulario.FormularioAlreadyExistsException;
import com.adote.api.core.exceptions.oraganizacao.CnpjAlreadyExistsException;
import com.adote.api.infra.exception.organizacao.OrganizacaoExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FormularioExceptionHandler {

    @ExceptionHandler(FormularioAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(FormularioAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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
