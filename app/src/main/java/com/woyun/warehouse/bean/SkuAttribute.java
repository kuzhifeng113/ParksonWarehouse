package com.woyun.warehouse.bean;


import java.io.Serializable;

/**
 * SpecMap
 */
public class SkuAttribute implements Serializable {

    /**
     * specName : 颜色
     * specValue : 红色
     */
    private String specName;
    private String specValue;

    public SkuAttribute() {
    }

    public SkuAttribute(String specName, String specValue) {
        this.specName = specName;
        this.specValue = specValue;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }
}
