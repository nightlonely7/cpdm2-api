package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderDetail;
import vn.edu.fpt.cpdm.models.outsiders.OutsiderSummary;
import vn.edu.fpt.cpdm.services.OutsiderService;

import java.util.List;

@RequestMapping("/outsiders")
@RestController
public class OutsiderController {

    private final OutsiderService outsiderService;

    @Autowired
    public OutsiderController(OutsiderService outsiderService) {
        this.outsiderService = outsiderService;
    }


    @GetMapping("/search/findAllSummaryByNameContainsOrCodeContains")
    public ResponseEntity<List<OutsiderSummary>> findAllSummaryByNameContainsOrCodeContains(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code) {
        return ResponseEntity.ok(outsiderService.findAllSummaryByNameContainsOrCodeContains(name, code));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutsiderDetail> findDetailById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(outsiderService.findDetailById(id));
    }

}
