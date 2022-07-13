package com.exam.backend.repository;

import com.exam.backend.entity.School;
import com.exam.backend.entity.SchoolSlotData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends CrudRepository<School, String> {

    @Query(nativeQuery = true, value = "Update Schools set password = :password where schoolsCode = :schoolId")
    @Modifying
    void updatePassword(String password, String schoolId);

}
