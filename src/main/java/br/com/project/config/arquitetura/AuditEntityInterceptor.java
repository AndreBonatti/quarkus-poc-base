package br.com.project.config.arquitetura;

import br.com.project.model.entity.Auditoria;
import br.com.project.model.entity.EnableRegister;
import br.com.project.service.imp.AuditoriaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static br.com.project.model.internal.enums.AcaoAuditoria.*;


@Slf4j
@ApplicationScoped
@PersistenceUnitExtension
public class AuditEntityInterceptor extends EmptyInterceptor {

    private Set inserts = new HashSet<>();
    private Set updates = new HashSet<>();
    private Set deletes = new HashSet<>();

    @Inject
    AuditoriaService auditEntityService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        log.info("UPDATE - Object {} /  id {}  / currentState {} / previousState {} / propertyNames {} / types {}", entity, id, currentState, previousState, propertyNames, types);

        if (!(entity instanceof Auditoria)) {
            updates.add(entity);
        }

        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        log.info("SAVE - Object {} /  id {}  / state {} / propertyNames {} / types {}", entity, id, state, propertyNames, types);

        if (!(entity instanceof Auditoria)) {
            inserts.add(entity);
        }

        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        log.info("DELETE - Object {} /  id {}  / state {} / propertyNames {} / types {}", entity, id, state, propertyNames, types);

        if (!(entity instanceof Auditoria)) {
            deletes.add(entity);
        }

        super.onDelete(entity, id, state, propertyNames, types);
    }


    @Transactional
    @Override
    public void postFlush(Iterator entities) {
        log.info("Salvar {}, Editar {}, Excluir {}", inserts.size(), updates.size(), deletes.size());

        try {

            inserts.forEach(o ->
                    auditEntityService.salvar(SALVAR, getDetalhe(o), getNameClass(o), getNameEntity(o))
            );
            updates.forEach(o -> {
                if (o instanceof EnableRegister) {
                    var entidade = (EnableRegister) o;
                    if (!entidade.isEnable()) {
                        auditEntityService.salvar(EXCLUIR_LOGICO, getDetalhe(o), getNameClass(o), getNameEntity(o));
                        return;
                    }
                }
                auditEntityService.salvar(EDITAR, getDetalhe(o), getNameClass(o), getNameEntity(o));

            });
            deletes.forEach(o ->
                    auditEntityService.salvar(EXCLUIR_FISICO, getDetalhe(o), getNameClass(o), getNameEntity(o))
            );

        } finally {
            inserts.clear();
            updates.clear();
            deletes.clear();
        }
        super.postFlush(entities);
    }

    private String getDetalhe(Object entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error("Problema ao serializar objeto Auditoria {}, Entity {}", e.getMessage(), entity.toString());
        }
        return "NONE";
    }

    private String getNameClass(Object entity) {
        Class<?> entityClass = entity.getClass();
        String className = entityClass.getSimpleName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private String getNameEntity(Object entity) {
        Annotation tableAnnotation = entity.getClass().getAnnotation(Table.class);
        if (tableAnnotation != null) {
            return ((Table) tableAnnotation).name();
        }
        return "NONE";
    }

}
