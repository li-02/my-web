package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 媒体资源实体类
 */
@Setter
@Getter
@Entity
@Table(name = "media", indexes = {
        @Index(name = "idx_upload_time", columnList = "upload_time")
})
public class Media extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename", nullable = false, length = 100)
    private String filename;

    @Column(name = "original_filename", length = 200)
    private String originalFilename;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "media_type", length = 50)
    private String mediaType;

    // Constructors
    public Media() {}

    public Media(String filename, String filePath) {
        this.filename = filename;
        this.filePath = filePath;
    }

}