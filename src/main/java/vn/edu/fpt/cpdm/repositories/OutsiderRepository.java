package vn.edu.fpt.cpdm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.cpdm.entities.OutsiderEntity;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;

import java.util.List;

public interface OutsiderRepository extends JpaRepository<OutsiderEntity, Integer> {
    List<OutsiderSummary> findAllSummaryByNameContainsOrCodeContains(String name, String code);
}
