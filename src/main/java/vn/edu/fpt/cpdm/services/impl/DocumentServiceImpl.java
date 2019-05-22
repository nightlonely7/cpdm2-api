package vn.edu.fpt.cpdm.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.*;
import vn.edu.fpt.cpdm.exceptions.BadRequestException;
import vn.edu.fpt.cpdm.exceptions.ConflictException;
import vn.edu.fpt.cpdm.exceptions.EntityNotFoundException;
import vn.edu.fpt.cpdm.forms.documents.DocumentCreateForm;
import vn.edu.fpt.cpdm.forms.process.FeedbackCreateForm;
import vn.edu.fpt.cpdm.models.documents.DocumentDetail;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;
import vn.edu.fpt.cpdm.repositories.*;
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
    private final OutsiderRepository outsiderRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository,
                               DocumentProcessRepository documentProcessRepository,
                               AuthenticationService authenticationService,
                               StepOutcomeRepository stepOutcomeRepository,
                               StepFeedbackRepository stepFeedbackRepository,
                               OutsiderRepository outsiderRepository) {
        this.documentRepository = documentRepository;
        this.documentProcessRepository = documentProcessRepository;
        this.authenticationService = authenticationService;
        this.stepOutcomeRepository = stepOutcomeRepository;
        this.stepFeedbackRepository = stepFeedbackRepository;
        this.outsiderRepository = outsiderRepository;
    }

    @Override
    public Page<DocumentSummary> findAllSummary(Pageable pageable) {
        return documentRepository.findAllSummaryBy(pageable);
    }

    @Override
    public DocumentDetail findDetailById(Integer id) {
        return documentRepository.findDetailById(id).orElseThrow(
                () -> new EntityNotFoundException(id, "Document")
        );
    }

    @Override
    public DocumentDetail create(DocumentCreateForm documentCreateForm) {

        if (documentRepository.existsByCode(documentCreateForm.getCode())) {
            throw new ConflictException("This document code '" + documentCreateForm.getCode() + "' " +
                    "is already existed!");
        }

        if (documentCreateForm.getEffectiveDate().isAfter(documentCreateForm.getEffectiveEndDate())) {
            throw new BadRequestException("EffectiveDate as '" + documentCreateForm.getEffectiveDate().toString() + "' " +
                    "can not after effectiveEndDate as '" + documentCreateForm.getEffectiveEndDate() + "'");
        }

        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setCode(documentCreateForm.getCode());
        documentEntity.setTitle(documentCreateForm.getTitle());
        documentEntity.setSummary(documentCreateForm.getSummary());
        documentEntity.setDecree(documentCreateForm.getDecree());
        documentEntity.setDetail(documentCreateForm.getDetail());
        documentEntity.setArrivalDate(documentCreateForm.getArrivalDate());
        documentEntity.setEffectiveDate(documentCreateForm.getEffectiveDate());
        documentEntity.setEffectiveEndDate(documentCreateForm.getEffectiveEndDate());
        documentEntity.setOutsider(outsiderRepository.findById(documentCreateForm.getOutsiderId()).orElseThrow(
                () -> new EntityNotFoundException(documentCreateForm.getOutsiderId(), "Outsider")
        ));
        DocumentEntity savedDocumentEntity = documentRepository.save(documentEntity);
        DocumentDetail savedDocumentDetail = documentRepository.findDetailById(savedDocumentEntity.getId()).orElseThrow(
                () -> new EntityNotFoundException(savedDocumentEntity.getId(), "Document")
        );

        return savedDocumentDetail;
    }

    @Override
    public DocumentDetail putIntoProcess(Integer documentId, Integer processId) {
        DocumentEntity documentEntity = documentRepository.findById(documentId).orElseThrow(
                () -> new EntityNotFoundException(documentId, "Document")
        );
        DocumentProcessEntity documentProcessEntity = documentProcessRepository.findById(processId).orElseThrow(
                () -> new EntityNotFoundException(processId, "DocumentProcess")
        );
        documentEntity.setProcess(documentProcessEntity);
        documentEntity.setCurrentStep(documentProcessEntity.getFirstStep());
        documentEntity.setStartedProcessingTime(LocalDateTime.now());
        documentEntity.setStartedProcessing(Boolean.TRUE);
        documentEntity.setProcessed(Boolean.FALSE);
        DocumentEntity savedDocumentEntity = documentRepository.save(documentEntity);
        DocumentDetail savedDocumentDetail = documentRepository.findDetailById(savedDocumentEntity.getId()).orElseThrow(
                () -> new EntityNotFoundException(savedDocumentEntity.getId(), "Document")
        );
        return savedDocumentDetail;
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
            documentEntity.setStartedProcessing(Boolean.FALSE);
        }
        documentRepository.save(documentEntity);
    }

    @Override
    public Page<DocumentSummary> findAllExecutingDocuments(Pageable pageable) {

        UserEntity executor = authenticationService.getCurrentLoggedUser();
        Page<DocumentSummary> documentSummaries = documentRepository.findAllByCurrentStep_Executor(executor, pageable);

        return documentSummaries;
    }


}
