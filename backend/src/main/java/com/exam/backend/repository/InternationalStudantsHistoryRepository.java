package com.exam.backend.repository;

import com.exam.backend.entity.InternationalStudantsHistory;
import com.exam.backend.entity.StudentClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternationalStudantsHistoryRepository extends CrudRepository<InternationalStudantsHistory, Integer> {

}
