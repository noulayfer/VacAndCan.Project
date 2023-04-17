package com.example.Vacancies.Statistic.service;


import com.example.Vacancies.Statistic.model.VacancyCard;
import com.example.Vacancies.Statistic.repository.VacancyRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {
    private final VacancyRepository vacancyRepository;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public VacancyService(final VacancyRepository vacancyRepository,
                          final UserDetailsServiceImpl userDetailsService) {
        this.vacancyRepository = vacancyRepository;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public void saveVacancy(final VacancyCard vacancyCard, String userId) {
        vacancyCard.setUser(userDetailsService.getUserById(userId));
        vacancyRepository.save(vacancyCard);
    }

    public List<VacancyCard> getAll() {
        return vacancyRepository.findAll();
    }

    public VacancyCard getById(String id) {
        return vacancyRepository.getById(id);
    }

    public void updateVacancyCard(String id, VacancyCard vacancyCard) {
        Optional<VacancyCard> optionalVacancy = vacancyRepository.findById(id);

        if (optionalVacancy.isPresent()) {
            VacancyCard vacancy = optionalVacancy.get();
            vacancy.setName(vacancyCard.getName());
            vacancy.setDateOfSubmittingForVacancy(vacancyCard.getDateOfSubmittingForVacancy());
            vacancy.setDateOfAppointment(vacancyCard.getDateOfAppointment());
            vacancy.setStatus(vacancyCard.getStatus());
            vacancy.setText(vacancyCard.getText());
            vacancyRepository.save(vacancy);
        }
    }

    public Page<VacancyCard> getPaginatedAndSortedVacancies(int page, int size, String userId) {
        Pageable pageable = PageRequest.of(page, size);
        return vacancyRepository.findByUserId(userId, pageable);
    }


    public List<VacancyCard> searchByName(String name, String userId) {
        return vacancyRepository.findByNameAndUserIdContainingIgnoreCase(name, userId);
    }

    public List<VacancyCard> getByIds(List<String> ids) {
        return vacancyRepository.findAllById(ids);
    }

    public void deleteVacanciesByIds(List<String> ids) {
        vacancyRepository.deleteAllById(ids);
    }

    public byte[] generateExcelReport(List<String> ids) throws Exception {
        List<VacancyCard> vacancies = getByIds(ids);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Vacancies");
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Date Of Submitting For Vacancy");
        header.createCell(2).setCellValue("Date Of Appointment");
        header.createCell(3).setCellValue("Status");
        header.createCell(4).setCellValue("Text");

        int rowNum = 1;
        for (VacancyCard vacancy : vacancies) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(vacancy.getName());
            XSSFCell cellWithDate1 = row.createCell(1);
            Optional.ofNullable(vacancy.getDateOfSubmittingForVacancy()).
                    ifPresent(date ->
                            cellWithDate1.setCellValue(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd / HH:mm"))));
            XSSFCell cellWithDate2 = row.createCell(2);
            Optional.ofNullable(vacancy.getDateOfAppointment()).
                    ifPresent(date ->
                            cellWithDate2.setCellValue(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm"))));
            row.createCell(3).setCellValue(vacancy.getStatus().toString());
            row.createCell(4).setCellValue(vacancy.getText());
        }


        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);

        return out.toByteArray();
    }
}
