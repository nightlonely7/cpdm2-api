package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.OutsiderEntity;
import vn.edu.fpt.cpdm.exceptions.ConflictException;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.forms.outsiders.OutsiderCreateForm;
import vn.edu.fpt.cpdm.forms.outsiders.OutsiderUpdateForm;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderDetail;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;
import vn.edu.fpt.cpdm.repositories.OutsiderRepository;
import vn.edu.fpt.cpdm.services.OutsiderService;

import java.util.List;

@Service
public class OutsiderServiceImpl implements OutsiderService {

    private final OutsiderRepository outsiderRepository;

    @Autowired
    public OutsiderServiceImpl(OutsiderRepository outsiderRepository) {
        this.outsiderRepository = outsiderRepository;
    }

    @Override
    public List<OutsiderSummary> findAllSummary() {
        return outsiderRepository.findAllSummaryByAvailableTrue();
    }

    @Override
    public OutsiderDetail findDetailById(Integer id) {
        return outsiderRepository.findDetailByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Outsider")
        );
    }

    @Override
    public OutsiderDetail create(OutsiderCreateForm createForm) {
        if(outsiderRepository.existsByCode(createForm.getCode())){
            throw new ConflictException("This outsider's code '"+ createForm.getCode()+"' already exist.");
        }
        OutsiderEntity entity = new OutsiderEntity();
        entity.setCode(createForm.getCode());
        entity.setName(createForm.getName());
        entity.setContactData(createForm.getContactData());
        OutsiderEntity saved = outsiderRepository.save(entity);
        return outsiderRepository.findDetailByIdAndAvailableTrue(saved.getId()).orElseThrow(
                () -> new EntityIdNotFoundException(saved.getId(), "Outsider")
        );
    }

    @Override
    public OutsiderDetail update(Integer id, OutsiderUpdateForm updateForm) {
        OutsiderEntity entity = outsiderRepository.findById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Outsider")
        );
        if(!updateForm.getCode().equals(entity.getCode()) && outsiderRepository.existsByCode(updateForm.getCode())){
            throw new ConflictException("This outsider's code '" + updateForm.getCode() + "' already exist");
        }
        entity.setCode(updateForm.getCode());
        entity.setName(updateForm.getName());
        entity.setContactData(updateForm.getContactData());
        outsiderRepository.save(entity);
        return outsiderRepository.findDetailByIdAndAvailableTrue(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Outsider")
        );
    }

    @Override
    public void delete(Integer id) {
        OutsiderEntity entity = outsiderRepository.findById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id, "Outsider")
        );
        entity.setAvailable(false);
        outsiderRepository.save(entity);
    }

    @Override
    public boolean isExistByCode(String code) {
        if(outsiderRepository.existsByCode(code)){
            return true;
        }
        return false;
    }
}
