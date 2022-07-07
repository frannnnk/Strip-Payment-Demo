package com.frank.demo.api;

import com.frank.demo.model.APIResponse;
import com.frank.demo.model.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
public class StripeAPIController {



    @PostMapping(value = "/api/chargeOnce",
                 produces = {"application/json"},
                 consumes = {"application/json"})
    public ResponseEntity<APIResponse> chargeOnce(@RequestBody ChargeRequest chargeRequest) {

        APIResponse response = new APIResponse();



        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_MZ6ZiNbihYcwWq7Z9L4gJ0Ym";

        // Token is created using Checkout or Elements!

        Map<String, Object> params = new HashMap<>();
        params.put("amount", Integer.parseInt(chargeRequest.getAmount()));
        params.put("currency", "hkd");
        params.put("description", chargeRequest.getDescription());
        params.put("source", chargeRequest.getStripToken());

        Charge charge = null;
        try {
            charge = Charge.create(params);
            response.setCode(0);
            response.setHttpStatus(HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            response.setCode(-1);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>( response, response.getHttpStatus());
    }

    @PostMapping(value = "/api/chargeMany",
                 produces = {"application/json"},
                 consumes = {"application/json"})
    public ResponseEntity<APIResponse> chargeMany(@RequestBody ChargeRequest chargeRequest) {

        APIResponse response = new APIResponse();



        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_MZ6ZiNbihYcwWq7Z9L4gJ0Ym";

        // Token is created using Checkout or Elements!

        Map<String, Object> params = new HashMap<>();
        params.put("amount", Integer.parseInt(chargeRequest.getAmount()));
        params.put("currency", "hkd");
        params.put("description", chargeRequest.getDescription());
        params.put("customer", chargeRequest.getCustomerStripeId());

        Charge charge = null;
        try {
            charge = Charge.create(params);
            response.setCode(0);
            response.setHttpStatus(HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            response.setCode(-1);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>( response, response.getHttpStatus());
    }



}
