package br.com.project.resource;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/async")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MessageAsyncResource {

    @GET
    @Path("{name}")
     Uni<String> greeting(@PathParam("name") String name);
}
