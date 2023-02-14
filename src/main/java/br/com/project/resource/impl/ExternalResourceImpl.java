package br.com.project.resource.impl;


import br.com.project.resource.ExternalResource;
import br.com.project.service.imp.ExternalServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Slf4j
public class ExternalResourceImpl implements ExternalResource {

    @Inject
    ExternalServiceImpl externalService;

    @Override
    public Response findAll() {
        return Response.ok(externalService.findAll()).build();
    }

    @Override
    public Response findByID(Integer code) {
        return Response.ok(externalService.findById(code)).build();
    }

}
