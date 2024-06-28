package com.zq.ei.publisherrealtime.service;

import com.zq.ei.publisherrealtime.bean.CompanyType;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyTypeService {

    public String getCompanyTypesAsJson() throws JsonProcessingException {
        List<CompanyType> companyTypes = new ArrayList<>();
        companyTypes.add(new CompanyType("外企", 23962));
        companyTypes.add(new CompanyType("私营", 21119));
        companyTypes.add(new CompanyType("中外合办", 19924));
        companyTypes.add(new CompanyType("国企", 19791));
        companyTypes.add(new CompanyType("其它", 17073 ));
        // 添加更多CompanyType对象...

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(companyTypes);
    }
}
