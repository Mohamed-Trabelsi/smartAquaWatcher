package com.example.projectapi.Repository;

import com.example.projectapi.Entity.Barage;
import com.example.projectapi.Entity.Noeud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Noeud,Long> {




}
