package com.example.Vacancies.Statistic.repository;

import com.example.Vacancies.Statistic.model.VacancyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<VacancyCard, String> {
    List<VacancyCard> findByNameContainingIgnoreCase(String name);
}
