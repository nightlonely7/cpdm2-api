package vn.edu.fpt.cpdm.forms.process;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StepOutcomeCreateForm {

    private String summary;

    @NotNull
    private String action;

    private Integer nextStepId;

    @NotNull
    private Boolean lastStep;

}
