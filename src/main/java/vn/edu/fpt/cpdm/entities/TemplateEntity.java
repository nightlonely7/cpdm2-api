package vn.edu.fpt.cpdm.entities;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "Template")
@Table(name = "template")
@Data
public class TemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "template")
    private String template;

}
