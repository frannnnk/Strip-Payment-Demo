package com.frank.demo.model;

import lombok.Data;

@Data
public class TicketVerificationRequest  {
    private String ticket;
    private Integer systemId;
}
