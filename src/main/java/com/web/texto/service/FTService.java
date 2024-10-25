package com.web.texto.service;

import com.web.texto.model.ExcelModel;
import com.web.texto.model.repo.FTModel;
import com.web.texto.model.HttpReqModel;
import com.web.texto.repository.FTRepository;
import com.web.texto.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.web.texto.util.Const.EXCEL_HEADER_FT;

@Service
public class FTService {

    Logger logger = LogManager.getLogger(FTService.class);

    @Autowired
    FTRepository fTRepository;


    /**
     *
     */
    public HttpReqModel getSuiteList() {
        logger.info("getSuiteList");
        HttpReqModel requestModel = new HttpReqModel();
        try {
            requestModel.setResult(fTRepository.findAllSuiteName());
        } catch (Exception e) {
            requestModel.updateError(e.getMessage());
        }
        return requestModel;
    }

    /**
     * @param requestModel . context . suite_name = suite name
     * @return
     */
    public HttpReqModel getSuiteContent(HttpReqModel requestModel) {
        logger.info("getSuiteContent, " + requestModel);

        List<FTModel> suiteContent = fTRepository.findAllBySuiteName(
                requestModel.getContextValue("suite_name")
        );
        Collections.sort(suiteContent);

        try {
            requestModel.setResult(suiteContent);
        } catch (Exception e) {
            requestModel.updateError(e.toString());
        }
        return requestModel;
    }

    /**
     * To init Suite from Excel<br>
     * context: {<br>
     * "file" : "filePath",<br>
     * "suite_name" : "Suite name"<br>
     * }<br>
     *
     * @param requestModel
     */
    public HttpReqModel initSuiteFromFile(HttpReqModel requestModel) {
        logger.info("initSuiteFromFile, " + requestModel);
        try {
            ExcelModel excelModel = FileUtil.fileReadExcel(requestModel.getContextValue("file"));
            String suiteName = requestModel.getContextValue("suite_name");
            boolean isFirst = true;
            List<FTModel> newList = new ArrayList<>();
            List<FTModel> toDeleteList = this.fTRepository.findAllBySuiteName(suiteName);
            for (List<String> row : excelModel.getFirstSheet()) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                newList.add(new FTModel(row, suiteName));
            }
            //delete
            toDeleteList.forEach(
                    this.fTRepository::delete
            );
            //new
            newList.forEach(
                    this.fTRepository::save
            );
            requestModel.setResult(
                    newList
            );

        } catch (Exception e) {
            requestModel.updateError(e.getMessage());
        }
        return requestModel;
    }

    /**
     * context{ <br>
     * "file" : filepath<br>
     * "suite_name" : suite name<br>
     * }<br>
     *
     * @param reqModel
     * @return
     */
    public HttpReqModel dumpSuiteToFile(HttpReqModel reqModel) {
        logger.info("dumpSuiteToFile, " + reqModel);
        try {
            ExcelModel excelModel = new ExcelModel(true);
            excelModel.getFirstSheet().add(EXCEL_HEADER_FT);
            List<FTModel> ftModelList = fTRepository.findAllBySuiteName(reqModel.getContextValue("suite_name"));
            Collections.sort(ftModelList);
            ftModelList.forEach(ft -> {
                excelModel.getFirstSheet().add(ft.ftModelToListString());
            });
            FileUtil.fileSaveToExcel(excelModel, reqModel.getContextValue("file"));
            reqModel.setResult(
                    "Suite : " + reqModel.getContextValue("suite_name") + "\n" +
                            reqModel.getContextValue("file") + " -> CREATED!"
            );
        } catch (Exception e) {
            reqModel.updateError(e.toString());
        }
        return reqModel;
    }


}
