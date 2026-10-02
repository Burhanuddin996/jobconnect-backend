package com.jobconnect.service;

import com.jobconnect.dto.ApplicationResponse;
import com.jobconnect.entity.*;
import com.jobconnect.exception.DuplicateResourceException;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.exception.UnauthorizedActionException;
import com.jobconnect.repository.ApplicationRepository;
import com.jobconnect.repository.CandidateProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobService jobService;
    private final UserService userService;

    public ApplicationService(ApplicationRepository applicationRepository,
                               CandidateProfileRepository candidateProfileRepository,
                               JobService jobService,
                               UserService userService) {
        this.applicationRepository = applicationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.jobService = jobService;
        this.userService = userService;
    }

    @Transactional
    public ApplicationResponse apply(String currentUserEmail, Long jobId) {
        User user = userService.getUserByEmailOrThrow(currentUserEmail);
        CandidateProfile candidate = candidateProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Create a candidate profile before applying to jobs"));

        Job job = jobService.getByIdOrThrow(jobId);

        // Milestone 7 business rule: no double-applying to the same job.
        if (applicationRepository.existsByJobAndCandidate(job, candidate)) {
            throw new DuplicateResourceException("You have already applied to this job");
        }

        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.APPLIED);

        return ApplicationResponse.from(applicationRepository.save(application));
    }

    public Page<ApplicationResponse> getMyApplications(String currentUserEmail, Pageable pageable) {
        User user = userService.getUserByEmailOrThrow(currentUserEmail);
        CandidateProfile candidate = candidateProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No candidate profile found for this user"));

        return applicationRepository.findByCandidate(candidate, pageable).map(ApplicationResponse::from);
    }

    /** Recruiter viewing everyone who applied to one of their jobs. */
    public List<ApplicationResponse> getApplicationsForJob(String currentUserEmail, Long jobId) {
        Job job = jobService.getByIdOrThrow(jobId);

        if (!job.getCompany().getRecruiter().getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new UnauthorizedActionException("You do not manage the job this application belongs to");
        }

        return applicationRepository.findByJobId(jobId).stream().map(ApplicationResponse::from).toList();
    }

    public ApplicationResponse getById(Long id) {
        return ApplicationResponse.from(getByIdOrThrow(id));
    }

    @Transactional
    public ApplicationResponse updateStatus(String currentUserEmail, Long id, ApplicationStatus status) {
        Application application = getByIdOrThrow(id);
        String recruiterEmail = application.getJob().getCompany().getRecruiter().getEmail();

        if (!recruiterEmail.equalsIgnoreCase(currentUserEmail)) {
            throw new UnauthorizedActionException("Only the recruiter who owns this job can update its applications");
        }

        application.setStatus(status);
        return ApplicationResponse.from(applicationRepository.save(application));
    }

    public Application getByIdOrThrow(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + id));
    }
}
