package com.example.blog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

//    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
//    private List<Article> articles = new ArrayList<>();

    // Constructors
    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

}
