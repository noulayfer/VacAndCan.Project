package com.example.Vacancies.Statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;

@SpringBootApplication
public class VacanciesStatisticApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacanciesStatisticApplication.class, args);
	}
}
