package com.proiect.licentam.services;

import com.proiect.licentam.model.Home;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RealEstateServiceTest {

    @Autowired
    RealEstateService realEstateService;

    @Test
    void getHomeList() {

        List<Home> homes = realEstateService.getHomeList();

        assertEquals(homes.size(), 1);
    }
}