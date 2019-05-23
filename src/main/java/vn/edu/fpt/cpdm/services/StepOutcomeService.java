package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.models.processes.StepOutcomeSummary;

import java.util.List;

public interface StepOutcomeService {
    List<StepOutcomeSummary> findAllSummaryByStep_Id(Integer stepId);
}
