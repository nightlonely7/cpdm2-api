package vn.edu.fpt.cpdm.forms.process;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DocumentProcessCreateForm {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private List<ProcessStepCreateForm> steps;

    @NotNull
    private Integer firstStepTemporaryId;
}
