package br.gov.caixa.negociosdigitais.service;

import br.gov.caixa.negociosdigitais.config.exception.BusinessException;
import br.gov.caixa.negociosdigitais.model.entity.Cliente;
import br.gov.caixa.negociosdigitais.model.resource.in.ClienteInDto;
import br.gov.caixa.negociosdigitais.model.resource.in.parameters.Pageable;
import br.gov.caixa.negociosdigitais.repository.ClienteRepository;
import br.gov.caixa.negociosdigitais.utils.enums.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@QuarkusTest
class ClienteServiceTest {

    @Inject
    ClienteService clienteService;

    @InjectMock
    ClienteRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    private String pathResource = Paths.get("src", "test", "resources").toFile().getAbsolutePath();


    public Cliente getCliente() {
        try {
            return mapper.readValue(Paths.get(pathResource, "model.entity/cliente.json").toFile(), Cliente.class);
        } catch (IOException ex) {
            log.error("", ex);
        }
        return null;
    }

    public ClienteInDto getClienteInDto() {
        try {
            return mapper.readValue(Paths.get(pathResource, "model.resource/in/clienteInDto.json").toFile(), ClienteInDto.class);
        } catch (IOException ex) {
            log.error("", ex);
        }
        return null;
    }


    @Test
    void findByIdSuccess() {

        var cliente = getCliente();
        when(repository.findByIdOptional(any())).thenReturn(Optional.of(cliente));

        var resultado = clienteService.findById(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("JOAO DA SILVA", resultado.getNome());
    }

    @Test
    void findByIdNotFound() {

        when(repository.findByIdOptional(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> clienteService.findById(2L));
    }

    @Test
    void findAllPageableSuccess() {

        // https://github.com/quarkusio/quarkus/issues/13251
        PanacheQuery query = mock(PanacheQuery.class);
        var clientes = List.of(
                Cliente.builder().nome("JOAO DA SILVA").id(1L).build(),
                Cliente.builder().nome("MARIA DE OLIVEIRA").id(2L).build()
        );
        when(query.list()).thenReturn(clientes);
        when(query.page(any())).thenReturn(query);
        when(query.count()).thenReturn((long) clientes.size());
        when(repository.findAll(any())).thenReturn(query);

        Pageable pageable = Pageable
                .builder()
                .page(0)
                .pageSize(25)
                .orderField("id")
                .orderSort(Sort.Direction.Ascending)
                .build();

        var resultado = clienteService.findAllPageable(pageable);

        assertEquals(2L, resultado.getContent().size());
        assertEquals(2L, resultado.getCount());
        assertEquals(0, resultado.getPageCount());
    }

    @Test
    void findAllPageableProblemParams() {
        Pageable pageable = Pageable
                .builder()
                .filterField("nome")
                .filterOperation(Operation.LIKE)
                .filterValue(null)
                .page(0)
                .pageSize(25)
                .build();

        assertThrows(BusinessException.class, () -> clienteService.findAllPageable(pageable));
    }

    @Test
    void createdClienteSuccess() throws IOException {

        when(repository.createdCliente(any())).thenReturn(1L);

        var resultado = clienteService.createdCliente(getClienteInDto());

        assertEquals(1L, resultado);
    }

    @Test
    void createdClienteProblemDate() throws IOException {

        var clienteDto = getClienteInDto();

        clienteDto.setAniversario("16/10/2000"); //formato errado

        assertThrows(BusinessException.class, () -> clienteService.createdCliente(clienteDto));
    }

    @Test
    void deleteClienteSuccess() throws IOException {

        when(repository.findByIdOptional(any())).thenReturn(Optional.of(getCliente()));

        clienteService.deleteCliente(1L);

        verify(repository, times(1)).deleteCliente(any());
    }

    @Test
    void deleteClienteNotFound() {
        assertThrows(BusinessException.class, () -> clienteService.deleteCliente(1L));
    }
}