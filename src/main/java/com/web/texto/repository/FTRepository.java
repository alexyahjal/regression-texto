package com.web.texto.repository;

import com.web.texto.model.repo.FTModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FTRepository extends JpaRepository<FTModel, Integer> {

    List<FTModel> findAllBySuiteName(String suiteName);

    /**
     * SELECT * FROM functional_test ft WHERE ft.suite_name='SuiteA'
     * @return
     */
    @Query(
            value = "SELECT DISTINCT suite_name FROM functional_test",
            nativeQuery = true)
    List<String> findAllSuiteName();

}
