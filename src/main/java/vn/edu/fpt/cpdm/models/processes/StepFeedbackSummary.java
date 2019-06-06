package vn.edu.fpt.cpdm.models.processes;

import java.time.LocalDateTime;

public interface StepFeedbackSummary {
    String getFeedback();
    LocalDateTime getArrivalTime();
    LocalDateTime getCompletedTime();
    StepOutcomeSummary getOutcome();
}
