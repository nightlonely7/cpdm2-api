package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.TemplateEntity;
import vn.edu.fpt.cpdm.models.NameOnlyModel;
import vn.edu.fpt.cpdm.models.templates.TemplateDetail;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Integer> {

    Optional<TemplateEntity> findByIdAndAvailableTrue(Integer id);

    List<NameOnlyModel> findAllNameOnlyByAvailableTrue();

    boolean existsByNameAndAvailableTrue(String name);

    Optional<TemplateDetail> findDetailByIdAndAvailableTrue(Integer id);
}
