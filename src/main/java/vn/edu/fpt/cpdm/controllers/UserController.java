package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.exceptions.BadRequestException;
import vn.edu.fpt.cpdm.forms.users.UserCreateForm;
import vn.edu.fpt.cpdm.forms.users.UserUpdateForm;
import vn.edu.fpt.cpdm.models.users.UserBasic;
import vn.edu.fpt.cpdm.services.StepOutcomeService;
import vn.edu.fpt.cpdm.services.UserService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.RespectBinding;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search/executor")
    public ResponseEntity<List<UserBasic>> findAllExecutor(){
        return ResponseEntity.ok(userService.findAllExecutor());
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<UserBasic> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.findBasicById(id));
    }
    @PostMapping
    public ResponseEntity<UserBasic> create(@Valid @RequestBody UserCreateForm userCreateForm,
                                            BindingResult result){
        if(result.hasErrors()){
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(userService.create(userCreateForm));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserBasic> update(@PathVariable("id") Integer id,
                                            @Valid @RequestBody UserUpdateForm userUpdateForm,
                                            BindingResult result){
        if(result.hasErrors()){
            String message = ModelErrorMessage.build(result);
            throw new BadRequestException(message);
        }
        return ResponseEntity.ok(userService.update(id, userUpdateForm));
    }
    @GetMapping("/search/findManagerByDepartment")
    public ResponseEntity<UserBasic> findManagerByDepartment(@PathParam("departmentId") Integer departmentId){
        return ResponseEntity.ok(userService.findManagerByDepartmentId(departmentId));
    }
}
