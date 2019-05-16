package vn.edu.fpt.cpdm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private Integer id;
    private String entity;

    public EntityNotFoundException(Integer id, String entity) {
        super(null, null, true, false);
        this.id = id;
        this.entity = entity;
    }

    @Override
    public String getMessage() {

        return entity + " with id '" + id + "' is not found!";
    }
}
