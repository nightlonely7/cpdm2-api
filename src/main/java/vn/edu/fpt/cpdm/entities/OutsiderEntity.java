package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "Outsider")
@Table(name = "outsider")
@Data
public class OutsiderEntity {

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
    @Lob
    @Column(name = "contact_data")
    private String contactData;

    @Basic
    @Column(name = "available", nullable = false)
    private Boolean available;

    @PrePersist
    void prePersist() {
        this.available = Boolean.TRUE;
    }

}
