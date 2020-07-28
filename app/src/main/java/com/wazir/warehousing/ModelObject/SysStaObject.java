package com.wazir.warehousing.ModelObject;

import java.util.ArrayList;

public class SysStaObject {
    String warehouseId, capacity;
    ArrayList<Compartment> compartments;

    public SysStaObject() {
    }

    public SysStaObject(String warehouseId, String capacity, ArrayList<Compartment> compartments) {
        this.warehouseId = warehouseId;
        this.capacity = capacity;
        this.compartments = compartments;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(ArrayList<Compartment> compartments) {
        this.compartments = compartments;
    }
}
