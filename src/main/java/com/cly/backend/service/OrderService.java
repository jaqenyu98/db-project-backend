package com.cly.backend.service;

import com.cly.backend.entity.CustomerVehicle;
import com.cly.backend.entity.Payment;
import com.cly.backend.form.OrderForm;

import java.util.List;

public interface OrderService {
    void reserve(Long customerId, OrderForm form);
    List<CustomerVehicle> listReservedOrders(Long customerId);
    List<CustomerVehicle> listPickedUpOrders(Long customerId);
    List<CustomerVehicle> listDroppedOffOrders(Long customerId);
    List<CustomerVehicle> listPaidOrders(Long customerId);
    void makePayments(Long customerVehicleId, List<Payment> payments);
}
