package com.cly.backend;

import com.cly.backend.entity.Vehicle;
import com.cly.backend.mapper.VehicleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClyBackendApplicationTests {
    @Autowired
    private VehicleMapper vehicleMapper;

    @Test
    public void testResultMap(){
        Vehicle vehicle = vehicleMapper.getVehicleById(1L);
        System.out.println(vehicle.toString());
    }

}
