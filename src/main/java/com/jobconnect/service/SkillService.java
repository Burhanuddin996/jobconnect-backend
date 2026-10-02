package com.jobconnect.service;

import com.jobconnect.dto.SkillRequest;
import com.jobconnect.dto.SkillResponse;
import com.jobconnect.entity.Skill;
import com.jobconnect.exception.DuplicateResourceException;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Transactional
    public SkillResponse create(SkillRequest request) {
        if (skillRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("Skill already exists: " + request.getName());
        }
        Skill skill = new Skill(request.getName());
        return SkillResponse.from(skillRepository.save(skill));
    }

    public List<SkillResponse> findAll() {
        return skillRepository.findAll().stream().map(SkillResponse::from).toList();
    }

    public Skill getByIdOrThrow(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found: " + id));
    }
}
