package com.proiect.licentam.repository;


import com.proiect.licentam.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZoneRepository extends JpaRepository<Zone, Integer> {


    @Query(value = "select z from Zone z where z.type = ?1")
    Zone findByZone(String zone);
}
