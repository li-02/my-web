package com.example.blog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="tag")
public class Tag extends BaseEntity {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "usage_count", columnDefinition = "INT DEFAULT 0")
    private Integer usageCount;

    // Constructors
    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

}
