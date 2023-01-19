package br.gov.caixa.negociosdigitais.model.resource.in;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ClienteInDto {

    @NotBlank
    @NotNull
    @Size(min = 10, max = 255)
    @Schema(example = "JOSE DA SILVA", required = true)
    private String nome;

    @NotBlank
    @NotNull
    @Size(min = 10, max = 10)
    @Schema(example = "2023-01-31", required = true)
    private String aniversario;

    private String anotacao;
}
