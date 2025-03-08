package com.example.demo.util.excel;

import org.apache.poi.ss.usermodel.IndexedColors;

public enum CellColorEnum {
    WHITE(IndexedColors.WHITE.getIndex()),
    BLACK(IndexedColors.BLACK.getIndex()),
    RED(IndexedColors.RED.getIndex()),
    BLUE(IndexedColors.BLUE.getIndex()),
    YELLOW(IndexedColors.YELLOW.getIndex()),
    ORANGE(IndexedColors.ORANGE.getIndex());

    private short value;

    CellColorEnum(short value){
        this.value = value;
    }

    public short getValue(){
        return this.value;
    }

}
