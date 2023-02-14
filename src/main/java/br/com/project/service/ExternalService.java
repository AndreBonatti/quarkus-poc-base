package br.com.project.service;

import br.com.project.model.external.rest.out.BrasilApiBanksV1Dto;

import java.util.List;

public interface ExternalService {

    List<BrasilApiBanksV1Dto> findAll();

    Object findById(Integer code);
}
