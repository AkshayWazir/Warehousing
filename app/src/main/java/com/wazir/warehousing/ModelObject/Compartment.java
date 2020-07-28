package com.wazir.warehousing.ModelObject;

import java.util.ArrayList;

public class Compartment {
    String compId, capacity;
    ArrayList<SensorObj> sensors;

    public Compartment() {
    }

    public Compartment(String compId, String capacity, ArrayList<SensorObj> sensors) {
        this.compId = compId;
        this.capacity = capacity;
        this.sensors = sensors;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public ArrayList<SensorObj> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<SensorObj> sensors) {
        this.sensors = sensors;
    }
}
