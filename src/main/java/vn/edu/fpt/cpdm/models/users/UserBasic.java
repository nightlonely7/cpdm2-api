package vn.edu.fpt.cpdm.models.users;

import vn.edu.fpt.cpdm.models.NameOnlyModel;

public interface UserBasic {
    Integer getId();
    String getUsername();
    NameOnlyModel getRole();
}
