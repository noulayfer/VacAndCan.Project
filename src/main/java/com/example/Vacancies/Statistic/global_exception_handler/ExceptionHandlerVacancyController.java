package com.example.Vacancies.Statistic.global_exception_handler;

import com.example.Vacancies.Statistic.controller.VacancyCardController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {VacancyCardController.class})
public class ExceptionHandlerVacancyController {

    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler() {
        return "error-page";
    }
}
