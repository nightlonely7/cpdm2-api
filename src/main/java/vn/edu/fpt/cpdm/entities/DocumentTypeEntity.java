package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "DocumentTypeEntity")
@Table(name = "document_type")
@Data
public class DocumentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;
}
