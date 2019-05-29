package vn.edu.fpt.cpdm.models.processes;

import java.util.List;

public interface ProcessStepDetail extends ProcessStepSummary {
    List<StepOutcomeDetail> getOutcomes();
}
