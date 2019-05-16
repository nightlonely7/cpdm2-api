package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "DocumentProcess")
@Table(name = "document_process")
@Data
public class DocumentProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "first_step_id", referencedColumnName = "id")
    private ProcessStepEntity firstStep;
}
