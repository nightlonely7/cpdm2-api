package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.forms.templates.TemplateCreateForm;
import vn.edu.fpt.cpdm.forms.templates.TemplateUpdateForm;
import vn.edu.fpt.cpdm.models.NameOnlyModel;
import vn.edu.fpt.cpdm.models.templates.TemplateDetail;

import java.util.List;

public interface TemplateService {

    TemplateDetail findDetailById(Integer id);

    List<NameOnlyModel> findAllNameOnly();

    boolean existsByName(String name);

    TemplateDetail create(TemplateCreateForm templateCreateForm);

    TemplateDetail update(Integer id, TemplateUpdateForm templateUpdateForm);

    void delete(Integer id);
}
