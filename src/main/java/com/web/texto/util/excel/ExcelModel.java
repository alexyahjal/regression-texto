package com.example.demo.util.excel;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelModel {
    private static final CellModel emptyCell = new CellModel();

    @Getter
    private String fileName;
    @Getter
    private List<String> columnOrder;
    private List<Map<String,CellModel>> data;

    public int getRowCount(){
        return data.size();
    }
    public int getColCount(){
        return columnOrder.size();
    }

    public ExcelModel( String fileName) {
        this.fileName = fileName;
        this.data = new ArrayList<>();
        this.columnOrder = new ArrayList<>();
    }

    protected void initCells( int row, int col, CellModel cellModel){
        if( row == 0 ){
            columnOrder.add( cellModel.getData() );
        }
        if( col == 0 ) this.data.add( new HashMap<>() );
        this.data.get(row).put( columnOrder.get( col ) , cellModel );
    }

    public void setCell( int row, int col, CellModel cellModel ){
        this.data.get( row ).get( columnOrder.get( col ) ).setData( cellModel.getData() );
        this.data.get( row ).get( columnOrder.get( col ) ).setColor( cellModel.getColor() );
        this.data.get( row ).get( columnOrder.get( col ) ).setBackgroundColor( cellModel.getBackgroundColor() );
    }

    public void setCell(int row, int col, String cellModelData, Cell attribute ){
        switch( attribute ){
            case DATA -> this.data.get( row ).get( columnOrder.get( col ) ).setData( cellModelData );
            case COLOR -> this.data.get( row ).get( columnOrder.get( col ) ).setColor( cellModelData );
            case BACKGROUND_COLOR -> this.data.get( row ).get( columnOrder.get( col ) ).setBackgroundColor( cellModelData );
        }
    }

    public void setCell(int row, int col, String cellModelData ){
        this.data.get( row ).get( columnOrder.get( col ) ).setData( cellModelData );
    }

    public String getCellData( int row, int col ){
        return this.data.get(row).get( columnOrder.get(col)).getData();

    }

    public CellModel getCellModel( int row, int col ){
        return this.data.get(row).get( columnOrder.get(col));

    }

}


