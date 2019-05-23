package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.models.documents.DocumentDetail;
import vn.edu.fpt.cpdm.models.documents.DocumentSummary;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {

    Page<DocumentSummary> findAllByCurrentStep_Executor(UserEntity executor, Pageable pageable);

    Page<DocumentSummary> findAllSummaryBy(Pageable pageable);

    Page<DocumentSummary> findAllSummaryByStartedProcessingFalse(Pageable pageable);

    Optional<DocumentDetail> findDetailById(Integer id);

    boolean existsByCode(String code);
}
