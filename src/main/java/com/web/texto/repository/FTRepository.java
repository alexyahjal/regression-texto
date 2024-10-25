package com.web.texto.repository;

import com.web.texto.model.repo.FTModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FTRepository extends JpaRepository<FTModel, Integer> {

    List<FTModel> findAllBySuiteName(String suiteName);

}
