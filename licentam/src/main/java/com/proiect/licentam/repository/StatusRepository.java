package com.proiect.licentam.repository;


import com.proiect.licentam.model.Status;
import com.proiect.licentam.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    @Query(value = "select s from Status s where s.status = ?1")
    Status findByStatus(String status);
}
