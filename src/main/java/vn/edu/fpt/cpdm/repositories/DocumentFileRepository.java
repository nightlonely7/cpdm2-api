package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentFileEntity;

public interface DocumentFileRepository extends JpaRepository<DocumentFileEntity, Integer> {
}
