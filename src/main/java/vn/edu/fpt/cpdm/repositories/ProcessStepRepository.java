package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.ProcessStepEntity;
import vn.edu.fpt.cpdm.models.processes.ProcessStepDetail;

import java.util.List;

public interface ProcessStepRepository extends JpaRepository<ProcessStepEntity, Integer> {
    List<ProcessStepDetail> findDetailById(Integer id);
}
