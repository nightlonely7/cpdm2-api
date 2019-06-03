package vn.edu.fpt.cpdm.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "DocumentFile")
@Table(name = "document_file")
@Data
public class DocumentFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private DocumentEntity document;

    @Basic
    @Column(name = "filename", nullable = false)
    private String filename;

    @Basic
    @Column(name = "stored_filename", unique = true, nullable = false)
    private String storedFilename;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    private UserEntity creator;

    @Basic
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Basic
    @Column(name = "last_modified_time", nullable = false)
    private LocalDateTime lastModifiedTime;

    @Basic
    @Column(name = "available")
    private Boolean available;

    @PrePersist
    void prePersist() {
        this.available = Boolean.TRUE;
        LocalDateTime now = LocalDateTime.now();
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
