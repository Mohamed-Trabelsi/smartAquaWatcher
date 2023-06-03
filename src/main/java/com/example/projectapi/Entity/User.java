package com.example.projectapi.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(unique = true)
    private String login ;
    @Column(unique = true)
    private String email ;

    private String password;

    private String phoneNumber ;

    @Column(nullable = true)
    private Integer code ;

    private Integer type;

    private Boolean approval ;

    @ManyToMany
    private Set<Barage> barages ;






}
