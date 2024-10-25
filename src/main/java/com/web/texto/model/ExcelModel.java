package com.web.texto.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ExcelModel {
    private List<String> sheetNameList;
    private List<List<List<String>>> data;
    private String filePath;

    private int totalSheet;
    private Map<Integer,Short> colsInsheetMap; //use only to ensure that all cell are going to be fill

    public ExcelModel(){
        init();
    }

    public ExcelModel( boolean onlyOneSheet ){
        init();
        if( onlyOneSheet) initForOneSheetOnly();

    }

    public List<List<String>> getSheetData( int sheetIndex ){
        return data.get( sheetIndex );
    }

    public List<List<String>> getFirstSheet(){
        return data.getFirst();
    }

    public void initForOneSheetOnly(){
        data.add( new ArrayList<>() );
        sheetNameList.add("Sheet 1");
    }

    private void init(){
        this.totalSheet = 0;
        sheetNameList = new ArrayList<>();
        data = new ArrayList<>();
        this.colsInsheetMap = new HashMap<>();
    }
}
