package br.gov.caixa.negociosdigitais.model.resource.out;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseError {

    @Schema(required = true, description = "Código de erro específico do endpoint")
    @NotNull
    private Integer code;

    @Schema(required = true, description = "Título legível deste erro específico")
    @NotNull
    @Size(max = 255)
    private String title;

    @Schema(required = true, description = "Descrição legível erro específico")
    @NotNull
    @Size(max = 4096)
    private String detail;

}
