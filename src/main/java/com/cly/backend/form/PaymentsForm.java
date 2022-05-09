package com.cly.backend.form;

import com.cly.backend.entity.Payment;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class PaymentsForm {
    private Long customerVehicleId;
    private List<@Valid Payment> payments;
}
