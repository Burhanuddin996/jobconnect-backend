package com.jobconnect.repository;

import com.jobconnect.entity.Application;
import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByJobAndCandidate(Job job, CandidateProfile candidate);
    Optional<Application> findByJobAndCandidate(Job job, CandidateProfile candidate);
    Page<Application> findByCandidate(CandidateProfile candidate, Pageable pageable);
    List<Application> findByJobId(Long jobId);
    Page<Application> findByJobId(Long jobId, Pageable pageable);
}
