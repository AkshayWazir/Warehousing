package com.wazir.warehousing.ModelObject;

public class SysStaObject {
    private String sensName, location,info;
    boolean sensValue;

    public SysStaObject() {
    }

    public SysStaObject(String sensName, boolean sensValue) {
        this.sensName = sensName;
        this.sensValue = sensValue;
    }

    public void setSensName(String sensName) {
        this.sensName = sensName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setSensValue(boolean sensValue) {
        this.sensValue = sensValue;
    }

    public String getSensName() {
        return sensName;
    }

    public boolean isSensValue() {
        return sensValue;
    }
}
