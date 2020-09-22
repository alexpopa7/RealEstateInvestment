package com.proiect.licentam.repository;

import com.proiect.licentam.model.Address;
import com.proiect.licentam.model.Status;
import com.proiect.licentam.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "select a from Address a where a.street = ?1 and a.number =?2")
    Address findByStreetAndNumber (String street, String number);
}
