package com.exam.backend.repository;

import com.exam.backend.entity.IndividualStudent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualStudentRepository extends CrudRepository<IndividualStudent, String> {

    @Query(nativeQuery = true, value = "SELECT max(RIGHT(RollNo, 4)) from IndividualStudent;")
    Integer findLastRunningRollNumber();

    IndividualStudent findByRollNumber(String rollNumber);

}
