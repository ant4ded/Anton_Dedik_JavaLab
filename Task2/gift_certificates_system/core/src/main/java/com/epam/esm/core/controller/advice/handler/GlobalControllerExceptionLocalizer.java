package com.epam.esm.core.controller.advice.handler;

import com.epam.esm.core.controller.advice.util.ExceptionMessageLocaleTranslator;
import com.epam.esm.core.controller.advice.util.ExceptionMessagePropertyKey;
import com.epam.esm.core.service.DuplicateEntityException;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.core.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionLocalizer {
    public static final String DEFAULT_ERROR_VIEW = "redirect:/error";
    private final ExceptionMessageLocaleTranslator translator;

    @Autowired
    public GlobalControllerExceptionLocalizer(ExceptionMessageLocaleTranslator translator) {
        this.translator = translator;
    }

    @ExceptionHandler(value = ServiceException.class)
    public ModelAndView handleServiceException(HttpServletRequest req, Exception e) {
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

        ModelAndView model = new ModelAndView();
        model.addObject("msg", message);
//        model.addObject("url", req.getRequestURL());
        model.setViewName(DEFAULT_ERROR_VIEW);
        return model;
    }

    @ExceptionHandler(value = InvalidEntityFieldException.class)
    public ModelAndView handleInvalidEntityField(HttpServletRequest req, Exception e) {
        String message = e.getMessage();
        if (message.contains(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD)) {
            message = message.replace(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD,
                    translator.toLocale(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD));
        }

        ModelAndView model = new ModelAndView();
        model.addObject("msg", message);
//        model.addObject("url", req.getRequestURL());
        model.setViewName(DEFAULT_ERROR_VIEW);
        return model;
    }

    @ExceptionHandler(value = DuplicateEntityException.class)
    public ModelAndView handleDuplicateEntity(HttpServletRequest req, Exception e) {
        String message = e.getMessage();
        if (message.contains(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_EXISTS)) {
            message = message.replace(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_EXISTS,
                    translator.toLocale(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_EXISTS));
        }
        if (message.contains(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_NAME)) {
            message = message.replace(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_NAME,
                    translator.toLocale(ExceptionMessagePropertyKey.DUPLICATE_ENTITY_NAME));
        }

        ModelAndView model = new ModelAndView();
        model.addObject("msg", message);
//        model.addObject("url", req.getRequestURL());
        model.setViewName(DEFAULT_ERROR_VIEW);
        return model;
    }
}
