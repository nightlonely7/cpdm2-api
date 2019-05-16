package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.ProcessStepEntity;

public interface ProcessStepRepository extends JpaRepository<ProcessStepEntity, Integer> {

}
