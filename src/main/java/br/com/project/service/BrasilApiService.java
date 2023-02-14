package br.com.project.service;

import br.com.project.model.external.rest.out.BrasilApiBanksV1Dto;

import java.util.List;

public interface BrasilApiService {

    List<BrasilApiBanksV1Dto> banksV1();

    BrasilApiBanksV1Dto banksV1FindbyId(Integer code);

}
