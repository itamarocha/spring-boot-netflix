package com.invillia.acme.orderservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated order ID")
    private Long id;
    @ApiModelProperty(notes = "Product code")
    private String product;
    @ApiModelProperty(notes = "Description")
    private String description;
    @ApiModelProperty(notes = "Product quantity")
    private int quantity;
    @ApiModelProperty(notes = "Product unit Price")
    private BigDecimal unitPrice;
    /**
     * Get price total item
     * @return
     */
    public BigDecimal getPrice() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}
