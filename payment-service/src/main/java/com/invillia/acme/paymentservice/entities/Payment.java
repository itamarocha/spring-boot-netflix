package com.invillia.acme.paymentservice.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "The database generated order ID")
	private Long id;

	@ApiModelProperty(notes = "Order ID")
	@Column(nullable=false)
	private String orderId;

	@ApiModelProperty(notes = "Credit card")
	private String creditCard;

	@ApiModelProperty(notes = "Payment status")
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@ApiModelProperty(notes = "Payment Date")
	private Date paymentDate;

}
