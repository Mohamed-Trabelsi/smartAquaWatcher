package com.example.projectapi.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Noeud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNoeud ;
    private String mac ;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "noeud")
    private Set<Grandeur> grandeurs;

    @ManyToOne
    Barage barage;

}
