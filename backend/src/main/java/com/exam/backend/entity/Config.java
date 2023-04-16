package com.exam.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CONFIG")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Config implements Serializable {

    @Id
    @Column(name = "ConfigId")
    private Integer configId;

    @Column(name = "ConfigName")
    private String configName;

    @Column(name = "ConfigValue")
    private String configValue;

}