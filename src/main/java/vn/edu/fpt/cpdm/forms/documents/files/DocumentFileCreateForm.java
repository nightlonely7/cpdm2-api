package vn.edu.fpt.cpdm.forms.documents.files;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DocumentFileCreateForm {

    @NotNull
    private MultipartFile file;

    @NotBlank
    private String filename;

    private String description;
}
