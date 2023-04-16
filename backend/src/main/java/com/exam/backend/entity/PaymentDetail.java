package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "PaymentDetail")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class PaymentDetail implements Serializable {

    @Id
    @Column(name = "OrderId")
    private String orderId;

    @Column(name = "Schoolcode_Rollno")
    private String schoolcode_Rollno;

    @Column(name = "Mode")
    private String mode;

    @Column(name = "SubscriberType")
    private String subscriberType;

    @Column(name = "Amount")
    private BigDecimal amount;

    @Column(name = "PaymentID")
    private String paymentId;

    @Column(name = "PaymentReceivedStatus")
    private String paymentReceivedStatus;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "ModifyBy")
    private String modifyBy;
}