package com.web.texto.service;

import com.web.texto.model.ExcelModel;
import com.web.texto.model.repo.CALModel;
import com.web.texto.model.repo.FTModel;
import com.web.texto.model.HttpReqModel;
import com.web.texto.repository.CALRepository;
import com.web.texto.repository.FTRepository;
import com.web.texto.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.web.texto.util.Const.EXCEL_HEADER_CAL;
import static com.web.texto.util.Const.EXCEL_HEADER_FT;

@Service
public class DBModelService {

    Logger logger = LogManager.getLogger(DBModelService.class);

    @Autowired
    FTRepository fTRepository;
    @Autowired
    CALRepository calRepository;


    /**
     * @param requestModel context . model : CAL or FT
     */
    public HttpReqModel getSuiteList(HttpReqModel requestModel) {
        String model = requestModel.getContextValue("model");
        logger.info("getSuiteList");
        try {
            if( model.equalsIgnoreCase("ft") ) requestModel.setResult(fTRepository.findAllSuiteName());
            if( model.equalsIgnoreCase("cal") ) requestModel.setResult(calRepository.findAllSuiteName());
        } catch (Exception e) {
            requestModel.updateError(e.getMessage());
        }
        return requestModel;
    }

    /**
     * @param requestModel . context . suite_name = suite name
     *                     . context . model = FT or CAL
     * @return
     */
    public HttpReqModel getSuiteContent(HttpReqModel requestModel) {
        String model = requestModel.getContextValue("model");
        logger.info("getSuiteContent, " + requestModel);
        try {
            List suiteContent = new ArrayList<>();
            if (model.equalsIgnoreCase("ft")) suiteContent = fTRepository.findAllBySuiteName(requestModel.getContextValue("suite_name"));
            if (model.equalsIgnoreCase("cal")) suiteContent = calRepository.findAllBySuiteName(requestModel.getContextValue("suite_name"));
            Collections.sort(suiteContent);
            requestModel.setResult(suiteContent);
        } catch (Exception e) {
            requestModel.updateError(e.toString());
        }
        return requestModel;
    }

    /**
     * To init Suite from Excel<br>
     * @param requestModel . context . file : "filePath",<br>
     *                     . context . suite_name : "Suite name"<br>
     *                     . context . model : FT or CAL
     */
    public HttpReqModel addSuiteContentFromFile(HttpReqModel requestModel) {
        logger.info("addSuiteContentFromFile, " + requestModel);
        try {
            ExcelModel excelModel = FileUtil.fileReadExcel(requestModel.getContextValue("file"));
            String suiteName = requestModel.getContextValue("suite_name");
            String model = requestModel.getContextValue("model");
            boolean isFirst = true;
            List newList = new ArrayList<>();
            for (List<String> row : excelModel.getFirstSheet()) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                if(model.equalsIgnoreCase("ft")) newList.add(new FTModel(row, suiteName));
                if(model.equalsIgnoreCase("cal")) newList.add(new CALModel(row, suiteName));
            }
            if(model.equalsIgnoreCase("ft")) this.fTRepository.saveAll( newList );
            if(model.equalsIgnoreCase("cal")) this.calRepository.saveAll( newList );
            requestModel.setResult(
                    newList
            );
        } catch (Exception e) {
            requestModel.updateError(e.getMessage());
        }
        return requestModel;
    }

    /**
     * To dump to Excel file
     * @param reqModel . context . file : "filePath",<br>
     *                     . context . suite_name : "Suite name"<br>
     *                     . context . model : FT or CAL
     * @return
     */
    public HttpReqModel dumpSuiteToFile(HttpReqModel reqModel) {
        logger.info("dumpSuiteToFile, " + reqModel);
        String model = reqModel.getContextValue("model");
        try {
            ExcelModel excelModel = new ExcelModel(true);
            List modelList = new ArrayList<>();

            if(model.equalsIgnoreCase("ft")){
                excelModel.getFirstSheet().add(EXCEL_HEADER_FT);
                modelList = fTRepository.findAllBySuiteName(reqModel.getContextValue("suite_name"));
            }
            if(model.equalsIgnoreCase("cal")){
                excelModel.getFirstSheet().add(EXCEL_HEADER_CAL);
                modelList = calRepository.findAllBySuiteName(reqModel.getContextValue("suite_name"));
            }
            Collections.sort(modelList);
            modelList.forEach( dbModel -> {
                if( model.equalsIgnoreCase("ft")) excelModel.getFirstSheet().add(((FTModel) dbModel).modelToListString());
                if( model.equalsIgnoreCase("cal")) excelModel.getFirstSheet().add(((CALModel) dbModel).modelToListString());
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

    public HttpReqModel checkContent(){
        HttpReqModel reqModel = new HttpReqModel();
        try{
            List<CALModel> calDeletedModel = new ArrayList<>();
            List<FTModel> ftDeletedModel = new ArrayList<>();
            calRepository.findAll().forEach( cal -> { if( cal.isModelEmpty() ){ calRepository.delete( cal ); calDeletedModel.add( cal ); }} );
            fTRepository.findAll().forEach( ft -> {if(ft.isModelEmpty()){ fTRepository.delete( ft ); ftDeletedModel.add( ft );}});
            reqModel.setResult(
                    new HashMap<String,List>(){{
                        put("CAL", calDeletedModel );
                        put("FT" , ftDeletedModel );
                    }}
            );
        }catch (Exception e){
            reqModel.updateError( e.toString() );
        }
        return reqModel;
    }

}
