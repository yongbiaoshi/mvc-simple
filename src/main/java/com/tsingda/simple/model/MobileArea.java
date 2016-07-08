package com.tsingda.simple.model;

public class MobileArea {
    private Integer id;

    private String mobile;

    private String sheng;

    private String shi;

    private String yunyingshang;

    private String code1;

    private String code2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng == null ? null : sheng.trim();
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi == null ? null : shi.trim();
    }

    public String getYunyingshang() {
        return yunyingshang;
    }

    public void setYunyingshang(String yunyingshang) {
        this.yunyingshang = yunyingshang == null ? null : yunyingshang.trim();
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1 == null ? null : code1.trim();
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2 == null ? null : code2.trim();
    }
}