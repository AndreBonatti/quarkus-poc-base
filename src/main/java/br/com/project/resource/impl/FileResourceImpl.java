package br.com.project.resource.impl;

import br.com.project.resource.FileResource;
import br.com.project.service.imp.FileUploadService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/insumos")
@Tag(name = "File Resource", description = "Endpoint for file operations")
public class FileResourceImpl implements FileResource {

    @Inject
    FileUploadService fileUploadService;

    public Response handleFileUploadForm(@MultipartForm MultipartFormDataInput input) throws IOException {
        List<InputPart> inputParts = input.getFormDataMap().get("files");
        System.out.println("inputParts size: " + inputParts.size());
        fileUploadService.processZip(inputParts);

        return Response.ok().entity("All files save successfully.").build();
    }


}
