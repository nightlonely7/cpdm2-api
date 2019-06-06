package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DocumentProcessEntity;
import vn.edu.fpt.cpdm.entities.ProcessStepEntity;
import vn.edu.fpt.cpdm.entities.StepOutcomeEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.forms.process.DocumentProcessCreateForm;
import vn.edu.fpt.cpdm.forms.process.ProcessStepCreateForm;
import vn.edu.fpt.cpdm.forms.process.StepOutcomeCreateForm;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessDetail;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessSummary;
import vn.edu.fpt.cpdm.repositories.DocumentProcessRepository;
import vn.edu.fpt.cpdm.repositories.ProcessStepRepository;
import vn.edu.fpt.cpdm.repositories.StepOutcomeRepository;
import vn.edu.fpt.cpdm.repositories.UserRepository;
import vn.edu.fpt.cpdm.services.DocumentProcessService;

import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentProcessServiceImpl implements DocumentProcessService {

    private final DocumentProcessRepository documentProcessRepository;
    private final ProcessStepRepository processStepRepository;
    private final StepOutcomeRepository stepOutcomeRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentProcessServiceImpl(DocumentProcessRepository documentProcessRepository, ProcessStepRepository processStepRepository, StepOutcomeRepository stepOutcomeRepository, UserRepository userRepository) {
        this.documentProcessRepository = documentProcessRepository;
        this.processStepRepository = processStepRepository;
        this.stepOutcomeRepository = stepOutcomeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DocumentProcessDetail findDetailById(Integer id) {
        return documentProcessRepository.findDetailById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "DocumentProcess")
        );
    }

    @Override
    public DocumentProcessDetail create(DocumentProcessCreateForm documentProcessCreateForm) {

        // Process
        DocumentProcessEntity documentProcessEntity = new DocumentProcessEntity();
        documentProcessEntity.setName(documentProcessCreateForm.getName());
        documentProcessEntity.setDescription(documentProcessCreateForm.getDescription());
        DocumentProcessEntity savedDocumentProcessEntity = documentProcessRepository.saveAndFlush(documentProcessEntity);

        Map<Integer, ProcessStepEntity> map = new HashMap<>();

        // Step
        for (ProcessStepCreateForm processStepCreateForm : documentProcessCreateForm.getSteps()) {
            ProcessStepEntity processStepEntity = new ProcessStepEntity();
            processStepEntity.setProcess(savedDocumentProcessEntity);
            processStepEntity.setName(processStepCreateForm.getName());
            processStepEntity.setDescription(processStepCreateForm.getDescription());
            UserEntity executor = userRepository.findById(processStepCreateForm.getExecutorId()).orElseThrow(
                    () -> new EntityIdNotFoundException(processStepCreateForm.getExecutorId(), "User")
            );
            processStepEntity.setExecutor(executor);
            ProcessStepEntity savedProcessStepEntity = processStepRepository.saveAndFlush(processStepEntity);
            map.put(processStepCreateForm.getTemporaryId(), savedProcessStepEntity);
        }

        savedDocumentProcessEntity.setFirstStep(map.get(documentProcessCreateForm.getFirstStepTemporaryId()));
        documentProcessRepository.saveAndFlush(savedDocumentProcessEntity);

        // Outcome
        for (ProcessStepCreateForm processStepCreateForm : documentProcessCreateForm.getSteps()) {
            for (StepOutcomeCreateForm stepOutcomeCreateForm : processStepCreateForm.getOutcomes()) {
                StepOutcomeEntity stepOutcomeEntity = new StepOutcomeEntity();
                stepOutcomeEntity.setStep(map.get(processStepCreateForm.getTemporaryId()));
                stepOutcomeEntity.setDescription(stepOutcomeCreateForm.getDescription());
                stepOutcomeEntity.setName(stepOutcomeCreateForm.getName());
                stepOutcomeEntity.setLastStep(stepOutcomeCreateForm.getLastStep());
                stepOutcomeEntity.setMain(stepOutcomeCreateForm.getMain());
                if (stepOutcomeEntity.getLastStep() == false) {
                    stepOutcomeEntity.setNextStep(map.get(stepOutcomeCreateForm.getNextStepTemporaryId()));
                }
                stepOutcomeRepository.saveAndFlush(stepOutcomeEntity);
            }
        }

        DocumentProcessDetail savedDocumentProcessDetail = documentProcessRepository.findDetailById(
                savedDocumentProcessEntity.getId()).orElseThrow(
                () -> new EntityIdNotFoundException(savedDocumentProcessEntity.getId(), "DocumentProcess")
        );
        return savedDocumentProcessDetail;
    }

    @Override
    public Page<DocumentProcessSummary> findAllSummary(Pageable pageable) {
        return documentProcessRepository.findAllByAvailableTrueOrderByCreatedTimeDesc(pageable);
    }
}
