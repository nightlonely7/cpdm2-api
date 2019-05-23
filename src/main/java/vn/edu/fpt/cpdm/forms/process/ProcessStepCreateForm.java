package vn.edu.fpt.cpdm.forms.process;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProcessStepCreateForm {

    @NotNull
    private Integer temporaryId;

    @NotNull
    @Size(min = 4)
    private String name;

    private String description;

    @NotNull
    private Integer executorId;

    @NotNull
    private List<StepOutcomeCreateForm> outcomes;
}
