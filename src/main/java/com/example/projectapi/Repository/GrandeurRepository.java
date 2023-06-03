package com.example.projectapi.Repository;

import com.example.projectapi.Entity.Grandeur;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrandeurRepository extends JpaRepository<Grandeur,Long> {

    Grandeur findFirstByOrderByIdDesc();
    List<Grandeur> findAllByOrderByIdDesc();

}
