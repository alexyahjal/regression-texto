package com.web.texto.model.repo;

import java.util.List;

public interface DBModel{
    abstract List<String> modelToListString();
    abstract void setModelFromListString(List<String> excelData,String suiteName);
}
