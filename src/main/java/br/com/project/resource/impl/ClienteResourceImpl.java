package br.com.project.resource.impl;

import br.com.project.config.resilience.CircuitBreakerFallback;
import br.com.project.model.resource.in.ClienteInDto;
import br.com.project.model.resource.in.parameters.Pageable;
import br.com.project.resource.ClienteResource;
import br.com.project.service.imp.ClienteServiceImpl;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
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

    private final MeterRegistry registry;

    // Update the constructor to create the gauge
    ClienteResourceImpl(MeterRegistry registry) {
        this.registry = registry;
    }

    @Inject
    ClienteServiceImpl clienteService;

    @Override
    public Response findByID(Long id) {
        Timer timer = registry.timer("cliente.resource.findbyid");
        return timer.record(() ->
                Response.ok(clienteService.findById(id)).build()
        );
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
        registry.counter("cliente.resource.create.cliente", "type", "count-acess").increment();
        final URI processIdUri = UriBuilder.fromResource(ClienteResource.class)
                .path("{id}").build(clienteService.createdCliente(clienteDTO));

        return Response.created(processIdUri).build();
    }

    @Override
    public Response updateCliente(Long id, ClienteInDto clienteInDto) {
        registry.counter("cliente.resource.update.cliente", "type", "count-acess").increment();
        return Response.ok(clienteService.updateCliente(id, clienteInDto)).build();
    }

    @Override
    public Response deleteCliente(Long id) {
        registry.counter("cliente.resource.delete.cliente", "type", "count-acess").increment();

        clienteService.deleteCliente(id);
        return Response.noContent().build();
    }

    @Override
    public Response massaTeste(Long num) {
        clienteService.massaTeste(num);
        return Response.ok().build();
    }
}
