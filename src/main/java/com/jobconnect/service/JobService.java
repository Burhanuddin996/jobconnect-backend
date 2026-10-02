package com.jobconnect.service;

import com.jobconnect.dto.JobRequest;
import com.jobconnect.dto.JobResponse;
import com.jobconnect.entity.Company;
import com.jobconnect.entity.Job;
import com.jobconnect.entity.JobType;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.specification.JobSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyService companyService;

    public JobService(JobRepository jobRepository, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
    }

    @Transactional
    public JobResponse create(String currentUserEmail, Long companyId, JobRequest request) {
        Company company = companyService.getByIdOrThrow(companyId);
        companyService.assertOwnership(company, currentUserEmail);

        Job job = new Job();
        job.setCompany(company);
        applyRequest(job, request);

        return JobResponse.from(jobRepository.save(job));
    }

    public JobResponse getById(Long id) {
        return JobResponse.from(getByIdOrThrow(id));
    }

    public Page<JobResponse> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable).map(JobResponse::from);
    }

    /** Milestone 6: search by keyword/location and filter by salary/type, with pagination. */
    public Page<JobResponse> search(String keyword, String location, Double minSalary,
                                     Double maxSalary, JobType jobType, Pageable pageable) {
        var spec = JobSpecification.build(keyword, location, minSalary, maxSalary, jobType, true);
        return jobRepository.findAll(spec, pageable).map(JobResponse::from);
    }

    @Transactional
    public JobResponse update(String currentUserEmail, Long id, JobRequest request) {
        Job job = getByIdOrThrow(id);
        companyService.assertOwnership(job.getCompany(), currentUserEmail);

        applyRequest(job, request);
        return JobResponse.from(jobRepository.save(job));
    }

    @Transactional
    public void delete(String currentUserEmail, Long id) {
        Job job = getByIdOrThrow(id);
        companyService.assertOwnership(job.getCompany(), currentUserEmail);
        jobRepository.delete(job);
    }

    public Job getByIdOrThrow(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found: " + id));
    }

    private void applyRequest(Job job, JobRequest request) {
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setExperienceMin(request.getExperienceMin());
        job.setJobType(request.getJobType());
        if (request.getActive() != null) {
            job.setActive(request.getActive());
        }
    }
}
