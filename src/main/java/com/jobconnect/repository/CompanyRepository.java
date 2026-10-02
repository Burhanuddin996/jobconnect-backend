package com.jobconnect.repository;

import com.jobconnect.entity.Company;
import com.jobconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByRecruiter(User recruiter);
    boolean existsByRecruiter(User recruiter);
}
