package br.com.project.service.imp;

import br.com.project.model.entity.Auditoria;
import br.com.project.model.internal.enums.AcaoAuditoria;
import br.com.project.repository.AuditoriaRepository;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Timestamp;

@Slf4j
@ApplicationScoped
public class AuditoriaService {

    @Inject
    AuditoriaRepository auditoriaRepository;

    @Inject
    RoutingContext routingContext;

    public void salvar(AcaoAuditoria acao, String detalhe, String classe, String entidade) {

        var timeMili = System.currentTimeMillis();
        var ipOrigem = routingContext.request().remoteAddress().host();

        var auditoria = Auditoria.builder()
                .deAcao(acao.getDescricao())
                .deConteudo(detalhe)
                .deClasse(classe)
                .deEntidadeConteudo(entidade)
                .tsAudit(new Timestamp(timeMili))
                .deIpUsuario(ipOrigem)
                .build();

        auditoriaRepository.persist(auditoria);
    }


}
