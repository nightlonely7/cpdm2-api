package vn.edu.fpt.cpdm.forms.process;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProcessStepCreateForm {

    @NotNull
    private Integer temporaryId;

    private String description;

    @NotNull
    private Integer executorId;

    @NotNull
    private Boolean first;

    @NotNull
    private List<StepOutcomeCreateForm> outcomes;
}
