package com.exam.backend.entity;

import java.util.Date;

public interface TicketDetail {

    Integer getTicketID();

    String getCategoryName();

    String getStatusName();

    String getSubject();

    String getSchoolID_RollNo();

    String getSubscriberType();

    String getMessage();

    String getCreatedBy();

    Date getCreatedOn();

}