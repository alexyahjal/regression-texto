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
    private List<Map<String, CellModel>> data;

    public int getRowCount() {
        return data.size();
    }

    public int getColCount() {
        return columnOrder.size();
    }

    /**
     * Prepare the object to be the holder of an Excel File
     *
     * @param fileName
     */
    public ExcelModel(String fileName) {
        this.fileName = fileName;
        this.data = new ArrayList<>();
        this.columnOrder = new ArrayList<>();
    }

    /**
     * Prepare the object to define the excel to be writen
     *
     * @param fileName
     * @param columnOrder
     */
    public ExcelModel(String fileName, List<String> columnOrder) {
        if (fileName.endsWith("xlsx")) this.fileName = fileName;
        else this.fileName = fileName + ".xlsx";
        this.columnOrder = new ArrayList<>();
        this.data = new ArrayList<>();
        data.add(new HashMap<>());
        int i = 0;
        for (String col : columnOrder) {
            if (col == null || col.isEmpty()) this.columnOrder.add("Column" + (i + 1));
            else this.columnOrder.add(col);
            data.get(0).put(this.columnOrder.get(i), new CellModel(this.columnOrder.get(i)));
            i++;
        }
    }

    /**
     * is for read excel file, first row is header row
     *
     * @param row
     * @param col
     * @param cellModel
     */
    protected void initCells(int row, int col, CellModel cellModel) {
        if (row == 0) {
            columnOrder.add(cellModel.getData());
        }
        if (col == 0) this.data.add(new HashMap<>());
        this.data.get(row).put(columnOrder.get(col), cellModel);
    }

    /**
     * Given the row/col, update the values with the same values of the given CellModel<br>
     * this operation do not update the object, update the values on the object
     *
     * @param row
     * @param col
     * @param cellModel
     */
    public void setCell(int row, int col, CellModel cellModel) {
        this.data.get(row).get(columnOrder.get(col)).setData(cellModel.getData());
        this.data.get(row).get(columnOrder.get(col)).setColor(cellModel.getColor());
        this.data.get(row).get(columnOrder.get(col)).setBackgroundColor(cellModel.getBackgroundColor());
    }

    /**
     * Given the row/col, update the value of the attribute
     *
     * @param row
     * @param col
     * @param cellModelData
     * @param attribute
     */
    public void setCell(int row, int col, String cellModelData, CellEnum attribute) {
        switch (attribute) {
            case DATA -> this.data.get(row).get(columnOrder.get(col)).setData(cellModelData);
            case COLOR -> this.data.get(row).get(columnOrder.get(col)).setColor(cellModelData);
            case BACKGROUND_COLOR -> this.data.get(row).get(columnOrder.get(col)).setBackgroundColor(cellModelData);
        }
    }

    /**
     * Given the row/col, update the value of the attribute DATA
     *
     * @param row
     * @param col
     * @param cellModelData
     */
    public void setCell(int row, int col, String cellModelData) {
        this.data.get(row).get(columnOrder.get(col)).setData(cellModelData);
    }

    /**
     * Given the row/col, return the CellModel.data String
     *
     * @param row
     * @param col
     * @return
     */
    public String getCellData(int row, int col) {
        return this.data.get(row).get(columnOrder.get(col)).getData();

    }

    /**
     * given the row/col, return the CellModel, if modified, the ExcelModel itself will be modified
     *
     * @param row
     * @param col
     * @return
     */
    public CellModel getCellModel(int row, int col) {
        return this.data.get(row).get(columnOrder.get(col));

    }

    /**
     * @param rowData
     */
    public <T> void addNewRow(List<T> rowData) {
        if( rowData == null ) return;
        data.add(new HashMap<>());
        for (int i = 0; i < rowData.size(); i++) {
            if( i >= columnOrder.size() ) continue;
            if( rowData.get(i) == null ){
                data.get(data.size() - 1).put(columnOrder.get(i),new CellModel(""));
                continue;
            }
            if (rowData.get(i).getClass().equals(CellModel.class)) data.get(data.size() - 1)
                    .put(columnOrder.get(i),
                            new CellModel(
                                    ((CellModel) rowData.get(i)).getData(),
                                    ((CellModel) rowData.get(i)).getColor(),
                                    ((CellModel) rowData.get(i)).getBackgroundColor()
                            )
                    );
            else data.get(data.size() - 1)
                    .put(columnOrder.get(i),
                            new CellModel(
                                    rowData.get(i).toString()
                            )
                    );
        }
        if( columnOrder.size() > rowData.size() ){
            for( int i = rowData.size() ; i < columnOrder.size(); i++){
                data.get(data.size()-1).put( columnOrder.get(i),new CellModel(""));
            }
        }

    }
}