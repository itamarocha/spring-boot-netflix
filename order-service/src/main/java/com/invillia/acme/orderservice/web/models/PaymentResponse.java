package com.invillia.acme.orderservice.web.models;

import java.util.Date;

import lombok.Data;
@Data
public class PaymentResponse {

    private String status;
    private Date paymentDate;
}