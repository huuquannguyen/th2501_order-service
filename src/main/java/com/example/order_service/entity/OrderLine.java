package com.example.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @ManyToOne
    @JsonIgnore
    private OrderEntity order;

    private Long productId;

    private boolean inCart;

    private Integer quantity;

    private String size;

    private String color;

    private Double price;

    private String imageUrl;
}
