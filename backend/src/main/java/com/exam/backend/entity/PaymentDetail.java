package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "PaymentDetail")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class PaymentDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentDetailID")
    private int paymentDetailId;

    @Column(name = "SchoolID_RollNo")
    private String schoolID_RollNo;

    @Column(name = "SubscriberType")
    private String subscriberType;

    @Column(name = "Amount")
    private BigDecimal amount;

    @Column(name = "OrderId")
    private String orderId;

    @Column(name = "PaymentStatus")
    private String paymentStatus;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "modifyBy")
    private String ModifyBy;
}