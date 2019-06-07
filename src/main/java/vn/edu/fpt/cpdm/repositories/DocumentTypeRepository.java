package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.DocumentTypeEntity;
import vn.edu.fpt.cpdm.models.NameOnlyModel;

import java.util.List;

public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity, Integer> {
    List<NameOnlyModel> findAllBy();
}
