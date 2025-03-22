package com.web.texto.util.excel;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;

public class ExcelUtil {

    /**
     * Read XLSX file and convert in ExcelModel object<br>
     * first row is header row
     *
     * @param filename
     * @return
     * @throws Exception
     */
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

    /**
     * @param excelModel
     * @throws Exception
     */
    public static void writeExcel(ExcelModel excelModel) throws Exception {
        writeExcel(excelModel.getFileName(), excelModel);
    }

    /**
     * Write file
     *
     * @param filename
     * @param excelModel
     * @throws Exception
     */
    public static void writeExcel(String filename, ExcelModel excelModel) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        for (int r = 0; r < excelModel.getRowCount(); r++) {
            Row row = sheet.createRow(r);
            for (int c = 0; c < excelModel.getColCount(); c++) {
                Cell cell = row.createCell(c);
                cell.setCellValue(excelModel.getCellModel(r, c).getData());

                XSSFCellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                updateStyle(excelModel.getCellModel(r, c), style, font);
                cell.setCellStyle(style);
            }
        }
        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    private static short getColorShortByString(String color) {
        for (CellColorEnum colorEnum : CellColorEnum.values()) {
            if (colorEnum.name().equals(color)) return colorEnum.getValue();
        }
        return 0;
    }

    private static void updateStyle(CellModel cellModel, XSSFCellStyle style, Font font) {
        font.setColor(
                getColorShortByString(
                        cellModel.getColor()
                )
        );
        style.setFont(font);
        if (!cellModel.getBackgroundColor().equals(CellColorEnum.WHITE.name())) {
            style.setFillPattern(FillPatternType.BIG_SPOTS);
            style.setFillForegroundColor(
                    getColorShortByString(
                            cellModel.getBackgroundColor()
                    )
            );
        }

    }
}
