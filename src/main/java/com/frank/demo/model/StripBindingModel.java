package com.frank.demo.model;

import lombok.Data;

@Data
public class StripBindingModel {
    String country;
    String brand;
    String last4;
    String exp_month;
    String exp_year;
    String stripeToken;
    String customerId;
    String customerStripeId;
    String customerEmail;
}
