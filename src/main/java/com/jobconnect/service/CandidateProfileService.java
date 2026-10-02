package com.jobconnect.service;

import com.jobconnect.dto.CandidateProfileRequest;
import com.jobconnect.dto.CandidateProfileResponse;
import com.jobconnect.entity.CandidateProfile;
import com.jobconnect.entity.Skill;
import com.jobconnect.entity.User;
import com.jobconnect.exception.DuplicateResourceException;
import com.jobconnect.exception.ResourceNotFoundException;
import com.jobconnect.repository.CandidateProfileRepository;
import com.jobconnect.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final SkillRepository skillRepository;
    private final UserService userService;

    public CandidateProfileService(CandidateProfileRepository candidateProfileRepository,
                                    SkillRepository skillRepository,
                                    UserService userService) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.skillRepository = skillRepository;
        this.userService = userService;
    }

    @Transactional
    public CandidateProfileResponse createProfile(String currentUserEmail, CandidateProfileRequest request) {
        User user = userService.getUserByEmailOrThrow(currentUserEmail);

        if (candidateProfileRepository.existsByUser(user)) {
            throw new DuplicateResourceException("A candidate profile already exists for this user");
        }

        CandidateProfile profile = new CandidateProfile();
        profile.setUser(user);
        applyRequest(profile, request);

        return CandidateProfileResponse.from(candidateProfileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    public CandidateProfileResponse getMyProfile(String currentUserEmail) {
        User user = userService.getUserByEmailOrThrow(currentUserEmail);
        CandidateProfile profile = candidateProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No candidate profile found for this user"));
        return CandidateProfileResponse.from(profile);
    }

    @Transactional
    public CandidateProfileResponse updateMyProfile(String currentUserEmail, CandidateProfileRequest request) {
        User user = userService.getUserByEmailOrThrow(currentUserEmail);
        CandidateProfile profile = candidateProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No candidate profile found for this user"));

        applyRequest(profile, request);
        return CandidateProfileResponse.from(candidateProfileRepository.save(profile));
    }

    @Transactional
    public CandidateProfileResponse updateResumeUrl(String currentUserEmail, String resumeUrl) {
        User user = userService.getUserByEmailOrThrow(currentUserEmail);
        CandidateProfile profile = candidateProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No candidate profile found for this user"));
        profile.setResumeUrl(resumeUrl);
        return CandidateProfileResponse.from(candidateProfileRepository.save(profile));
    }

    @Transactional
    public CandidateProfileResponse addSkill(Long candidateId, Long skillId) {
        CandidateProfile profile = candidateProfileRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found: " + candidateId));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found: " + skillId));

        profile.getSkills().add(skill);
        return CandidateProfileResponse.from(candidateProfileRepository.save(profile));
    }

    @Transactional
    public CandidateProfileResponse removeSkill(Long candidateId, Long skillId) {
        CandidateProfile profile = candidateProfileRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found: " + candidateId));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found: " + skillId));

        profile.getSkills().remove(skill);
        return CandidateProfileResponse.from(candidateProfileRepository.save(profile));
    }

    public CandidateProfile getByIdOrThrow(Long id) {
        return candidateProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found: " + id));
    }

    private void applyRequest(CandidateProfile profile, CandidateProfileRequest request) {
        profile.setHeadline(request.getHeadline());
        profile.setSummary(request.getSummary());
        profile.setLocation(request.getLocation());
        profile.setEducation(request.getEducation());
        profile.setExperienceYears(request.getExperienceYears());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());

        if (request.getSkillIds() != null) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(request.getSkillIds()));
            profile.setSkills(skills);
        }
    }
}