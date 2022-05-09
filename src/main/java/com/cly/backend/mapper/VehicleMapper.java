package com.cly.backend.mapper;

import com.cly.backend.entity.Vehicle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VehicleMapper {
    List<Vehicle> listVehiclesByFilter(Long locationId, String pickUpDate, String dropOffDate);
    List<Vehicle> listSortedVehiclesByFilter(Long locationId, String pickUpDate, String dropOffDate);
    Vehicle getVehicleById(Long id);
}
