package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentProcessEntity;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessSummary;

import java.util.List;

public interface DocumentProcessRepository extends JpaRepository<DocumentProcessEntity, Integer> {

    List<DocumentProcessSummary> findAllSummaryBy();
}
