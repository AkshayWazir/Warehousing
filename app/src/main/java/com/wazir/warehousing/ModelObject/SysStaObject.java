package com.wazir.warehousing.ModelObject;

public class SysStaObject {
    private String sensorId, compartId, location, info;
    boolean sensValue;

    public SysStaObject() {
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getCompartId() {
        return compartId;
    }

    public void setCompartId(String compartId) {
        this.compartId = compartId;
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


    public boolean isSensValue() {
        return sensValue;
    }
}
