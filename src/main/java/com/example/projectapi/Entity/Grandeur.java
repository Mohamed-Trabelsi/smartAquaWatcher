package com.example.projectapi.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grandeur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Float temp ;
    private  Float humidite ;
    private Float ph ;
    private Float turbidite ;
    private Boolean critique ;
    //@Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime heure ;

    @ManyToOne
    @JsonIgnore
    private Noeud noeud;

}
