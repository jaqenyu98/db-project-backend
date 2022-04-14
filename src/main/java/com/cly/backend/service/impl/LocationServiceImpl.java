package com.cly.backend.service.impl;

import com.cly.backend.entity.Location;
import com.cly.backend.mapper.LocationMapper;
import com.cly.backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationMapper locationMapper;
    @Override
    public List<Location> listAllLocations() {
        return locationMapper.listAllLocations();
    }
}
