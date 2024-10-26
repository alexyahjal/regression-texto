package com.web.texto.model.repo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cal_log")
@TableGenerator(
        name = "cal_logTableGenerator",
        allocationSize = 1,
        initialValue = 1
)
public class CALModel implements DBModel{
    @Id
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "cal_logTableGenerator")
    private Integer id;

    private String type;

    @Column(length = 500)
    private String calName;

    private String reportedDate;

    @Column(length = 2500)
    private String comment;

    private String suiteName;


    public CALModel(){}

    public CALModel(List<String> excelData, String suiteName){
        setModelFromListString(excelData,suiteName);
    }

    /**
     * @return "type","calName","Reported","Comments"
     */
    public List<String> modelToListString() {
        List<String> ftModelAsArrayList = new ArrayList<>();
        ftModelAsArrayList.add(this.type);
        ftModelAsArrayList.add(this.calName);
        ftModelAsArrayList.add(this.reportedDate);
        ftModelAsArrayList.add(this.comment);
        return ftModelAsArrayList;
    }

    @Override
    public void setModelFromListString(List<String> excelData, String suiteName) {
        this.type = excelData.get(0);
        this.calName = excelData.get(1);
        this.reportedDate = excelData.get(2);
        this.comment = excelData.get(3);
        //
        this.suiteName = suiteName;
    }

    @Override
    public boolean isModelEmpty() {
        return this.type.isEmpty()
                && this.calName.isEmpty();
    }

    @Override
    public int compareTo(Object o) {
        try {
            return (this.type.equalsIgnoreCase("errors")?1:0) - ( (( (CALModel) o ).getType().equalsIgnoreCase("errors"))?1:0 );
        } catch (Exception e) {
            return 0;
        }
    }
}

