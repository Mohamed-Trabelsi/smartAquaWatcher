package com.example.projectapi.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Barage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nom ;
    private Float capacite ;
    private String region ;
    private Float niveau ;
    private String mac ;
    @ManyToMany(mappedBy = "barages")
    @JsonIgnore
    private Set<User> users;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "barage")
    @JsonIgnore
    private Set<Noeud> noeuds;

}
