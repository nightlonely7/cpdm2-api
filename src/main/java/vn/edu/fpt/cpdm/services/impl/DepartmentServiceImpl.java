package vn.edu.fpt.cpdm.services.impl;

import org.modelmapper.Conditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.DepartmentEntity;
import vn.edu.fpt.cpdm.exceptions.ConflictException;
import vn.edu.fpt.cpdm.exceptions.EntityIdNotFoundException;
import vn.edu.fpt.cpdm.forms.departments.DepartmentCreateForm;
import vn.edu.fpt.cpdm.forms.departments.DepartmentUpdateForm;
import vn.edu.fpt.cpdm.models.departments.DepartmentSummary;
import vn.edu.fpt.cpdm.repositories.DepartmentRepository;
import vn.edu.fpt.cpdm.services.DepartmentService;
import org.modelmapper.ModelMapper;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Page<DepartmentSummary> findAllSummary(Pageable pageable) {
        return departmentRepository.findAllBy(pageable);
    }

    @Override
    public DepartmentSummary findSummaryById(Integer id) {
        return departmentRepository.findSummaryById(id);
    }

    @Override
    public DepartmentSummary create(DepartmentCreateForm departmentCreateForm) {
        if(departmentRepository.existsByCode(departmentCreateForm.getCode())){
            throw new ConflictException("Mã phòng ban '" + departmentCreateForm.getCode() + "' đã tồn tại!");
        }
        if(departmentRepository.existsByName(departmentCreateForm.getName())){
            throw new ConflictException("Tên phòng ban '" + departmentCreateForm.getName() + "' đã tồn tại!");
        }

        ModelMapper modelMapper = new ModelMapper();
        DepartmentEntity departmentEntity = modelMapper.map(departmentCreateForm,DepartmentEntity.class);
        DepartmentEntity savedDeparmentEntity = departmentRepository.save(departmentEntity);
        DepartmentSummary departmentSummary = departmentRepository.findSummaryById(savedDeparmentEntity.getId());
        return departmentSummary;
    }

    @Override
    public DepartmentSummary update(Integer id, DepartmentUpdateForm departmentUpdateForm) {
        DepartmentEntity departmentEntity = departmentRepository.findById(id).orElseThrow(
                () -> new EntityIdNotFoundException(id,"Department")
        );

        if(!departmentEntity.getCode().equals(departmentUpdateForm.getCode()) && departmentRepository.existsByCode(departmentUpdateForm.getCode())){
            throw new ConflictException("Mã phòng ban '" + departmentUpdateForm.getCode() + "' đã tồn tại!");
        }
        if(!departmentEntity.getName().equals(departmentUpdateForm.getName()) && departmentRepository.existsByName(departmentUpdateForm.getName())){
            throw new ConflictException("Tên phòng ban '" + departmentUpdateForm.getName() + "' đã tồn tại!");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(departmentUpdateForm,departmentEntity);
        DepartmentEntity savedDeparmentEntity = departmentRepository.save(departmentEntity);
        DepartmentSummary departmentSummary = departmentRepository.findSummaryById(savedDeparmentEntity.getId());
        return departmentSummary;
    }
}
