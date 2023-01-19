package br.gov.caixa.negociosdigitais.service;

import br.gov.caixa.negociosdigitais.config.exception.BusinessException;
import br.gov.caixa.negociosdigitais.config.exception.EnumBusinessExceptionMessage;
import br.gov.caixa.negociosdigitais.model.entity.Cliente;
import br.gov.caixa.negociosdigitais.model.resource.in.ClienteInDto;
import br.gov.caixa.negociosdigitais.model.resource.in.parameters.Pageable;
import br.gov.caixa.negociosdigitais.model.resource.out.ResponsePageable;
import br.gov.caixa.negociosdigitais.repository.ClienteRepository;
import br.gov.caixa.negociosdigitais.utils.OperationSQL;
import br.gov.caixa.negociosdigitais.utils.UtilsToData;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository repository;

    public Cliente findById(Long id) {

        var cliente = repository.findByIdOptional(id);

        if (cliente.isPresent()) return cliente.get();

        throw new BusinessException(EnumBusinessExceptionMessage.NOT_FOUND);
    }

    public ResponsePageable findAllPageable(Pageable pageable) {

        var orderField = Optional.ofNullable(pageable.getOrderField());
        var filterField = Optional.ofNullable(pageable.getFilterField());

        PanacheQuery<Cliente> result;

        var page = Page.of(pageable.getPage(), pageable.getPageSize()); // campos obrigatórios

        Sort sort = null;
        if (orderField.isPresent()) {
            sort = Sort.by(orderField.get(), pageable.getOrderSort());
        }

        if (filterField.isPresent()) {
            var query = OperationSQL.queryBuild(pageable.getFilterField(), pageable.getFilterOperation());
            var param = OperationSQL.paramsBuild(Cliente.class, pageable.getFilterField(), pageable.getFilterValue(), pageable.getFilterOperation());

            result = (sort != null) ?
                    repository.find(query, sort, param).page(page) :
                    repository.find(query, pageable.getFilterValue()).page(page);
        } else {
            result = (sort != null) ?
                    repository.findAll(sort).page(page) :
                    repository.findAll().page(page);
        }

        ResponsePageable respPageable = new ResponsePageable();

        respPageable.setContent(Arrays.asList(result.list().toArray()));
        respPageable.setPageCount(result.pageCount());
        respPageable.setCount(result.count());

        return respPageable;
    }

    public Long createdCliente(ClienteInDto clienteInDto) {

        Cliente cliente;
        try {
            cliente = Cliente.builder()
                    .nome(clienteInDto.getNome())
                    .aniversario(UtilsToData.formatStringParaDataHora(clienteInDto.getAniversario()))
                    .anotacao(clienteInDto.getAnotacao())
                    .build();

        } catch (ParseException e) {
            log.error("Erro formato data", e);
            throw new BusinessException(EnumBusinessExceptionMessage.BAD_REQUEST);
        }

        return repository.createdCliente(cliente);
    }

    @Transactional
    public Cliente updateCliente(Long id, ClienteInDto clienteInDto) {

        Cliente entity = this.findById(id);

        entity.setNome(clienteInDto.getNome());
        try {
            entity.setAniversario(UtilsToData.formatStringParaDataHora(clienteInDto.getAniversario()));
        } catch (ParseException e) {
            log.error("Erro formato data ", e);
            throw new BusinessException(EnumBusinessExceptionMessage.BAD_REQUEST);
        }
        entity.setAnotacao(clienteInDto.getAnotacao());

        return entity;
    }

    public void deleteCliente(Long id) {
        Cliente entity = this.findById(id);
        repository.deleteCliente(entity);
    }

    public void massaTeste(Long numeroClientes) {
        repository.createMassaDados(numeroClientes);
    }
}
