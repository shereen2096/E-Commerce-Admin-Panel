package com.examly.springapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemCreateRequest {

    private Long productId;

    private Integer quantity;
}