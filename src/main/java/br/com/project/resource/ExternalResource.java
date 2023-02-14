package br.com.project.resource;

import br.com.project.config.exception.ConstantExceptionMessage;
import br.com.project.model.resource.out.ResponseError;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/projeto/extenal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "OPERAÇÕES EXTERNAS")
public interface ExternalResource {

    @GET
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "BUSCA PELO ID", content = @Content(schema = @Schema(implementation = Response.class))),
            @APIResponse(responseCode = "500", description = ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500, content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    @Operation(summary = "Consulta todos", description = "descricao")
    Response findAll();

    @GET
    @Path("{code}")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "BUSCA PELO ID", content = @Content(schema = @Schema(implementation = Response.class))),
            @APIResponse(responseCode = "400", description = ConstantExceptionMessage.BAD_REQUEST_400, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "404", description = ConstantExceptionMessage.NOT_FOUND_404, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "500", description = ConstantExceptionMessage.INTERNAL_SERVER_ERROR_500, content = @Content(schema = @Schema(implementation = ResponseError.class))),
            @APIResponse(responseCode = "503", description = ConstantExceptionMessage.SEVICE_UNAVAILABLE_503, content = @Content(schema = @Schema(implementation = ResponseError.class)))
    })
    @Operation(summary = "Consulta pelo codigo bancario", description = "descricao")
    Response findByID(@Parameter(description = "Código bancário") @PathParam("code") Integer id);

}
