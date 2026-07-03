package com.examly.springapp.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateRequest {

    private String customerName;

    private String customerEmail;

    private String shippingAddress

    private List<OrderItemCreateRequest> orderItems;
}