package br.com.treinaweb.twjobs.api.jobs.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.treinaweb.twjobs.api.jobs.dtos.CandidateResponse;
import br.com.treinaweb.twjobs.core.models.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelMapperCandidateMapper implements CandidateMapper {

    private final ModelMapper modelMapper;

    @Override
    public CandidateResponse toCandidateResponse(User candidate) {
        return modelMapper.map(candidate, CandidateResponse.class);
    }
    
}
