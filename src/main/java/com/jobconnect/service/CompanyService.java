package com.jobconnect.service;

import com.jobconnect.dto.CompanyRequest;
import com.jobconnect.dto.CompanyResponse;
import com.jobconnect.entity.Company;
import com.jobconnect.entity.User;
import com.jobconnect.exception.DuplicateResourceException;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.exception.UnauthorizedActionException;
import com.jobconnect.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;

    public CompanyService(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    @Transactional
    public CompanyResponse create(String currentUserEmail, CompanyRequest request) {
        User recruiter = userService.getUserByEmailOrThrow(currentUserEmail);

        if (companyRepository.existsByRecruiter(recruiter)) {
            throw new DuplicateResourceException("This recruiter already has a company profile");
        }

        Company company = new Company();
        company.setRecruiter(recruiter);
        applyRequest(company, request);

        return CompanyResponse.from(companyRepository.save(company));
    }

    public CompanyResponse getById(Long id) {
        return CompanyResponse.from(getByIdOrThrow(id));
    }

    public List<CompanyResponse> findAll() {
        return companyRepository.findAll().stream().map(CompanyResponse::from).toList();
    }

    @Transactional
    public CompanyResponse update(String currentUserEmail, Long id, CompanyRequest request) {
        Company company = getByIdOrThrow(id);
        assertOwnership(company, currentUserEmail);

        applyRequest(company, request);
        return CompanyResponse.from(companyRepository.save(company));
    }

    public Company getByIdOrThrow(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + id));
    }

    /** Milestone 5 business rule: a recruiter can only touch their own company/jobs. */
    public void assertOwnership(Company company, String currentUserEmail) {
        if (!company.getRecruiter().getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new UnauthorizedActionException("You do not own this company profile");
        }
    }

    private void applyRequest(Company company, CompanyRequest request) {
        company.setCompanyName(request.getCompanyName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setIndustry(request.getIndustry());
    }
}
