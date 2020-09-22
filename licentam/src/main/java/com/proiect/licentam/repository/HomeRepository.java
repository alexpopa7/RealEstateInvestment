package com.proiect.licentam.repository;

import com.proiect.licentam.model.Home;
import com.proiect.licentam.model.HomeDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<HomeDao, Integer> {
}
