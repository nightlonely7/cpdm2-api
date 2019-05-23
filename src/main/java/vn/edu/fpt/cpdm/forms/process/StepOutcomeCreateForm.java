package vn.edu.fpt.cpdm.forms.process;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StepOutcomeCreateForm {

    private String description;

    @NotNull
    private String action;

    private Integer nextStepTemporaryId;

    @NotNull
    private Boolean lastStep;

}
