package com.frank.demo.controllers;

import com.braintreegateway.*;
import com.frank.demo.StripePOC;
import com.frank.demo.repository.CustomerRepository;
import com.frank.demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class BraintreePageController {

    private BraintreeGateway gateway = StripePOC.gateway;

    @Autowired
    CustomerRepository customerRepository;

    private Transaction.Status[] TRANSACTION_SUCCESS_STATUSES = new Transaction.Status[] {
            Transaction.Status.AUTHORIZED,
            Transaction.Status.AUTHORIZING,
            Transaction.Status.SETTLED,
            Transaction.Status.SETTLEMENT_CONFIRMED,
            Transaction.Status.SETTLEMENT_PENDING,
            Transaction.Status.SETTLING,
            Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };


    @Autowired
    Environment env;

    @GetMapping("/paypal-charge-customer")
    public String charge(Model model) {
        List<com.frank.demo.entity.Customer> customerList = customerRepository.findAll().stream().filter(c -> "BRAINTREE".equalsIgnoreCase(c.getPaymentType())).collect(Collectors.toList());

        for (com.frank.demo.entity.Customer customer : customerList) {
            if (CommonUtil.isExNull(customer.getCustomerBraintreeDefaultPaymentMethodImageURL())) {
                Customer bc = gateway.customer().find(customer.getCustomerBraintreeId());
                customer.setCustomerBraintreeDefaultPaymentMethodImageURL(bc.getDefaultPaymentMethod().getImageUrl());
                customerRepository.save(customer);
            }
        }

        model.addAttribute("customerList", customerList);
        return "charge-paypal";
    }

    @GetMapping("/paypal")
    public String paypal(Model model) {
        String clientToken = gateway.clientToken().generate();
        model.addAttribute("clientToken", clientToken);
        return "paypal";
    }

    @GetMapping("/checkouts")
    public String paypalDI(Model model) {
        String clientToken = gateway.clientToken().generate();
        model.addAttribute("clientToken", clientToken);
        return "paypal-dropin";
    }

    @GetMapping("/paypal-save-customer")
    public String paypalSave(Model model) {
        String clientToken = gateway.clientToken().generate();
        model.addAttribute("clientToken", clientToken);
        return "paypal-dropin-save-customer";
    }

    @PostMapping("/checkouts")
    public String paypalDIPost(@RequestParam("amount") String amount, @RequestParam("payment_method_nonce") String nonce, Model model, final RedirectAttributes redirectAttributes) {
            BigDecimal decimalAmount;
            try {
                decimalAmount = new BigDecimal(amount);
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("errorDetails", "Error: 81503: Amount is an invalid format.");
                return "redirect:checkouts";
            }

            TransactionRequest request = new TransactionRequest()
                    .amount(decimalAmount)
                    .paymentMethodNonce(nonce)
                    .options()
                    .submitForSettlement(true)
                    .done();

            Result<Transaction> result = gateway.transaction().sale(request);

            if (result.isSuccess()) {
                Transaction transaction = result.getTarget();
                return "redirect:checkouts/" + transaction.getId();
            } else if (result.getTransaction() != null) {
                Transaction transaction = result.getTransaction();
                return "redirect:checkouts/" + transaction.getId();
            } else {
                String errorString = "";
                for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                    errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
                }
                redirectAttributes.addFlashAttribute("errorDetails", errorString);
                return "redirect:checkouts";
            }
    }

    @PostMapping("/checkouts-save")
    public String paypalDIPostSaveCus(@RequestParam("payment_method_nonce") String nonce, Model model, final RedirectAttributes redirectAttributes) {

        String name = CommonUtil.generateRandomString(5);
        CustomerRequest request = new CustomerRequest()
                .firstName("Frank")
                .email("frank."+name+"@example.com")
                .lastName(name)
                .paymentMethodNonce(nonce);

        Result<Customer> result = gateway.customer().create(request);
        model.addAttribute("isSuccess", result.isSuccess());
        Customer customer = result.getTarget();

        model.addAttribute("customerId", customer.getId());
        model.addAttribute("customerPaymentMethods", customer.getPaymentMethods());


        if (result.isSuccess()) {
            com.frank.demo.entity.Customer udCustomer = new com.frank.demo.entity.Customer();
            udCustomer.setPaymentType("BRAINTREE");
            udCustomer.setCustomerBraintreeId(customer.getId());
            udCustomer.setCustomerBraintreePaymentMethodToken( customer.getPaymentMethods().get(0).getToken() );
            udCustomer.setCreateTime(new Date());
            udCustomer.setCustomerBraintreeDefaultPaymentMethodImageURL(customer.getDefaultPaymentMethod().getImageUrl());
            customerRepository.save(udCustomer);
        }


        return "show-save-cus-result";
    }

    @GetMapping (value = "/checkouts/{transactionId}")
    public String getTransaction(@PathVariable String transactionId, Model model) {
        Transaction transaction;
        CreditCard creditCard;
        Customer customer;

        try {
            transaction = gateway.transaction().find(transactionId);
            creditCard = transaction.getCreditCard();
            customer = transaction.getCustomer();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return "redirect:/checkouts";
        }

        model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
        model.addAttribute("transaction", transaction);
        model.addAttribute("creditCard", creditCard);
        model.addAttribute("customer", customer);

        return "show";
    }




}