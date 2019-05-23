package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentProcessEntity;
import vn.edu.fpt.cpdm.models.processes.DocumentProcessSummary;

public interface DocumentProcessRepository extends JpaRepository<DocumentProcessEntity, Integer> {
    Page<DocumentProcessSummary> findAllByActiveTrueOrderByCreatedTimeDesc(Pageable pageable);
}
