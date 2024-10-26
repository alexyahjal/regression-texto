package com.web.texto.controller;

import com.web.texto.model.HttpReqModel;
import com.web.texto.service.DBModelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("model")
public class DBModelController {

    Logger logger = LogManager.getLogger(DBModelController.class);

    @Autowired
    DBModelService dbModelService;

    @GetMapping("/suite-list")
    public HttpReqModel getAllSuite(@RequestBody HttpReqModel requestModel){
        logger.info("getAllSuite");
        return dbModelService.getSuiteList(requestModel);
    }

    /**
     * To init Suite from Excel
     * - context: {
     *     "file" : "filePath",
     *     "suite_name" : "Suite name"
     * }
        @param requestModel
     */
    @PostMapping("/suite-add-content")
    public HttpReqModel addSuiteContentFromFilet(@RequestBody HttpReqModel requestModel){
        logger.info("addSuiteContentFromFilet, " + requestModel);
        return dbModelService.addSuiteContentFromFile(requestModel);
    }

    /**
     * To init Suite from Excel
     * - context: {
     *     "file" : "filePath",
     *     "suite_name" : "Suite name"
     * }
     @param requestModel
     */
    @PostMapping("/suite-dump")
    public HttpReqModel suiteDumpToFile(@RequestBody HttpReqModel requestModel){
        logger.info("suiteDumpToFile, " + requestModel);
        return dbModelService.dumpSuiteToFile(requestModel);
    }

    /**
     * To init Suite from Excel
     * - context: {
     *     "suite_name" : "Suite name"
     * }
     @param requestModel
     */
    @GetMapping("/suite-content")
    public HttpReqModel getSuiteContent(@RequestBody HttpReqModel requestModel){
        logger.info("getSuiteContent, " + requestModel);
        return dbModelService.getSuiteContent(requestModel);
    }

    @PutMapping("/check-content")
    public HttpReqModel checkContent(){
        logger.info("checkContent");
        return dbModelService.checkContent();

    }

}
