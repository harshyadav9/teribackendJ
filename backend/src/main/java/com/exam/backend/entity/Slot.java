package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "Slot")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Slot implements Serializable {

    @Id
    @Column(name = "SlotID")
    private int slotId;

    @Column(name = "Mode")
    private String mode;

    @Column(name = "Examtheme")
    private String examTheme;

    @Column(name = "DateofExam")
    private Date dateOfExam;

    @Column(name = "Slotdatetime")
    private String slotDateTime;

    @Column(name = "SeatAvailable")
    private int seatAvailable;

    @Column(name = "TotalSeat")
    private int totalSeat;

}
