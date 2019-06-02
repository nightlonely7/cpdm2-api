package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Department")
@Table(name = "department")
@Data
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Basic
    @Column(name = "available", nullable = false)
    private Boolean available;

    @PrePersist
    public void prePersist() {
        this.available = Boolean.TRUE;
        this.createdTime = LocalDateTime.now();
    }
}
