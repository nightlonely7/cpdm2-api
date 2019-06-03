package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.exceptions.BadRequestException;
import vn.edu.fpt.cpdm.exceptions.ModelNotValidException;
import vn.edu.fpt.cpdm.forms.departments.DepartmentCreateForm;
import vn.edu.fpt.cpdm.forms.departments.DepartmentUpdateForm;
import vn.edu.fpt.cpdm.models.departments.DepartmentSummary;
import vn.edu.fpt.cpdm.services.DepartmentService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentSummary>> findAllSummary(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(departmentService.findAllSummary(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentSummary> getDetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok(departmentService.findSummaryById(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentSummary> create(@Valid @RequestBody DepartmentCreateForm departmentCreateForm,
                                             BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(departmentService.create(departmentCreateForm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentSummary> update(@PathVariable("id") Integer id,
                                             @Valid @RequestBody DepartmentUpdateForm departmentUpdateForm,
                                             BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(departmentService.update(id,departmentUpdateForm));
    }
}
