package vn.edu.fpt.cpdm.models.processes;

import java.util.List;

public interface ProcessStepDetail extends ProcessStepSummary {
    String getDescription();
    List<StepOutcomeDetail> getOutcomes();
}
