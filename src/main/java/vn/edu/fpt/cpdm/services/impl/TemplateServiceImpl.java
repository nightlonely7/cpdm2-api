package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.TemplateEntity;
import vn.edu.fpt.cpdm.exceptions.ConflictException;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.forms.templates.TemplateCreateForm;
import vn.edu.fpt.cpdm.forms.templates.TemplateUpdateForm;
import vn.edu.fpt.cpdm.models.NameOnlyModel;
import vn.edu.fpt.cpdm.models.templates.TemplateDetail;
import vn.edu.fpt.cpdm.repositories.TemplateRepository;
import vn.edu.fpt.cpdm.services.TemplateService;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public TemplateDetail findDetailById(Integer id) {
        return templateRepository.findDetailByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Template")
        );
    }

    @Override
    public List<NameOnlyModel> findAllNameOnly() {
        return templateRepository.findAllNameOnlyByAvailableTrue();
    }

    @Override
    public boolean existsByName(String name) {
        return templateRepository.existsByNameAndAvailableTrue(name);
    }

    @Override
    public TemplateDetail create(TemplateCreateForm templateCreateForm) {

        if (templateRepository.existsByNameAndAvailableTrue(templateCreateForm.getName())) {
            throw new ConflictException("This template's name '" + templateCreateForm.getName() + "' is already existed!");
        }

        TemplateEntity templateEntity = new TemplateEntity();
        templateEntity.setName(templateCreateForm.getName());
        templateEntity.setTemplate(templateCreateForm.getTemplate());
        TemplateEntity savedTemplateEntity = templateRepository.save(templateEntity);
        TemplateDetail templateDetail = templateRepository.findDetailByIdAndAvailableTrue(savedTemplateEntity.getId()).orElseThrow(
                () -> new EntityIdNotFoundException(savedTemplateEntity.getId(), "Template")
        );

        return templateDetail;
    }

    @Override
    public TemplateDetail update(Integer id, TemplateUpdateForm templateUpdateForm) {

        TemplateEntity templateEntity = templateRepository.findByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Template")
        );

        if (!templateUpdateForm.getName().equals(templateEntity.getName())
                && templateRepository.existsByNameAndAvailableTrue(templateUpdateForm.getName())) {
            throw new ConflictException("This template's name '" + templateUpdateForm.getName() + "' is already existed!");
        }

        templateEntity.setName(templateUpdateForm.getName());
        templateEntity.setTemplate(templateUpdateForm.getTemplate());
        TemplateEntity savedTemplateEntity = templateRepository.save(templateEntity);
        TemplateDetail templateDetail = templateRepository.findDetailByIdAndAvailableTrue(savedTemplateEntity.getId()).orElseThrow(
                () -> new EntityIdNotFoundException(savedTemplateEntity.getId(), "Template")
        );

        return templateDetail;
    }

    @Override
    public void delete(Integer id) {

        TemplateEntity templateEntity = templateRepository.findByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Template")
        );

        templateEntity.setAvailable(Boolean.FALSE);
        templateRepository.save(templateEntity);
    }
}
