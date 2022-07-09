package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@ToString
@Embeddable
@Getter
@Setter
public class AllotedSlotId implements Serializable {

    public AllotedSlotId() {
    }

    @Column(name = "SlotID")
    private int slotId;

    @Column(name = "AllotedSchoolID")
    private String allotedSchoolId;
}