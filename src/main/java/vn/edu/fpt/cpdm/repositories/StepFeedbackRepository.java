package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.entities.StepFeedbackEntity;
import vn.edu.fpt.cpdm.models.processes.StepFeedbackSummary;

import java.util.List;

public interface StepFeedbackRepository extends JpaRepository<StepFeedbackEntity, Integer> {
    List<StepFeedbackSummary> findAllByDocumentOrderByArrivalTime(DocumentEntity documentEntity);
}
