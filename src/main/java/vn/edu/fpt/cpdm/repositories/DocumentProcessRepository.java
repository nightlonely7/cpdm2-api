package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentProcessEntity;

public interface DocumentProcessRepository extends JpaRepository<DocumentProcessEntity, Integer> {
}
