package br.com.project.service.imp;

import br.com.project.model.resource.in.FileInfo;
import br.com.project.model.resource.in.FileUploadForm;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@ApplicationScoped
public class FileUploadService {

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "upload.directory", defaultValue = "uploads/")
    String uploadDirectory;

    public void processFiles(FileUploadForm form) throws IOException {
        // Cria diretório de upload se não existir
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Cria arquivo ZIP
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            // Adiciona os parâmetros de texto ao arquivo ZIP
            addToZip(zipOutputStream, "param1.txt", form.getParam1().getBytes());
            addToZip(zipOutputStream, "param2.txt", form.getParam2().getBytes());

            // Adiciona cada arquivo ao ZIP
            for (FileInfo fileInfo : form.getFiles()) {
                addToZip(zipOutputStream, fileInfo.getFileName(), fileInfo.getFile().readAllBytes());
            }
        }

        // Grava o arquivo ZIP no sistema de arquivos
        byte[] zipData = byteArrayOutputStream.toByteArray();
        String zipFilePath = uploadDirectory + "uploadedFiles.zip";
        vertx.fileSystem().writeFileBlocking(zipFilePath, Buffer.buffer(zipData));
    }

    public void processZip(List<InputPart> inputParts) throws IOException {

        // Cria diretório de upload se não existir
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Cria arquivo ZIP
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            inputParts.forEach(inputPart -> {
                try {

                    MultivaluedMap<String, String> header = inputPart.getHeaders();
                    var fileName = getFileName(header);
                    System.out.println("File Name: " + fileName);
                    InputStream inputStream = inputPart.getBody(InputStream.class, null);
                    byte[] bytes = IOUtils.toByteArray(inputStream);

                    addToZip(zipOutputStream, fileName, bytes);

//                    File customDir = new File(uploadDirectory);
//                    fileName = customDir.getAbsolutePath() + File.separator + fileName;
//                    Files.write(Paths.get(fileName), bytes, StandardOpenOption.CREATE_NEW);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            byte[] zipData = byteArrayOutputStream.toByteArray();
            String zipFilePath = uploadDirectory + "uploadedFiles-" + new Random().nextInt(10_000) + ".zip";
            vertx.fileSystem().writeFileBlocking(zipFilePath, Buffer.buffer(zipData));
        }
    }

    private void addToZip(ZipOutputStream zipOutputStream, String fileName, byte[] data) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(data);
        zipOutputStream.closeEntry();
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }
}
