package com.epam.esm.core.controller.advice.handler;

import com.epam.esm.core.controller.advice.util.ExceptionMessageLocaleTranslator;
import com.epam.esm.core.controller.advice.util.ExceptionMessagePropertyKey;
import com.epam.esm.core.service.DuplicateEntityException;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.core.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionLocalizer {
    private final ExceptionMessageLocaleTranslator translator;

    @Autowired
    public GlobalControllerExceptionLocalizer(ExceptionMessageLocaleTranslator translator) {
        this.translator = translator;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException e) {
        String message = e.getMessage();
        if (message.contains(ExceptionMessagePropertyKey.SERVICE_ENTITY_NULL)) {
            message = message.replace(ExceptionMessagePropertyKey.SERVICE_ENTITY_NULL,
                    translator.toLocale(ExceptionMessagePropertyKey.SERVICE_ENTITY_NULL));
        }
        if (message.contains(ExceptionMessagePropertyKey.SERVICE_NOTHING_BY_NAME)) {
            message = message.replace(ExceptionMessagePropertyKey.SERVICE_NOTHING_BY_NAME,
                    translator.toLocale(ExceptionMessagePropertyKey.SERVICE_NOTHING_BY_NAME));
        }
        if (message.contains(ExceptionMessagePropertyKey.SERVICE_SOMETHING_WRONG)) {
            message = message.replace(ExceptionMessagePropertyKey.SERVICE_SOMETHING_WRONG,
                    translator.toLocale(ExceptionMessagePropertyKey.SERVICE_SOMETHING_WRONG));
        }

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(InvalidEntityFieldException.class)
    public ResponseEntity<Object> handleInvalidEntityField(InvalidEntityFieldException e) {
        String message = e.getMessage();
        if (message.contains(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD)) {
            message = message.replace(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD,
                    translator.toLocale(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD));
        }

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Object> handleDuplicateEntity(DuplicateEntityException e) {
        String message = e.getMessage();
        if (message.contains(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_EXISTS)) {
            message = message.replace(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_EXISTS,
                    translator.toLocale(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_EXISTS));
        }
        if (message.contains(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_NAME)) {
            message = message.replace(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_NAME,
                    translator.toLocale(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_NAME));
        }

        return ResponseEntity.badRequest().body(message);
    }
}
