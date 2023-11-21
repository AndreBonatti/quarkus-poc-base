package br.com.project.model.internal.enums;

public enum AcaoAuditoria {

    SALVAR("SALVAR"),
    EDITAR("EDITAR"),
    EXCLUIR_LOGICO("EXCLUIR_LOGICO"),
    EXCLUIR_FISICO("EXCLUIR_FISICO");

    private String descricao;

    AcaoAuditoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
