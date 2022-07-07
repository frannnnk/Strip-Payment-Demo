package com.frank.demo.controllers;

import com.frank.demo.repository.CustomerRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.frank.demo.entity.Customer;
import com.frank.demo.model.StripBindingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class StripePageController {

    @Autowired
    Environment env;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @PostMapping("/savecard")
    public String page(Model model, StripBindingModel stripe) {
        model.addAttribute("stripe", stripe);

        Customer customer = new Customer();
        customer.setPaymentType("STRIPE");
        customer.setBrand(stripe.getBrand());
        customer.setCountry(stripe.getCountry());
        customer.setCreateTime(new Date());
        customer.setCustomerId(stripe.getCustomerId());
        customer.setExpMonth(stripe.getExp_month());
        customer.setExpYear(stripe.getExp_year());
        customer.setLast4(stripe.getLast4());
        customer.setStripeToken(stripe.getStripeToken());

        // Create a Customer:
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("source", stripe.getStripeToken());
        customerParams.put("email", stripe.getCustomerEmail());
        try {
            Stripe.apiKey = "sk_test_MZ6ZiNbihYcwWq7Z9L4gJ0Ym";
            com.stripe.model.Customer stripeCus = com.stripe.model.Customer.create(customerParams);
            customer.setCustomerStripeId(stripeCus.getId());
            stripe.setCustomerStripeId(stripeCus.getId());
        } catch (StripeException e) {
            e.printStackTrace();
        }

        customerRepository.save(customer);
        return "saveresult";
    }

    @GetMapping("/bind")
    public String bind(Model model) {
        return "bind";
    }

    @GetMapping("/charge")
    public String charge(Model model) {
        List<Customer> customerList = customerRepository.findAll().stream().filter(c -> "STRIPE".equalsIgnoreCase(c.getPaymentType())).collect(Collectors.toList());

        model.addAttribute("customerList", customerList);
        return "charge";
    }


}