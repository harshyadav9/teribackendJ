package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HelpdeskTicketDetail")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class HelpdeskTicketDetail implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "TicketDetailID")
    private int ticketDetailID;

    @Column(name = "TicketID")
    private int ticketID;

    @Column(name = "TicketStatusID")
    private int ticketStatusID;

    @Column(name = "Message")
    private String message;

    @Column(name = "CreatedBy")
    private String createdBy;

}