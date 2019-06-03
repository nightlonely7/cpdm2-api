package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentEntity;
import vn.edu.fpt.cpdm.entities.DocumentFileEntity;
import vn.edu.fpt.cpdm.models.documents.files.DocumentFileDetail;

import java.util.List;
import java.util.Optional;

public interface DocumentFileRepository extends JpaRepository<DocumentFileEntity, Integer> {
    Optional<DocumentFileDetail> findDetailByIdAndAvailableTrue(Integer id);
    List<DocumentFileDetail> findAllDetailByDocumentAndAvailableTrue(DocumentEntity document);
}
