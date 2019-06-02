package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.OutsiderEntity;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderDetail;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;

import java.util.List;
import java.util.Optional;

public interface OutsiderRepository extends JpaRepository<OutsiderEntity, Integer> {

    List<OutsiderSummary> findAllSummaryByNameContainsOrCodeContainsAndAvailableTrue(String name, String code);

    Optional<OutsiderDetail> findDetailByIdAndAvailableTrue(Integer id);

    boolean existsByCode(String code);

}
