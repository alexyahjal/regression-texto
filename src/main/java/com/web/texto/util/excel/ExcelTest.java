package com.web.texto.util.excel;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;


public class ExcelTest {
    private final String desktopPath = "/home/alejandro/Escritorio/";
    private final String testFolder = desktopPath + "demo/src/test/java/com/example/demo/";

    @Test
    public void test_readCSVFile(){
        try{
            BufferedReader reader = new BufferedReader( new FileReader( "/home/alejandro/Escritorio/demo/src/test/java/com/example/demo/test.csv" ));
            List<String> lineList = new ArrayList<>();
            boolean notNull = true;
            while( notNull ){
                String line = reader.readLine();
                if( line == null ){
                    notNull = false;
                    continue;
                }
                lineList.add(line);
            }
            lineList.forEach( System.out::println );
        }catch (Exception e){
            fail(e.toString());
        }
    }

    @Test
    public void test_readExcel(){
        try{
            ExcelModel excelModel = ExcelUtil.readExcel( desktopPath + "test.xlsx" );
            System.out.println( "File: " + excelModel.getFileName() );
            System.out.println( "Columns: " + excelModel.getColumnOrder() );
            System.out.println( "Total rows: " + excelModel.getRowCount() );
        }catch (Exception e){
            fail(e.toString());
        }
    }

    @Test
    public void test_readExcelModifyAndCopy(){
        try{
            ExcelModel excelModel = ExcelUtil.readExcel( desktopPath + "test.xlsx" );
            System.out.println( "File read: " + excelModel.getFileName() );
            excelModel.setCell( 0,1,"MODIFY");
            excelModel.setCell( 0,2,"MODIFY_DATA", CellEnum.DATA);
            excelModel.setCell( 0,2,"MODIFY_COLOR", CellEnum.COLOR);
            excelModel.setCell( 0,2,"MODIFY,BACKGROUND,COLOR", CellEnum.BACKGROUND_COLOR);
            System.out.println( "Cell 0,1 and 0,2 modified ");
            ExcelUtil.writeExcel( desktopPath + "test_copy.xlsx" , excelModel );
            System.out.println( "File copied: " + desktopPath + "test_copy.xlsx" );
        }catch (Exception e){
            fail(e.toString());
        }
    }

    @Test
    public void test_createExcelModelAndSave(){
        try{
            ;
            ExcelModel excelModel = new ExcelModel( desktopPath + "testWrite.xlsx", Arrays.asList("Col1","Col2","Col3","") );
            excelModel.addNewRow( Arrays.asList("1"));
            excelModel.addNewRow( Arrays.asList(1,2,null));
            System.out.println("add 1 element of 4 total");
            excelModel.addNewRow( Arrays.asList("1","2"));
            System.out.println("add 2 element of 4 total");
            excelModel.addNewRow( Arrays.asList("1","2","3"));
            System.out.println("add 3 element of 4 total");
            excelModel.addNewRow( Arrays.asList("1","2","","4"));
            excelModel.addNewRow( Arrays.asList("1","2","3","4"));
            excelModel.addNewRow( Arrays.asList("1","2","3","4"));
            excelModel.addNewRow( Arrays.asList("1","2","3","4"));
            System.out.println("add 4 element of 4 total");
            excelModel.addNewRow( Arrays.asList("1",null,"3","4"));
            excelModel.addNewRow( Arrays.asList("1",null,"","4"));
            System.out.println("add 4 element of 4 total, one is null");
            excelModel.addNewRow( Arrays.asList("1","2","3","4",null));
            excelModel.addNewRow( Arrays.asList("1","2","3","4","5"));
            excelModel.addNewRow( Arrays.asList("1","2","3","4",""));
            excelModel.addNewRow( Arrays.asList("1","","3","4",null));
            excelModel.addNewRow( Arrays.asList("1",null,"3","4","5"));
            System.out.println("add 5 element of 4 total");
            excelModel.addNewRow( Arrays.asList(
                    new CellModel(
                            "This is red",
                            "RED",
                            "WHITE"
                    )
            ));

            excelModel.addNewRow(
                    Arrays.asList(
                            new CellModel("uno"),
                            2,
                            new CellModel("A","NIGGA", "BLUE"),
                            new CellModel("A","B")
                    )
            );


            ExcelUtil.writeExcel( excelModel.getFileName(), excelModel );
        }catch (Exception e){
            fail(e.toString());
        }
    }

    @Test
    public void test_cellChange(){
        try{
            CellModel cellModel = new CellModel("A");
            System.out.println( cellModel );
            cellModel = new CellModel("A","C","B");
            System.out.println( cellModel );
            cellModel = new CellModel("A","BLUE","B");
            System.out.println( cellModel );
            cellModel = new CellModel("A","BLACC","B");
            System.out.println( cellModel );
            cellModel = new CellModel("A","blackC","B");
            System.out.println( cellModel );
            cellModel = new CellModel("A", CellColorEnum.YELLOW.name(),"BLUE");
            System.out.println( cellModel );

        }catch (Exception e){
            fail(e.toString());
        }
    }

}
