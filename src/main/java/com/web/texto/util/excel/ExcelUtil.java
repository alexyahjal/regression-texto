package com.example.demo.util.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelUtil {

    public static ExcelModel readExcel(String filename) throws Exception {
        ExcelModel excelModel = new ExcelModel(filename);
        XSSFWorkbook workbook = new XSSFWorkbook(filename);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
            XSSFRow row = sheet.getRow(r);
            if (row != null) {
                for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                    XSSFCell cell = row.getCell(c);
                    if (cell != null) {
                        excelModel.initCells(
                                r, c, new CellModel(
                                        cell.getCellType().equals(CellType.STRING)
                                                ? cell.getStringCellValue()
                                                : cell.getRawValue()
                                )
                        );
                    }
                }
            }
        }
        workbook.close();
        return excelModel;
    }

    public static void writeExcel(String filename, ExcelModel excelModel) throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        for( int r = 0; r < excelModel.getRowCount(); r++ ){
            Row row = sheet.createRow( r );
            for (int c = 0 ; c < excelModel.getColCount(); c++ ){
                Cell cell = row.createCell( c );
                cell.setCellValue(
                        excelModel.getCellData( r,c )
                );
            }
        }
        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}
