package br.com.treinaweb.twjobs.api.jobs.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinaweb.twjobs.api.jobs.dtos.CandidateResponse;
import br.com.treinaweb.twjobs.api.jobs.mappers.CandidateMapper;
import br.com.treinaweb.twjobs.core.exceptions.JobNotFoundException;
import br.com.treinaweb.twjobs.core.permissions.TWJobsPermissions;
import br.com.treinaweb.twjobs.core.repositories.JobRepository;
import br.com.treinaweb.twjobs.core.repositories.UserRepository;
import br.com.treinaweb.twjobs.core.services.auth.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs/{id}")
public class CanidateRestController {

    private final JobRepository jobRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final CandidateMapper candidateMapper;
    private final PagedResourcesAssembler<CandidateResponse> pagedResourcesAssembler;

    @PostMapping("/apply")
    @TWJobsPermissions.IsCandidate
    public ResponseEntity<?> apply(@PathVariable Long id) {
        var job = jobRepository.findById(id)
            .orElseThrow(JobNotFoundException::new);
        job.getCandidates().add(securityService.getCurrentUser());
        jobRepository.save(job);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/candidates")
    @TWJobsPermissions.IsOwner
    public CollectionModel<EntityModel<CandidateResponse>> getCandidates(@PathVariable Long id, Pageable pageable) {
        var job = jobRepository.findById(id)
            .orElseThrow(JobNotFoundException::new);
        var candidates = userRepository.findByAppliedJobs(job, pageable)
            .map(candidateMapper::toCandidateResponse);
        return pagedResourcesAssembler.toModel(candidates);
    }
    
    
}
