package com.exam.backend.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
@Setter
public class SchoolSlotUpdateStatus {

    private Map<String, String> status;

    private boolean errored;

}
