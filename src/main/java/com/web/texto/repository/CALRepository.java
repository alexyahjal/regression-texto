package com.web.texto.repository;

import com.web.texto.model.repo.CALModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CALRepository extends JpaRepository<CALModel, Integer> {

    List<CALModel> findAllBySuiteName(String suiteName);

    /**
     * SELECT * FROM functional_test ft WHERE ft.suite_name='SuiteA'
     * @return
     */
    @Query(
            value = "SELECT DISTINCT suite_name FROM cal_log",
            nativeQuery = true)
    List<String> findAllSuiteName();

}
