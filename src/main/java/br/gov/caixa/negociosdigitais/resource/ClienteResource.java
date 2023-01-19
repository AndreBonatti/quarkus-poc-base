package br.gov.caixa.negociosdigitais.resource;

import br.gov.caixa.negociosdigitais.config.exception.ConstantExceptionMessage;
import br.gov.caixa.negociosdigitais.model.entity.Cliente;
import br.gov.caixa.negociosdigitais.model.resource.in.ClienteInDto;
import br.gov.caixa.negociosdigitais.model.resource.in.parameters.Pageable;
import br.gov.caixa.negociosdigitais.model.resource.out.ResponseError;
import br.gov.caixa.negociosdigitais.model.resource.out.ResponsePageable;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/projeto/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "OPERACOES CLIENTE")
public interface ClienteResource {

    @GET
    @Path("{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "BUSCA PELO ID", content = @Content(schema = @Schema(implementation = Cliente.class))),
            @APIResponse(responseCode = "400", description = ConstantExceptionMessage.BAD_REQUEST_400, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "404", description = ConstantExceptionMessage.NOT_FOUND_404, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "500", description = ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500, content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    @Operation(summary = "Consulta pelo id", description = "descricao")
    Response findByID(@PathParam("id") Long id);


    @GET
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Lista de Cliente", content = @Content(schema = @Schema(implementation = ResponsePageable.class))),
            @APIResponse(responseCode = "400", description = ConstantExceptionMessage.BAD_REQUEST_400, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "404", description = ConstantExceptionMessage.NOT_FOUND_404, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "500", description = ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500, content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    @Operation(summary = "Consulta paginada", description = "descricao")
    Response findAll(@Valid @BeanParam Pageable pageable);

    @POST
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Cliente.class))),
            @APIResponse(responseCode = "400", description = ConstantExceptionMessage.BAD_REQUEST_400, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "500", description = ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500, content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    @Operation(summary = "Criacao do cliente", description = "descricao")
    Response createCliente(@Valid ClienteInDto clienteDTO);

    @PUT
    @Path("{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Updated"),
            @APIResponse(responseCode = "400", description = ConstantExceptionMessage.BAD_REQUEST_400, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "404", description = ConstantExceptionMessage.NOT_FOUND_404, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "500", description = ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500, content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    @Operation(summary = "Alteracao do cliente", description = "descricao")
    Response updateCliente(@PathParam("id") Long id, @Valid ClienteInDto clienteDTO);

    @DELETE
    @Path("{id}")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Delete"),
            @APIResponse(responseCode = "404", description = ConstantExceptionMessage.NOT_FOUND_404, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "500", description = ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500, content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    @Operation(summary = "Remocao do cliente", description = "descricao")
    Response deleteCliente(@PathParam("id") Long id);

    @POST
    @Path("{num}")
    @Operation(summary = "Massa de teste do cliente, passe um número de clientes a ser criado dinamicamente", description = "descricao")
    Response massaTeste(@PathParam("num") Long num);


}
