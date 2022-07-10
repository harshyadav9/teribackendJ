package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "AllotedSlot")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class AllotedSlot implements Serializable {

    @EmbeddedId
    private AllotedSlotId id;
}