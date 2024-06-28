package com.zq;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EIcsv {

    @JsonFormat
    private long num;

    @JsonFormat
    private String work_name;

    @JsonFormat
    private String location;

    @JsonFormat
    private long salary;

    @JsonFormat
    private String work_year;

    @JsonFormat
    private String educate;

    @JsonFormat
    private String company_name;

    @JsonFormat
    private String company_type;

    @JsonFormat
    private String financing_conditions;

    @JsonFormat
    private String company_size;

    @JsonFormat
    private String city;

    @JsonFormat
    private long nums;

    public EIcsv() {
    }

    public EIcsv(long num, String work_name, String location, long salary, String work_year, String educate,
                 String company_name, String company_type, String financing_conditions, String company_size,
                 String city, long nums) {
        this.num = num;
        this.work_name = work_name;
        this.location = location;
        this.salary = salary;
        this.work_year = work_year;
        this.educate = educate;
        this.company_name = company_name;
        this.company_type = company_type;
        this.financing_conditions = financing_conditions;
        this.company_size = company_size;
        this.city = city;
        this.nums = nums;
    }
}

