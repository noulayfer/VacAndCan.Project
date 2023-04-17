package com.example.Vacancies.Statistic.repository;

import com.example.Vacancies.Statistic.model.VacancyCard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<VacancyCard, String> {
    List<VacancyCard> findByNameContainingIgnoreCase(String name);


    Page<VacancyCard> findByUserId(String userId, Pageable pageable);
}
