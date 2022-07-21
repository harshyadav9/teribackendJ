package com.exam.backend.repository;

import com.exam.backend.entity.InternationalStudant;
import com.exam.backend.entity.RollNumberData;
import com.exam.backend.entity.SchoolSlotData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternationalStudantsRepository extends CrudRepository<InternationalStudant, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM vwGetExamSlotByTheme where schoolId = :schoolId and mode = :mode")
    List<SchoolSlotData> getData(String schoolId, String mode);

    List<InternationalStudant> findBySchoolIdAndExamTheme(String schoolId, String examTheme);

    List<InternationalStudant> findBySchoolIdAndDemoExam(String schoolId, String examTheme);

    List<InternationalStudant> findAllBySchoolIdAndPaymentStatusAndRollNoNull(String schoolId, Boolean paymentStatus);

    long countBySchoolIdAndPaymentStatusAndRollNoNotNull(String schoolId, Boolean paymentStatus);

    @Query(nativeQuery = true, value = "select l.rollno from (\n" +
            "SELECT *,   \n" +
            "    ROW_NUMBER() OVER(PARTITION BY SchoolID) AS row_num  \n" +
            "FROM InternationalStudants where SchoolID = :schoolId and paymentStatus = :paymentStatus and rollno is not null order by row_num desc limit 1) as l")
    String findRollNumberForAlreadyPaidSchool(String schoolId, Boolean paymentStatus);

    @Query(nativeQuery = true, value = "SELECT cntry.Code AS CountryCode,'22' AS Year\n" +
            ",CASE WHEN ct.CityCode IS NULL THEN CASE WHEN LENGTH(inst.Srn)=1 THEN CONCAT('0',inst.Srn) ELSE inst.Srn END\n" +
            "WHEN inst.Srn IS NULL THEN CASE WHEN LENGTH(ct.CityCode)=1 THEN CONCAT('0',ct.CityCode) ELSE ct.CityCode END \n" +
            "ELSE NULL END AS StateCode\n" +
            ",RIGHT(sch.SchoolsCode,4) AS SchoolNumber\n" +
            "FROM Schools sch\n" +
            "JOIN country cntry\n" +
            "ON cntry.Country = sch.country\n" +
            "LEFT JOIN city ct \n" +
            "ON ct.countrycode = cntry.Code\n" +
            "AND ct.CityName = sch.State\n" +
            "LEFT JOIN indian_state inst\n" +
            "ON inst.statename = sch.State\n" +
            "WHERE sch.SchoolsCode = :schoolId")
    RollNumberData getSchoolDataForGivenSchool(String schoolId);

}
