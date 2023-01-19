package br.gov.caixa.negociosdigitais.utils.enums;

public enum ParametroDoToken {
	
	NOME("name"),

    TELEFONE ("numeroTelefone"),
    
    CPF ("preferred_username"),

    ;

	ParametroDoToken(String claim) {
        this.claim = claim;
    }

    private final String claim;

    public String getClaim() {
        return claim;
    }
	
}
