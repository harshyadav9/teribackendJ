package com.exam.backend.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HelpdeskTicketDto {

    private Integer ticketID;

    private Integer categoryID;

    private Integer statusID;

    private String subject;

    private String schoolID_RollNo;

    private String subscriberType;

    private String createdBy;

    private String modifiedBy;

    private String message;

}