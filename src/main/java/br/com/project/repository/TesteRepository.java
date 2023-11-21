package br.com.project.repository;

import br.com.project.model.entity.ClasseTeste;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class TesteRepository implements PanacheRepositoryBase<ClasseTeste, Long> {
}
