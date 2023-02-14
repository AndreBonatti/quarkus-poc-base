package br.com.project.repository;

import br.com.project.model.entity.Cliente;
import br.com.project.utils.GeneratedValuesRandom;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    @Transactional
    public void createMassaDados(Long numeroClientes) {

        var nomes = List.of("ANDRE", "JOAO", "MARIA", "ESTELA");

        var clientes = new ArrayList<Cliente>();

        for (int i = 0; i < numeroClientes; i++) {
            Date date1 = Date.from(GeneratedValuesRandom.randomBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant());
            var cliente = Cliente
                    .builder()
                    .nome(GeneratedValuesRandom.getRandomElement(nomes) + " " + i)
                    .aniversario(date1)
                    .anotacao("TESTE " + i)
                    .build();
            clientes.add(cliente);
        }

        this.persist(clientes);
    }

    @Transactional
    public Long createdCliente(Cliente cliente) {
        this.persistAndFlush(cliente);
        return cliente.getId();
    }

    @Transactional
    public void deleteCliente(Cliente entity) {
        this.delete(entity);
    }

}
