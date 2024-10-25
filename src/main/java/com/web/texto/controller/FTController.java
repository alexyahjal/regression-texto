package com.web.texto.controller;

import com.web.texto.model.HttpReqModel;
import com.web.texto.service.FTService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ft")
public class FTController {

    Logger logger = LogManager.getLogger(FTController.class);

    @Autowired
    FTService fTService;

    @GetMapping("/suite-list")
    public HttpReqModel getAllSuite(){
        logger.info("getAllSuite");
        return fTService.getSuiteList();
    }

    /**
     * To init Suite from Excel
     * - context: {
     *     "file" : "filePath",
     *     "suite_name" : "Suite name"
     * }
        @param requestModel
     */
    @PostMapping("/suite-init")
    public HttpReqModel initSuite(@RequestBody HttpReqModel requestModel){
        logger.info("initSuite, " + requestModel);
        return fTService.initSuiteFromFile(requestModel);
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
        return fTService.dumpSuiteToFile(requestModel);
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
        return fTService.getSuiteContent(requestModel);
    }

}
