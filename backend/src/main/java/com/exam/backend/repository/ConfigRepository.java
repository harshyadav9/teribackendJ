package com.exam.backend.repository;

import com.exam.backend.entity.Config;
import com.exam.backend.entity.InternationalStudant;
import com.exam.backend.entity.RollNumberData;
import com.exam.backend.entity.SchoolSlotData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends CrudRepository<Config, Integer> {

    @Query(nativeQuery = true, value = "SELECT ConfigValue FROM CONFIG where configName = :configName")
    String getConfigValueForConfigName(String configName);

}
