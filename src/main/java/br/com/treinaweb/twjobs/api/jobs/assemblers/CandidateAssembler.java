package br.com.treinaweb.twjobs.api.jobs.assemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;

import br.com.treinaweb.twjobs.api.jobs.dtos.CandidateResponse;

public class CandidateAssembler implements SimpleRepresentationModelAssembler<CandidateResponse> {

    @Override
    public void addLinks(EntityModel<CandidateResponse> resource) {
        
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<CandidateResponse>> resources) {
        
    }
    
}
