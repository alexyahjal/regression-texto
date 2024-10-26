package com.web.texto.model.repo;

import java.util.List;

public interface DBModel extends Comparable{
    List<String> modelToListString();
    void setModelFromListString(List<String> excelData,String suiteName);
    boolean isModelEmpty();
}
