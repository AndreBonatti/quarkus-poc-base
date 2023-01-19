package br.gov.caixa.negociosdigitais.model.resource.in.parameters;

import br.gov.caixa.negociosdigitais.utils.enums.Operation;
import io.quarkus.panache.common.Sort;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pageable {

    @Parameter(example = "nome", description = "Filter - Campo a ser pesquisado")
    @QueryParam("filter-field")
    String filterField;

    @Parameter(example = "Maria", description = "Filter - valor do campo a ser pequisado")
    @QueryParam("filter-value")
    String filterValue;

    @Parameter(example = "EQ", description = "Filter - Operacoes disponíveis de busca")
    @QueryParam("filter-operation")
    Operation filterOperation;

    @Parameter(example = "id", description = "Ordenacao - campo a ser ordenado")
    @QueryParam("order-field")
    String orderField = "id";

    @Parameter(example = "Descending", description = "Ordenacao - direcao do campo ordenado ascendente ou descendente")
    @QueryParam("order-sort")
    Sort.Direction orderSort;

    @Parameter(example = "0", required = true)
    @QueryParam("page")
    @NotNull
    @Min(0)
    Integer page;

    @Parameter(example = "25", required = true)
    @QueryParam("page-size")
    @NotNull
    @Max(1000)
    Integer pageSize;
}


