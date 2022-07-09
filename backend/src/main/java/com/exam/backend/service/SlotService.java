package com.exam.backend.service;

import com.exam.backend.entity.SchoolSlotData;
import com.exam.backend.pojo.SchoolSlotDataIncoming;
import com.exam.backend.pojo.SchoolSlotUpdateStatus;

import java.util.List;

public interface SlotService {

    List<SchoolSlotData> getSlotsData(String schoolId, String mode);

    SchoolSlotUpdateStatus updateSlotData(List<SchoolSlotDataIncoming> data);
}
