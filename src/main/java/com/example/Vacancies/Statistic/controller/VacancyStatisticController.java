package com.example.Vacancies.Statistic.controller;
import com.example.Vacancies.Statistic.model.VacancyCard;
import com.example.Vacancies.Statistic.service.VacancyService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.*;


//public class VacancyStatisticController {
//
//    private final VacancyService vacancyService;
//
//    public VacancyStatisticController(final VacancyService vacancyService) {
//        this.vacancyService = vacancyService;
//    }
//
//
//
//    @GetMapping("/recruiter_menu")
//    public String recruiterMenu() {
//        return "recruiter_menu";
//    }
//
//
//
//    @Transactional
//    @PostMapping("/save_vacancy_add_event")
//    public String saveVacancyAddEvent(@ModelAttribute("vacancyInfoCandidate") VacancyCard vacancyCard, Model model) throws GeneralSecurityException, IOException, URISyntaxException {
//        vacancyService.saveVacancy(vacancyCard);
//        return "index";
//    }
//
//    @Transactional
//    @PostMapping("/save_vacancy")
//    public String saveVacancy(@ModelAttribute("vacancyInfoCandidate") VacancyCard vacancyCard, Model model) throws GeneralSecurityException, IOException, URISyntaxException {
//        vacancyService.saveVacancy(vacancyCard);
//        return "index";
//    }
//
//
//    @PostMapping("/delete")
//    public String deleteVacancies(@RequestParam(value = "selectedIds", required = false) List<String> selectedIds) {
//        if (selectedIds != null && !selectedIds.isEmpty()) {
//            vacancyService.deleteVacanciesByIds(selectedIds);
//        }
//        return "redirect:/my_vacancies";
//    }
//
//    @GetMapping("/about_us")
//    public String aboutUs() {
//        return "about_us";
//    }
//
//
//    @GetMapping("/contacts")
//    public String contacts() {
//        return "contacts";
//    }
//
//    @PostMapping("/generate_excel")
//    public ResponseEntity<InputStreamResource> generateExcel(@RequestParam(value = "selectedIds", required = false)
//                                                                 List<String> selectedIds) throws Exception {
//        byte[] data = vacancyService.generateExcelReport(selectedIds);
//
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=vacancies.xlsx");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(new InputStreamResource(in));
//    }
//
//}
