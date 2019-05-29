package vn.edu.fpt.cpdm.models.processes;

import vn.edu.fpt.cpdm.models.IdOnlyModel;

public interface StepOutcomeDetail extends StepOutcomeSummary {
    Boolean getLastStep();
    IdOnlyModel getNextStep();
}
