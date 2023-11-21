package br.com.project.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(name = "TESTE Interceptor")
@Path("v1/teste")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TesteResource {

    @POST
    @Operation(summary = "Criação do SGDB", description = "descricao")
    Response created(String descricao);

    @POST
    @Path("/lote")
    @Operation(summary = "Criação em lote", description = "descricao")
    Response creates();

    @DELETE
    @Path("{codigo}")
    @Operation(summary = "Apaga SGDB", description = "descricao")
    Response delete(@PathParam("codigo") Long codigo);

    @DELETE
    @Path("/logico/{codigo}")
    @Operation(summary = "Apaga LOGICO SGDB", description = "descricao")
    Response deleteLogico(@PathParam("codigo") Long codigo);

    @PUT
    @Path("{codigo}")
    @Operation(summary = "Alteracao do SGDB", description = "descricao")
    Response update(@PathParam("codigo") Long codigo, String descricao);

    @PUT
    @Path("/updates")
    @Operation(summary = "Alteracao em lote", description = "descricao")
    Response updates();

    @GET
    @Operation(summary = "CONSULTA", description = "descricao")
    Response query();

}
