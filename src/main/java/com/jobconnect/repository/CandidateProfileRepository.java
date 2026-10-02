package com.jobconnect.repository;

import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {
    Optional<CandidateProfile> findByUser(User user);
    Optional<CandidateProfile> findByUserId(Long userId);
    boolean existsByUser(User user);
}
