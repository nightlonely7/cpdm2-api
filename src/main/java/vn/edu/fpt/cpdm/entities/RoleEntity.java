package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "RoleEntity")
@Table(name = "role")
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
