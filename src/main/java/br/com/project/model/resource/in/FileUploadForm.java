package br.com.project.model.resource.in;

import javax.ws.rs.FormParam;
import java.util.List;

public class FileUploadForm {


    @FormParam("files")
    private List<FileInfo> files;
    @FormParam("param1")
    private String param1;
    @FormParam("param2")
    private String param2;

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
