package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {

    List<DocumentSummary> findAllByCurrentStep_Executor(UserEntity executor);

    Page<DocumentSummary> findAllSummaryBy(Pageable pageable);

    Page<DocumentSummary> findAllSummaryByStartedProcessingFalse();

    boolean existsByCode(String code);
}
