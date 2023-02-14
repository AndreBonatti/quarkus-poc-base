package br.com.project.service;

import br.com.project.model.resource.in.ClienteInDto;
import br.com.project.model.resource.in.parameters.Pageable;
import br.com.project.model.entity.Cliente;
import br.com.project.model.resource.out.ResponsePageable;

public interface ClienteService {

    Cliente findById(Long id);

    ResponsePageable findAllPageable(Pageable pageable);

    Long createdCliente(ClienteInDto clienteInDto);

    Cliente updateCliente(Long id, ClienteInDto clienteInDto);

    void deleteCliente(Long id);

    void massaTeste(Long numeroClientes);

}
