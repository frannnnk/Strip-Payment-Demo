package com.frank.demo.model;

import lombok.Data;

@Data
public class ChargeRequest {

    String stripToken;
    String customerId;
    String amount;
    String description;
    String customerStripeId;

}
