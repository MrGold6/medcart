package com.boots.repository;

import com.boots.entity.Disease;
import com.boots.entity.Visit;
import com.boots.transientClasses.DiseaseStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, String> {
    @Query("SELECT new com.boots.transientClasses.DiseaseStatistic(d, count(v)) FROM Disease d inner join Visit v ON v.disease = d.ICD_10 group by (d.name)")
    List<DiseaseStatistic> countDisease();

    @Query("SELECT new com.boots.transientClasses.DiseaseStatistic(d, count(v)) FROM Disease d inner join Visit v ON v.disease = d.ICD_10 where month(v.date) =:m group by (d.name)")
    List<DiseaseStatistic> countDiseaseMonth(@Param("m") int month);

}
