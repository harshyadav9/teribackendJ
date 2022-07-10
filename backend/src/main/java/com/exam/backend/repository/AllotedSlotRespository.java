package com.exam.backend.repository;

import com.exam.backend.entity.AllotedSlot;
import com.exam.backend.entity.StudentClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AllotedSlotRespository extends CrudRepository<AllotedSlot, Integer> {

}
