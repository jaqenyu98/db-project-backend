package com.cly.backend.mapper;

import com.cly.backend.entity.Location;
import com.cly.backend.entity.Vehicle;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VehicleMapper {
    List<Vehicle> listVehiclesByFilter(Long locationId, String pickUpDate, String dropOffDate);
}
