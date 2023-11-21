package br.com.project.model.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "AUDITORIA")
public class Auditoria extends PanacheEntityBase {
    @Id
    @Column(name = "NU_AUDITORIA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long nuAuditoria;

    @Column(name = "DE_ACAO", nullable = false, length = 15)
    public String deAcao;

    @Column(name = "TS_AUDIT", nullable = false)
    public java.sql.Timestamp tsAudit;

    @Lob
    @Column(name = "DE_CONTEUDO", columnDefinition = "TEXT", nullable = false)
    public String deConteudo;

    @Column(name = "DE_IP_USUARIO", nullable = false, length = 50)
    public String deIpUsuario;

    @Column(name = "DE_ENTIDADE_CONTEUDO", nullable = false, length = 100)
    public String deEntidadeConteudo;

    @Column(name = "DE_CLASSE", nullable = false, length = 100)
    public String deClasse;

}
