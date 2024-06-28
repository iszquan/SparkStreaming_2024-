package com.zq.ei.publisherrealtime.service.impl;

import com.zq.ei.publisherrealtime.mapper.PublisherMapper;
import com.zq.ei.publisherrealtime.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 业务层
 */
@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    PublisherMapper publisherMapper;

    @Override
    public String doDauRealtime() {
        //业务处理
        String dauResults = publisherMapper.searchDau();

        return dauResults;
    }


    @Override
    public String doDauRealtime3() {
        //业务处理
        String dauResults = publisherMapper.searchDau3();

        return dauResults;
    }


}
