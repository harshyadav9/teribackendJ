package com.exam.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class PaymentDetailDto implements Serializable {

    private String schoolID_RollNo;

    private String subscriberType;

    private BigDecimal amount;

    private String orderId;

    private String paymentStatus;

    private String createdBy;

    private String ModifyBy;
}