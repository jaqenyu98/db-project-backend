package com.cly.backend.mapper;

import com.cly.backend.entity.CustomerVehicle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerVehicleMapper {
    void reserve(CustomerVehicle customerVehicle);
    void updateOrderWhenPickUp(Long id, Integer startOdometer, Integer status);
    void updateOrderWhenDropOff(Long id, Integer endOdometer, Integer status);
    List<CustomerVehicle> listReservedOrders(Long customerId);
    List<CustomerVehicle> listPickedUpOrders(Long customerId);
    List<CustomerVehicle> listDroppedOffOrders(Long customerId);
    List<CustomerVehicle> listPaidOrders(Long customerId);
    List<CustomerVehicle> listUncompletedOrdersByVehicleId(Long vehicleId);
    void setOrderStatus(Long id, Integer status);
    CustomerVehicle getOrderById(Long id);
}
