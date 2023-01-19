package br.gov.caixa.negociosdigitais.resource.impl;

import br.gov.caixa.negociosdigitais.config.resilience.CircuitBreakerFallback;
import br.gov.caixa.negociosdigitais.model.resource.in.ClienteInDto;
import br.gov.caixa.negociosdigitais.model.resource.in.parameters.Pageable;
import br.gov.caixa.negociosdigitais.resource.ClienteResource;
import br.gov.caixa.negociosdigitais.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Slf4j
public class ClienteResourceImpl implements ClienteResource {

    @Inject
    ClienteService clienteService;

    @Override
    public Response findByID(Long id) {
        return Response.ok(clienteService.findById(id)).build();
    }

    // == Não funciona anotação na interface
    @Timeout
    @Fallback(CircuitBreakerFallback.class)
    @CircuitBreaker(requestVolumeThreshold = 4, delay = 3000L, successThreshold = 2)
    @Override
    public Response findAll(Pageable pageable) {
        return Response.ok(clienteService.findAllPageable(pageable)).build();
    }

    @Override
    public Response createCliente(ClienteInDto clienteDTO) {

        final URI processIdUri = UriBuilder.fromResource(ClienteResource.class)
                .path("{id}").build(clienteService.createdCliente(clienteDTO));

        return Response.created(processIdUri).build();
    }

    @Override
    public Response updateCliente(Long id, ClienteInDto clienteInDto) {
        return Response.ok(clienteService.updateCliente(id, clienteInDto)).build();
    }

    @Override
    public Response deleteCliente(Long id) {

        clienteService.deleteCliente(id);
        return Response.noContent().build();
    }

    @Override
    public Response massaTeste(Long num) {
        clienteService.massaTeste(num);
        return Response.ok().build();
    }
}
