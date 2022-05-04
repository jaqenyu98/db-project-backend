package com.cly.backend;

import com.cly.backend.entity.CustomerVehicle;
import com.cly.backend.entity.Vehicle;
import com.cly.backend.mapper.CustomerVehicleMapper;
import com.cly.backend.mapper.VehicleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ClyBackendApplicationTests {
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private CustomerVehicleMapper customerVehicleMapper;

    @Test
    public void testVehicles(){
        Vehicle vehicle = vehicleMapper.getVehicleById(1L);
        System.out.println(vehicle.toString());
    }
    @Test
    public void testOrders(){
        List<CustomerVehicle> orders = customerVehicleMapper.listReservedOrders(27L);
        System.out.println();
    }

}
