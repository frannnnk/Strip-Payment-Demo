package com.frank.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "customer", schema = "H44YN9kupS", catalog = "")
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Integer id;

    @Column(name = "`customerId`")
    private String customerId;

    @Column(name = "`paymentType`")
    private String paymentType;


    //// Stripe related fields
    @Column(name = "`customerStripeId`")
    private String customerStripeId;


    //// Braintree related fields
    @Column(name = "`customerBraintreeId`")
    private String customerBraintreeId;

    @Column(name = "`customerBraintreePaymentMethodToken`")
    private String customerBraintreePaymentMethodToken;

    @Column(name = "`customerBraintreeDefaultPaymentMethodImageURL`")
    private String customerBraintreeDefaultPaymentMethodImageURL;



    @Column(name = "`customerEmail`")
    private String customerEmail;

    @Column(name = "`createTime`", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "`last4`")
    private String last4;

    @Column(name = "`exp_month`")
    private String expMonth;

    @Column(name = "`exp_year`")
    private String expYear;

    @Column(name = "`stripeToken`")
    private String stripeToken;

    @Column(name = "`brand`")
    private String brand;

    @Column(name = "`country`")
    private String country;


}
