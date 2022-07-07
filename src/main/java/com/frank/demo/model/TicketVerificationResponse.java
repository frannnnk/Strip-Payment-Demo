package com.frank.demo.model;

import lombok.Data;

@Data
public class TicketVerificationResponse  {

    Integer code;
    String message;
    String localizedMessage;

    private SSOUser ssoUser;
}
