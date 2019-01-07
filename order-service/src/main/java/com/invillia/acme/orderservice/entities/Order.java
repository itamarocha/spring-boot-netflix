package com.invillia.acme.orderservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated order ID")
    private Long id;
    @ApiModelProperty(notes = "Order Confirmation date") 
    private Date confirmationDate;
    @ApiModelProperty(notes = "Customer address") 
    private String customerAddress;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

}
