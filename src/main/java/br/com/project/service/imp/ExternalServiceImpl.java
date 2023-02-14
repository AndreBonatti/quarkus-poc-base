package br.com.project.service.imp;

import br.com.project.model.external.rest.out.BrasilApiBanksV1Dto;
import br.com.project.service.BrasilApiService;
import br.com.project.service.ExternalService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@Slf4j
@ApplicationScoped
public class ExternalServiceImpl implements ExternalService {

    @Inject
    BrasilApiService brasilApiService;

    @Override
    public List<BrasilApiBanksV1Dto> findAll() {
        return brasilApiService.banksV1();
    }

    @Override
    public BrasilApiBanksV1Dto findById(Integer code) {
        return brasilApiService.banksV1FindbyId(code);
    }
}
