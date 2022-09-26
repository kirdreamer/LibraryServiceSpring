package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AGE")
    private int age;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL
            , orphanRemoval = true
    )
    @JoinColumn(name = "USER_ID")
    private List<Book> bookList;
}
