package com.web.texto.model.repo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "functional_test")
@TableGenerator(
        name = "functional_testTableGenerator",
        allocationSize = 1,
        initialValue = 1
)
public class FTModel implements DBModel{
    @Id
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "functional_testTableGenerator")
    private Integer id;

    @Column(length = 4)
    private String testId;

    @Column(length = 500)
    private String suiteName;

    @Column(length = 500)
    private String testName;

    private String reportedDate;

    @Column(length = 2500)
    private String comment;


    public FTModel(){}

    public FTModel(List<String> excelData, String suiteName){
        setModelFromListString(excelData,suiteName);
    }

    /**
     * @return "No","Test","Reported","Comments"
     */
    @Override
    public List<String> modelToListString(){
        List<String> ftModelAsArrayList = new ArrayList<>();
        ftModelAsArrayList.add(this.testId);
        ftModelAsArrayList.add(this.testName);
        ftModelAsArrayList.add(this.reportedDate);
        ftModelAsArrayList.add(this.comment);
        return ftModelAsArrayList;
    }

    @Override
    public void setModelFromListString(List<String> excelData, String suiteName) {
        this.testId = excelData.get(0);
        this.testName = excelData.get(1);
        this.reportedDate = excelData.get(2);
        this.comment = excelData.get(3);
        //
        this.suiteName = suiteName;
    }

    @Override
    public boolean isModelEmpty() {
        return this.testId.isEmpty()
                &&this.testName.isEmpty();
    }

    @Override
    public int compareTo(Object o) {
        try {
            return Integer.parseInt(this.testId) - Integer.parseInt(((FTModel) o).getTestId());
        } catch (Exception e) {
            return 0;
        }
    }
}

