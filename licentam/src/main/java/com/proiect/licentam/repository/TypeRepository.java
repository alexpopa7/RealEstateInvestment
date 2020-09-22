package com.proiect.licentam.repository;

import com.proiect.licentam.model.Type;
import com.proiect.licentam.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    @Query(value = "select t from Type t where t.type = ?1")
    Type findByType(String type);
}
