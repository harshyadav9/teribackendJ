package com.exam.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentDetailDto implements Serializable {

    private String orderId;

    private String schoolcode_Rollno;

    private String mode;

    private String subscriberType;

    private BigDecimal amount;

    private String paymentId;

    private String paymentReceivedStatus;

    private String createdBy;

    private String modifyBy;

    private Date paymentDate;
}