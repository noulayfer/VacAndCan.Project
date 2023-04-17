package com.example.Vacancies.Statistic.repository;

import com.example.Vacancies.Statistic.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String name);
    User findByEmail(String email);
}
