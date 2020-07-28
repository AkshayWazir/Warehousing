package com.wazir.warehousing.ModelObject;

public class SensorObj {
    private String sensorId, info, loc;
    private boolean status;

    public SensorObj() {
    }

    public SensorObj(String sensorId, String info, String loc, boolean status) {
        this.sensorId = sensorId;
        this.info = info;
        this.loc = loc;
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
