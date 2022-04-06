package com.cly.backend.mapper;

import com.cly.backend.entity.Location;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {

    List<Location> listLocations();
}
