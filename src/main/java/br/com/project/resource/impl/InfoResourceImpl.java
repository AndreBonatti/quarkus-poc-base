package br.com.project.resource.impl;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Tag(name = "Information")
@Path("v1/info")
public class InfoResourceImpl {

    @ConfigProperty(name = "quarkus.application.version")
    String version;

    @GET
    public Response getInfoApp() {
        return Response.ok(this.version != null ? this.version : "Versão não disponível").build();
    }

}
