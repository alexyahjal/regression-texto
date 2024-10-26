package com.web.texto.util;

import com.web.texto.model.ExcelModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileUtil {
    /**
     * Save the ExcelModel into a file as excel<br>
     * Require: <br>
     *
     * @param excelModel . SheetNameList
     * @param path
     * @throws Exception
     */
    public static void fileSaveToExcel(ExcelModel excelModel, String path) throws Exception {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet;
        Row row;
        for (int si = 0; si < excelModel.getSheetNameList().size(); si++) {
            sheet = wb.createSheet(excelModel.getSheetNameList().get(si));
            for (int ri = 0; ri < excelModel.getData().get(si).size(); ri++) {
                row = sheet.createRow(ri);
                for (int ci = 0; ci < excelModel.getData().get(si).get(ri).size(); ci++) {
                    row.createCell(ci).setCellValue(excelModel.getData().get(si).get(ri).get(ci));
                }
            }
        }
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(path);
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * Convert a Excel (.xls) file into a ExcelModel object
     *
     * @param path
     * @return ExcelModel
     * @throws Exception
     */
    public static ExcelModel fileReadExcel(String path) throws Exception {
        ExcelModel excelModel = new ExcelModel();
        HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(path));
        Sheet sheet;
        Row row;
        Cell cell;
        boolean isHeader = true;
        boolean isData = true;
        while (isData) {
            try {
                isHeader = true;
                sheet = workBook.getSheetAt(excelModel.getTotalSheet());
                excelModel.getData().add(
                        new ArrayList<>()
                );

                for (int ri = 0; ri < sheet.getPhysicalNumberOfRows(); ri++) {
                    excelModel.getData().get(excelModel.getTotalSheet()).add(
                            new ArrayList<>()
                    );
                    row = sheet.getRow(ri);
                    if (isHeader) {
                        excelModel.getColsInsheetMap().put(excelModel.getTotalSheet(), row.getLastCellNum());
                        isHeader = false;
                    }
                    for (int ci = 0; ci < excelModel.getColsInsheetMap().get( excelModel.getTotalSheet() ); ci++) {
                        cell = row.getCell(ci);
                        if (cell == null) excelModel.getData().get(excelModel.getTotalSheet()).get(ri).add("");
                        else excelModel.getData().get(excelModel.getTotalSheet()).get(ri).add(getCellStringValue(cell));
                    }
                }
                excelModel.setTotalSheet(
                        excelModel.getTotalSheet() + 1
                );
                excelModel.getSheetNameList().add(
                        sheet.getSheetName()
                );
            } catch (Exception e) {
                isData = false;
            }
        }
        excelModel.setFilePath(path);
        workBook.close();
        return excelModel;
    }

    /**
     * @param cell
     * @return
     */
    private static String getCellStringValue(Cell cell) {
        try {
            return cell.getStringCellValue();
        } catch (Exception ex) {
            return "" + cell.getNumericCellValue();
        }
    }
}
