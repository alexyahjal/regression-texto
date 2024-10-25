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
public class FTModel implements Comparable {
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

    public FTModel() {
    }

    /**
     * Create a FunctionalTestModel from List String
     *
     * @param excelModelRowData
     * @param suiteName
     */
    public FTModel(
            List<String> excelModelRowData,
            String suiteName
    ) {

        this.comment = "";
        this.testName = "";
        this.reportedDate = "";

        switch (excelModelRowData.size()) {
            case 4:
                this.comment = excelModelRowData.get(3);
            case 3:
                this.reportedDate = excelModelRowData.get(2);
            case 2:
                this.testName = excelModelRowData.get(1);
        }
        this.testId = excelModelRowData.get(0).replace(".0", "");
        this.suiteName = suiteName;

    }

    /**
     * @return "No","Test","Reported","Comments"
     */
    public List<String> ftModelToListString() {
        List<String> ftModelAsArrayList = new ArrayList<>();
        ftModelAsArrayList.add(this.testId);
        ftModelAsArrayList.add(this.testName);
        ftModelAsArrayList.add(this.reportedDate);
        ftModelAsArrayList.add(this.comment);
        return ftModelAsArrayList;
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
