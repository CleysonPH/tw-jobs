package br.com.treinaweb.twjobs.api.jobs.mappers;

import br.com.treinaweb.twjobs.api.jobs.dtos.CandidateResponse;
import br.com.treinaweb.twjobs.core.models.User;

public interface CandidateMapper {

    CandidateResponse toCandidateResponse(User candidate);
    
}
