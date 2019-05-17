package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DocumentProcessEntity;
import vn.edu.fpt.cpdm.entities.ProcessStepEntity;
import vn.edu.fpt.cpdm.entities.StepOutcomeEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.exceptions.EntityNotFoundException;
import vn.edu.fpt.cpdm.forms.process.DocumentProcessCreateForm;
import vn.edu.fpt.cpdm.forms.process.ProcessStepCreateForm;
import vn.edu.fpt.cpdm.forms.process.StepOutcomeCreateForm;
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
    public void create(DocumentProcessCreateForm documentProcessCreateForm) {

        // Process
        DocumentProcessEntity documentProcessEntity = new DocumentProcessEntity();
        documentProcessEntity.setName(documentProcessCreateForm.getName());
        documentProcessEntity.setDescription(documentProcessCreateForm.getDescription());
        DocumentProcessEntity savedDocumentProcessEntity = documentProcessRepository.save(documentProcessEntity);

        Map<Integer, ProcessStepEntity> map = new HashMap<>();

        // Step
        for (ProcessStepCreateForm processStepCreateForm : documentProcessCreateForm.getSteps()) {
            ProcessStepEntity processStepEntity = new ProcessStepEntity();
            processStepEntity.setProcess(savedDocumentProcessEntity);
            processStepEntity.setDescription(processStepCreateForm.getDescription());
            UserEntity executor = userRepository.findById(processStepCreateForm.getExecutorId()).orElseThrow(
                    () -> new EntityNotFoundException(processStepCreateForm.getExecutorId(), "User")
            );
            processStepEntity.setExecutor(executor);
            ProcessStepEntity savedProcessStepEntity = processStepRepository.saveAndFlush(processStepEntity);
            if (processStepCreateForm.getFirst()) {
                savedDocumentProcessEntity.setFirstStep(savedProcessStepEntity);
                documentProcessRepository.save(savedDocumentProcessEntity);
            }
            map.put(processStepCreateForm.getTemporaryId(), savedProcessStepEntity);
        }

        // Outcome
        for (ProcessStepCreateForm processStepCreateForm : documentProcessCreateForm.getSteps()) {
            for (StepOutcomeCreateForm stepOutcomeCreateForm : processStepCreateForm.getOutcomes()) {
                StepOutcomeEntity stepOutcomeEntity = new StepOutcomeEntity();
                stepOutcomeEntity.setStep(map.get(processStepCreateForm.getTemporaryId()));
                stepOutcomeEntity.setSummary(stepOutcomeCreateForm.getSummary());
                stepOutcomeEntity.setAction(stepOutcomeCreateForm.getAction());
                stepOutcomeEntity.setLastStep(stepOutcomeCreateForm.getLastStep());
                if (stepOutcomeEntity.getLastStep() == false) {
                    stepOutcomeEntity.setNextStep(map.get(stepOutcomeCreateForm.getNextStepTemporaryId()));
                }
                stepOutcomeRepository.save(stepOutcomeEntity);
            }
        }
    }
}
