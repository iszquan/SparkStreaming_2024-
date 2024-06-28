package com.zq.ei.publisherrealtime.bean;

public class CompanyType {
    private String name;
    private int value;


    // 构造方法
    public CompanyType(String name, int value) {
        this.name = name;
        this.value = value;

    }

    // Getter和Setter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }



    // 内嵌类来表示itemStyle

}
