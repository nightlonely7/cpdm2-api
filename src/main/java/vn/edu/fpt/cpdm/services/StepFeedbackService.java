package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.models.processes.StepFeedbackSummary;

import java.util.List;

public interface StepFeedbackService {
    List<StepFeedbackSummary> findAllByDocumentId(Integer documentId);
}
