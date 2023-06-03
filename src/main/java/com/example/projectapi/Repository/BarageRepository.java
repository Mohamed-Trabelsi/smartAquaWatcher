package com.example.projectapi.Repository;

import com.example.projectapi.Entity.Barage;
import com.example.projectapi.Entity.Grandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarageRepository extends JpaRepository<Barage,Long> {

    @Query("SELECT b FROM Barage b LEFT JOIN b.users u WHERE u.id IS NULL")
    List<Barage> findBaragesWithNoUsers();



}
