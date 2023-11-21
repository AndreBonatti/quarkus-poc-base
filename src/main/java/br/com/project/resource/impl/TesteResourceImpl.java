package br.com.project.resource.impl;

import br.com.project.model.entity.ClasseTeste;
import br.com.project.repository.AuditoriaRepository;
import br.com.project.repository.TesteRepository;
import br.com.project.resource.TesteResource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TesteResourceImpl implements TesteResource {

    @Inject
    AuditoriaRepository auditoriaRepository;

    @Inject
    TesteRepository testeRepository;

    Random random = new Random();

    @Transactional
    @Override
    public Response created(String descricao) {
        var testPersist = ClasseTeste.builder().descricao(descricao).build();
        testeRepository.persistAndFlush(testPersist);
        return Response.ok(testPersist).build();
    }

    @Transactional
    @Override
    public Response creates() {
        List<String> nomes = new ArrayList<>();
        nomes.add("Olivia Mendes");
        nomes.add("Gabriel Santos");
        nomes.add("Isabella Costa");
        nomes.add("Matheus Oliveira");
        nomes.add("Sophia Pereira");
        nomes.add("Lucas Rodrigues");
        nomes.add("Laura Silva");
        nomes.add("Pedro Almeida");
        nomes.add("Maria Souza");
        nomes.add("Guilherme Fernandes");

        nomes.forEach(s ->
                testeRepository.persist(ClasseTeste.builder().descricao(s).build())
        );

        return Response.ok().build();
    }

    @Transactional
    @Override
    public Response updates() {


        List<String> nomes = new ArrayList<>();
        List<ClasseTeste> listaUpdate = new ArrayList<>();

        nomes.add("Olivia Mendes");
        nomes.add("Gabriel Santos");
        nomes.add("Isabella Costa");
        nomes.add("Matheus Oliveira");
        nomes.add("Sophia Pereira");
        nomes.add("Lucas Rodrigues");
        nomes.add("Laura Silva");
        nomes.add("Pedro Almeida");
        nomes.add("Maria Souza");
        nomes.add("Guilherme Fernandes");


        for (int i = 0; i < 4; i++) {
            Integer randomNumber = random.nextInt(10);
            var testUpdate = testeRepository.findById(randomNumber.longValue());
            if (testUpdate != null) {
                testUpdate.setDescricao(nomes.get(randomNumber));
                listaUpdate.add(testUpdate);
            }
        }

        listaUpdate.forEach(s ->
                testeRepository.persist(s)
        );

        return Response.ok().build();
    }

    @Override
    public Response query() {
        return Response.ok(testeRepository.findAll()).build();
    }

    @Transactional
    @Override
    public Response delete(Long codigo) {
        return Response.ok(testeRepository.deleteById(codigo)).build();
    }

    @Transactional
    @Override
    public Response deleteLogico(Long codigo) {
        var testUpdate = testeRepository.findById(codigo);
        testUpdate.setEnable(!testUpdate.isEnable());
        testeRepository.persistAndFlush(testUpdate);
        return Response.ok(true).build();
    }

    @Transactional
    @Override
    public Response update(Long codigo, String descricao) {
        var testUpdate = testeRepository.findById(codigo);
        testUpdate.setDescricao(descricao);
        testeRepository.persistAndFlush(testUpdate);
        return Response.ok(testUpdate).build();
    }

}
