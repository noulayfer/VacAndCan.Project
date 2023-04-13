package com.example.Vacancies.Statistic.controller;

import com.example.Vacancies.Statistic.model.VacancyCard;
import com.example.Vacancies.Statistic.service.VacancyService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vacancies")
public class VacancyCardController {
    private final VacancyService vacancyService;

    public VacancyCardController(final VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("/all-elements")
    public String showMyVacancies(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "") String name,
                                  Model model) {

        Page<VacancyCard> vacanciesPage = vacancyService.getPaginatedAndSortedVacancies(page, size);
        List<VacancyCard> vacancies = vacanciesPage.getContent();
        int totalPages = vacancies.isEmpty() ? 1 : vacanciesPage.getTotalPages();
        int currentPage = vacancies.isEmpty() ? 0 : page;
        model.addAttribute("all", vacancies);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", size);
        model.addAttribute("VacancyCard", VacancyCard.class);
        model.addAttribute("selectedIds", new ArrayList<String>());
        return "all-elements";
    }

    @GetMapping("/all-elements/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<VacancyCard> vacancies = vacancyService.searchByName(name);
        model.addAttribute("all", vacancies);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("size", 10);
        return "all-elements";
    }

    @GetMapping("/menu")
    public String vacanciesMenu() {
        return "vacancies_menu";
    }

    @GetMapping("/create")
    public String createVacancy(Model model) {
        model.addAttribute("vacancyCard", new VacancyCard());
        model.addAttribute("bindingResult", new BeanPropertyBindingResult(new VacancyCard(), "vacancyCard"));
        return "create_vacancy";
    }

    @Transactional
    @PostMapping("/create/save")
    public String saveVacancy(@Valid @ModelAttribute("vacancyInfoCandidate") VacancyCard vacancyCard,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancyCard", vacancyCard);
            model.addAttribute("bindingResult", bindingResult);
            return "create_vacancy";
        }

        vacancyService.saveVacancy(vacancyCard);
        return "redirect:/vacancies/all-elements";
    }

    @PostMapping("/all-elements/generate-excel")
    public ResponseEntity<InputStreamResource> generateExcel(@RequestParam(value = "selectedIds", required = false)
                                                             List<String> selectedIds) throws Exception {
        byte[] data = vacancyService.generateExcelReport(selectedIds);

        ByteArrayInputStream in = new ByteArrayInputStream(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=vacancies.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

    @DeleteMapping ("/all-elements/delete")
    public String deleteVacancies(@RequestParam(value = "selectedIds", required = false) List<String> selectedIds) {
        if (selectedIds != null && !selectedIds.isEmpty()) {
            vacancyService.deleteVacanciesByIds(selectedIds);
        }
        return "redirect:/vacancies/all-elements";
    }
}
