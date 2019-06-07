package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.cpdm.exceptions.BadRequestException;
import vn.edu.fpt.cpdm.forms.users.UserCreateForm;
import vn.edu.fpt.cpdm.forms.users.UserUpdateForm;
import vn.edu.fpt.cpdm.models.users.User;
import vn.edu.fpt.cpdm.models.users.UserBasic;
import vn.edu.fpt.cpdm.models.users.UserSummary;
import vn.edu.fpt.cpdm.services.UserService;
import vn.edu.fpt.cpdm.utils.ModelErrorMessage;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
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
    public ResponseEntity<List<UserSummary>> findAllExecutor(){
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

    @PutMapping("/{id}/active")
    public ResponseEntity<User> active(@PathVariable(name = "id") Integer id){
        User user = userService.active(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/deactive")
    public ResponseEntity<User> deactive(@PathVariable(name = "id") Integer id){
        User user = userService.deActive(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search/findManagerByDepartment")
    public ResponseEntity<UserBasic> findManagerByDepartment(@PathParam("departmentId") Integer departmentId){
        return ResponseEntity.ok(userService.findManagerByDepartmentId(departmentId));
    }

    @GetMapping("/search/findAllDirector")
    public ResponseEntity<List<UserBasic>> findAllDirector(){
        return ResponseEntity.ok(userService.findAllDirector());
    }

    @GetMapping("/search/findAllUserForDirector")
    public ResponseEntity<Page<UserSummary>> findAllUserForDirector(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(userService.findAllUserForDirector(pageable));
    }

    @GetMapping("/search/findAllStaffForManager")
    public ResponseEntity<Page<UserSummary>> findAllStaffForManager(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(userService.findAllStaffForManager(pageable));
    }

    @GetMapping("/search/findAllByDepartmentId/{departmentId}")
    public ResponseEntity<List<UserSummary>> findByDepartment(@PathVariable("departmentId") Integer departmentId){
        return ResponseEntity.ok(userService.findAllSummaryByDepartment(departmentId));
    }

    @PutMapping("/promo/{id}/{departmentId}")
    public ResponseEntity<UserBasic> update(@PathVariable("id") Integer id,
                                              @PathVariable("departmentId") Integer departmentId) {
        return ResponseEntity.ok(userService.promo(id,departmentId));
    }
}
