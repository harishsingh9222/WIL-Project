package com.lambton.lofterapp.models.agents;

import java.io.Serializable;

public class AgentInfo implements Serializable {
    private String propName;
    private String propImgPath;
    private String propAddress;
    private String contact;
    private String propType;
    private double llat;
    private double llaong;
    private String propPrice;
    private String propSize;
    private double distance;

    public AgentInfo() {
    }

    public AgentInfo(String propName, String propImgPath, String propAddress,
                     String contact, String propType, double llat, double llaong,
                     String propPrice, String propSize) {
        this.propName = propName;
        this.propImgPath = propImgPath;
        this.propAddress = propAddress;
        this.contact = contact;
        this.propType = propType;
        this.llat = llat;
        this.llaong = llaong;
        this.propPrice = propPrice;
        this.propSize = propSize;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropImgPath() {
        return propImgPath;
    }

    public void setPropImgPath(String propImgPath) {
        this.propImgPath = propImgPath;
    }

    public String getPropAddress() {
        return propAddress;
    }

    public void setPropAddress(String propAddress) {
        this.propAddress = propAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public double getLlat() {
        return llat;
    }

    public void setLlat(double llat) {
        this.llat = llat;
    }

    public double getLlaong() {
        return llaong;
    }

    public void setLlaong(double llaong) {
        this.llaong = llaong;
    }

    public String getPropPrice() {
        return propPrice;
    }

    public void setPropPrice(String propPrice) {
        this.propPrice = propPrice;
    }

    public String getPropSize() {
        return propSize;
    }

    public void setPropSize(String propSize) {
        this.propSize = propSize;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}