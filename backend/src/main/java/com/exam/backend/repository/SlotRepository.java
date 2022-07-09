package com.exam.backend.repository;

import com.exam.backend.entity.Slot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SlotRepository extends CrudRepository<Slot, Integer> {

    Slot findBySlotId(int slotId);

}
