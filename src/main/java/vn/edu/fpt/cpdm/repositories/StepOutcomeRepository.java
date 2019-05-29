package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.StepOutcomeEntity;
import vn.edu.fpt.cpdm.models.processes.StepOutcomeDetail;
import vn.edu.fpt.cpdm.models.processes.StepOutcomeSummary;

import java.util.List;

public interface StepOutcomeRepository extends JpaRepository<StepOutcomeEntity, Integer> {

    List<StepOutcomeDetail> findDetailById(Integer id);

    List<StepOutcomeSummary> findAllSummaryByStep_Id(Integer stepId);

    boolean existsByStep_Id(Integer stepId);
}
