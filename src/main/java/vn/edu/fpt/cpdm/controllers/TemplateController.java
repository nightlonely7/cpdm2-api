package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.exceptions.BadRequestException;
import vn.edu.fpt.cpdm.forms.templates.TemplateCreateForm;
import vn.edu.fpt.cpdm.forms.templates.TemplateUpdateForm;
import vn.edu.fpt.cpdm.models.NameOnlyModel;
import vn.edu.fpt.cpdm.models.templates.TemplateDetail;
import vn.edu.fpt.cpdm.services.TemplateService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/templates")
@RestController
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ResponseEntity<List<NameOnlyModel>> findAllNameOnly() {
        return ResponseEntity.ok(templateService.findAllNameOnly());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateDetail> findDetailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(templateService.findDetailById(id));
    }

    @GetMapping("/check/existsByName")
    public ResponseEntity<Boolean> checkExistsByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(templateService.existsByName(name));
    }

    @PostMapping
    public ResponseEntity<TemplateDetail> create(@Valid @RequestBody TemplateCreateForm templateCreateForm,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(templateService.create(templateCreateForm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateDetail> update(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody TemplateUpdateForm templateUpdateForm,
                                                 BindingResult result) {
        if (result.hasErrors()) {
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(templateService.update(id, templateUpdateForm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        templateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
