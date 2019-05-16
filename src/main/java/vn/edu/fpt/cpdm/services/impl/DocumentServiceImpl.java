package vn.edu.fpt.cpdm.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.*;
import vn.edu.fpt.cpdm.exceptions.BadRequestException;
import vn.edu.fpt.cpdm.exceptions.ConflictException;
import vn.edu.fpt.cpdm.exceptions.EntityNotFoundException;
import vn.edu.fpt.cpdm.forms.documents.DocumentCreateForm;
import vn.edu.fpt.cpdm.forms.process.FeedbackCreateForm;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;
import vn.edu.fpt.cpdm.repositories.DocumentProcessRepository;
import vn.edu.fpt.cpdm.repositories.DocumentRepository;
import vn.edu.fpt.cpdm.repositories.StepFeedbackRepository;
import vn.edu.fpt.cpdm.repositories.StepOutcomeRepository;
import vn.edu.fpt.cpdm.services.AuthenticationService;
import vn.edu.fpt.cpdm.services.DocumentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentProcessRepository documentProcessRepository;
    private final AuthenticationService authenticationService;
    private final StepOutcomeRepository stepOutcomeRepository;
    private final StepFeedbackRepository stepFeedbackRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository,
                               DocumentProcessRepository documentProcessRepository,
                               AuthenticationService authenticationService,
                               StepOutcomeRepository stepOutcomeRepository,
                               StepFeedbackRepository stepFeedbackRepository) {
        this.documentRepository = documentRepository;
        this.documentProcessRepository = documentProcessRepository;
        this.authenticationService = authenticationService;
        this.stepOutcomeRepository = stepOutcomeRepository;
        this.stepFeedbackRepository = stepFeedbackRepository;
    }

    @Override
    public void create(DocumentCreateForm documentCreateForm) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setTitle(documentCreateForm.getTitle());
        documentEntity.setDetail(documentCreateForm.getDetail());
        DocumentProcessEntity documentProcessEntity = documentProcessRepository
                .findById(documentCreateForm.getProcessId()).orElseThrow(
                        () -> new EntityNotFoundException(documentCreateForm.getProcessId(), "DocumentProcess")
                );
        documentEntity.setProcess(documentProcessEntity);
        documentRepository.save(documentEntity);
    }

    @Override
    public void putIntoProcess(Integer documentId) {
        DocumentEntity documentEntity = documentRepository.findById(documentId).orElseThrow(
                () -> new EntityNotFoundException(documentId, "Document")
        );
        documentEntity.setCurrentStep(documentEntity.getProcess().getFirstStep());
        documentEntity.setStartedProcessingTime(LocalDateTime.now());
        documentEntity.setStartedProcessing(Boolean.TRUE);
        documentRepository.save(documentEntity);
    }

    @Override
    public void forwardProcess(Integer documentId, Integer outcomeId, FeedbackCreateForm feedbackCreateForm) {

        DocumentEntity documentEntity = documentRepository
                .findById(documentId).orElseThrow(
                        () -> new EntityNotFoundException(documentId, "Document")
                );

        if (documentEntity.getProcessed()) {
            throw new BadRequestException("This document with id '" + documentId + "' is already processed!");
        }
        if (documentEntity.getStartedProcessing() == false) {
            throw new BadRequestException("This document with id '" + documentId + "' is not started processing!");
        }

        StepOutcomeEntity stepOutcomeEntity = stepOutcomeRepository
                .findById(outcomeId).orElseThrow(
                        () -> new EntityNotFoundException(outcomeId, "StepOutcome")
                );
        ProcessStepEntity currentStep = documentEntity.getCurrentStep();
        if (stepOutcomeEntity.getStep().equals(currentStep) == false
                && stepOutcomeEntity.getLastStep() == false) {
            throw new ConflictException(
                    "This Step Outcome with id '" + stepOutcomeEntity.getId() + "' is not belong in the " +
                            "Step with id '" + currentStep.getId() + "'");
        }

        StepFeedbackEntity stepFeedbackEntity = new StepFeedbackEntity();
        stepFeedbackEntity.setDocument(documentEntity);
        stepFeedbackEntity.setOutcome(stepOutcomeEntity);
        if (documentEntity.getLastProcessedTime() == null) {
            stepFeedbackEntity.setArrivalTime(documentEntity.getStartedProcessingTime());
        } else {
            stepFeedbackEntity.setArrivalTime(documentEntity.getLastProcessedTime());
        }
        stepFeedbackEntity.setCompletedTime(LocalDateTime.now());
        stepFeedbackEntity.setFeedback(feedbackCreateForm.getFeedback());
        StepFeedbackEntity savedStepFeedbackEntity = stepFeedbackRepository.save(stepFeedbackEntity);

        documentEntity.setCurrentStep(stepOutcomeEntity.getNextStep());
        documentEntity.setLastProcessedTime(savedStepFeedbackEntity.getCompletedTime());
        if (stepOutcomeEntity.getLastStep()) {
            documentEntity.setProcessed(Boolean.TRUE);
        }
        documentRepository.save(documentEntity);
    }

    @Override
    public List<DocumentSummary> findAllExecutingDocuments() {

        UserEntity executor = authenticationService.getCurrentLoggedUser();
        List<DocumentSummary> documentSummaries = documentRepository.findAllByCurrentStep_Executor(executor);

        return documentSummaries;
    }


}
