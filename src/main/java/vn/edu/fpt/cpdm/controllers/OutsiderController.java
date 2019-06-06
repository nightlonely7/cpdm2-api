package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.exceptions.BadRequestException;
import vn.edu.fpt.cpdm.forms.outsiders.OutsiderCreateForm;
import vn.edu.fpt.cpdm.forms.outsiders.OutsiderUpdateForm;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderDetail;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;
import vn.edu.fpt.cpdm.services.OutsiderService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/outsiders")
@RestController
public class OutsiderController {

    private final OutsiderService outsiderService;

    @Autowired
    public OutsiderController(OutsiderService outsiderService) {
        this.outsiderService = outsiderService;
    }


    @GetMapping
    public ResponseEntity<List<OutsiderSummary>> findAllSummary() {
        return ResponseEntity.ok(outsiderService.findAllSummary());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutsiderDetail> findDetailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(outsiderService.findDetailById(id));
    }

    @PostMapping
    public ResponseEntity<OutsiderDetail> create(@Valid @RequestBody OutsiderCreateForm outsiderCreateForm,
                                                 BindingResult result){
        if(result.hasErrors()){
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(outsiderService.create(outsiderCreateForm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutsiderDetail> update(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody OutsiderUpdateForm outsiderUpdateForm,
                                                 BindingResult result){
        if(result.hasErrors()){
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(outsiderService.update(id, outsiderUpdateForm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OutsiderDetail> delete(@PathVariable("id") Integer id){
        outsiderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/existsByCode")
    public ResponseEntity<Boolean> checkExistByCode(@RequestParam("code") String code){
        return ResponseEntity.ok(outsiderService.isExistByCode(code));
    }
}
