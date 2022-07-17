package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HelpdeskTicket")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class HelpdeskTicket implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "TicketID")
    private int ticketID;

    @Column(name = "CategoryID")
    private int categoryID;

    @Column(name = "StatusID")
    private int statusID;

    @Column(name = "Subject")
    private String subject;

    @Column(name = "SchoolID_RollNo")
    private String schoolID_RollNo;

    @Column(name = "SubscriberType")
    private String subscriberType;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "ModifiedBy")
    private String modifiedBy;

}