package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.models.processes.StepOutcomeSummary;
import vn.edu.fpt.cpdm.repositories.StepOutcomeRepository;
import vn.edu.fpt.cpdm.services.StepOutcomeService;

import java.util.List;

@Service
public class StepOutcomeServiceImpl implements StepOutcomeService {

    private final StepOutcomeRepository stepOutcomeRepository;

    @Autowired
    public StepOutcomeServiceImpl(StepOutcomeRepository stepOutcomeRepository) {
        this.stepOutcomeRepository = stepOutcomeRepository;
    }

    @Override
    public List<StepOutcomeSummary> findAllSummaryByStep_Id(Integer stepId) {

        if (stepOutcomeRepository.existsByStep_Id(stepId) == false) {
            throw new EntityIdNotFoundException(stepId, "Step");
        }

        return stepOutcomeRepository.findAllSummaryByStep_Id(stepId);
    }
}
