package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PaymentStatus")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class PaymentStatus implements Serializable {

    @EmbeddedId
    private AllotedSlotId id;
}