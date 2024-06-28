package com.zq.ei.publisherrealtime.controller;

import com.zq.ei.publisherrealtime.service.CompanyTypeService;
import com.zq.ei.publisherrealtime.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 控制层
 */
@RestController
public class PublisherController {

    @Autowired
    PublisherService publisherService;
    @Autowired
    private CompanyTypeService companyTypeService;

    // bar_horizontal接口
    @GetMapping("dauRealtime")
    public String dauRealtime(){
        String results = publisherService.doDauRealtime();

        return results;

    }

    // bar_markline接口
    @GetMapping("dauRealtime2")
    public String getCompanyTypes() throws Exception {
        return companyTypeService.getCompanyTypesAsJson();
    }

    // bar_ROA接口
    @GetMapping("dauRealtime3")
    public String dauRealtime3(){
        String results = publisherService.doDauRealtime3();

        return results;

    }

}
