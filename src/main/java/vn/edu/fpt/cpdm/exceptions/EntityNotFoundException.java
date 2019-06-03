package vn.edu.fpt.cpdm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private Integer id;
    private String entity;
    private String name;

    public EntityNotFoundException(Integer id, String entity) {
        super(null, null, true, false);
        this.id = id;
        this.entity = entity;
    }

    public EntityNotFoundException(String name, String entity) {
        super(null, null, true, false);
        this.name = name;
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        if(name==null){
            return entity + " with id '" + id + "' is not found!";
        }
        else{
            return entity + " with name '" + name + "' is not found!";
        }
    }
}
