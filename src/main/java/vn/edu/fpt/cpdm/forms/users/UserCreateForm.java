package vn.edu.fpt.cpdm.forms.users;

import lombok.Data;

@Data
public class UserCreateForm {
    private String username;
    private String password;
    private String processRole;
}
