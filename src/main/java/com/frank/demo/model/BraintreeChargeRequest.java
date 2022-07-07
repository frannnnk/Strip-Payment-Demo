package com.frank.demo.model;

import lombok.Data;

@Data
public class BraintreeChargeRequest {

    String customerId;
    String customerBraintreeId;
    String amount;
    String description;


}
