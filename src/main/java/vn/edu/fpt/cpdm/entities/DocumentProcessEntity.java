package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "DocumentProcessEntity")
@Table(name = "document_process")
@Data
public class DocumentProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "available")
    private boolean available;

    @Basic
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @OneToOne
    @JoinColumn(name = "first_step_id", referencedColumnName = "id")
    private ProcessStepEntity firstStep;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private List<ProcessStepEntity> steps;

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now();
        this.available = true;
    }
}
