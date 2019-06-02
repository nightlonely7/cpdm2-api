package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.forms.outsiders.OutsiderCreateForm;
import vn.edu.fpt.cpdm.forms.outsiders.OutsiderUpdateForm;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderDetail;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;

import java.util.List;

public interface OutsiderService {

    List<OutsiderSummary> findAllSummaryByNameContainsOrCodeContains(String name, String code);

    OutsiderDetail findDetailById(Integer id);

    OutsiderDetail create(OutsiderCreateForm createForm);

    OutsiderDetail update(Integer id, OutsiderUpdateForm updateForm);

    void delete(Integer id);
}
