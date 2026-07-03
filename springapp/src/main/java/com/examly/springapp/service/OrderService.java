package com.examly.springapp.service;

import com.examly.springapp.dto.OrderCreateRequest;
import com.examly.springapp.dto.OrderItemCreateRequest;
import com.examly.springapp.model.Order;
import com.examly.springapp.model.OrderItem;
import com.examly.springapp.model.Product;
import com.examly.springapp.repository.OrderRepository;
import com.examly.springapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

public Order create(OrderCreateRequest request) {

 if (request == null || request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
 throw new RuntimeException("Order items cannot be empty");
 }

Order order = new Order();
order.setOrderItems(new java.util.ArrayList<>());
 order.setCustomerName(request.getCustomerName());
 order.setCustomerEmail(request.getCustomerEmail());
 order.setShippingAddress(request.getShippingAddress());
 order.setStatus("PENDING");
 order.setOrderDate(LocalDateTime.now());

 // IMPORTANT
 order.setOrderItems(new ArrayList<>());

 double total = 0.0;

 for (OrderItemCreateRequest itemRequest : request.getOrderItems()) {

 Product product = productRepository.findById(itemRequest.getProductId())
 .orElseThrow(() -> new RuntimeException("Product not found"));

 if (product.getStockQuantity() < itemRequest.getQuantity()) {
 throw new RuntimeException("Insufficient stock");
 }

 // Reduce stock
 product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
 productRepository.save(product);

 OrderItem orderItem = new OrderItem();
 orderItem.setOrder(order);
 orderItem.setProduct(product);
 orderItem.setQuantity(itemRequest.getQuantity());
 orderItem.setPriceAtPurchase(product.getPrice());

 order.getOrderItems().add(orderItem);

 total += product.getPrice() * itemRequest.getQuantity();
 }

 order.setTotalAmount(total);

 return orderRepository.save(order);
 }

 public Order updateStatus(Long id, String status) {

 if (!status.equals("PENDING")
 && !status.equals("PROCESSING")
 && !status.equals("SHIPPED")
 && !status.equals("DELIVERED")) {

 throw new RuntimeException("Invalid status");
 }

 Order order = orderRepository.findById(id)
 .orElseThrow(() -> new RuntimeException("Order not found"));

 order.setStatus(status);

 return orderRepository.save(order);
 }
}