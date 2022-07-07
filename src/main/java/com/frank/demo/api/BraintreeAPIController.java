package com.frank.demo.api;

import com.braintreegateway.*;
import com.frank.demo.StripePOC;
import com.frank.demo.model.APIResponse;
import com.frank.demo.model.BraintreeChargeRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Log4j2
@RestController
public class BraintreeAPIController {


    private BraintreeGateway gateway = StripePOC.gateway;

    @PostMapping(value = "/api/paypal/chargeOnce",
                 produces = {"application/json"},
                 consumes = {"application/json"})
    public ResponseEntity<APIResponse> chargeOnce(@RequestBody BraintreeChargeRequest chargeRequest) {

        APIResponse response = new APIResponse();

        BigDecimal decimalAmount;
        try {
            decimalAmount = new BigDecimal(chargeRequest.getAmount());
        } catch (NumberFormatException e) {
            response.setCode(-1);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Invalid amount");
            return new ResponseEntity<>( response, response.getHttpStatus());
        }

        TransactionRequest request = new TransactionRequest()
                .customerId(chargeRequest.getCustomerBraintreeId())
                .amount(decimalAmount)
                //.paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(request);
        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            response.setCode(0);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("OK. TransactionID: "+transaction.getId());
            return new ResponseEntity<>( response, response.getHttpStatus());
        } else if (result.getTransaction() != null) {
            response.setCode(-2);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Unknow error case -2 "+result.getTransaction().toString());
            return new ResponseEntity<>( response, response.getHttpStatus());
        } else {
            String errorString = "";
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
            }
            response.setCode(-3);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Unknow error case -3 "+errorString);
            return new ResponseEntity<>( response, response.getHttpStatus());
        }

    }




}
