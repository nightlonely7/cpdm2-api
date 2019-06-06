package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.models.processes.StepFeedbackSummary;
import vn.edu.fpt.cpdm.repositories.DocumentRepository;
import vn.edu.fpt.cpdm.repositories.StepFeedbackRepository;
import vn.edu.fpt.cpdm.services.StepFeedbackService;

import java.util.List;

@Service
public class StepFeedbackServiceImpl implements StepFeedbackService {

    private final DocumentRepository documentRepository;
    private final StepFeedbackRepository stepFeedbackRepository;

    @Autowired
    public StepFeedbackServiceImpl(DocumentRepository documentRepository,
                                   StepFeedbackRepository stepFeedbackRepository) {
        this.documentRepository = documentRepository;
        this.stepFeedbackRepository = stepFeedbackRepository;
    }

    @Override
    public List<StepFeedbackSummary> findAllByDocumentId(Integer documentId) {

        DocumentEntity documentEntity = documentRepository.findById(documentId).orElseThrow(
                () -> new EntityIdNotFoundException(documentId, "Document")
        );

        return stepFeedbackRepository.findAllByDocumentOrderByArrivalTime(documentEntity);
    }
}
