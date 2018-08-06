package com.epam.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * The type Exception handler controller.
 */
@ControllerAdvice
public class ExceptionHandlerController {
    private static final String EXCEPTION = "exception";

    /**
     * Default error handler model and view.
     *
     * @param e the e
     * @return the model and view
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView(EXCEPTION);
        modelAndView.addObject(EXCEPTION, e);
        return modelAndView;
    }
}