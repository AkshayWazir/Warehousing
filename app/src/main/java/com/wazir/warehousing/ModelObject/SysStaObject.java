package com.wazir.warehousing.ModelObject;

public class SysStaObject {
    String sensName;
    boolean sensValue;

    public SysStaObject() {
    }

    public SysStaObject(String sensName, boolean sensValue) {
        this.sensName = sensName;
        this.sensValue = sensValue;
    }

    public String getSensName() {
        return sensName;
    }

    public boolean isSensValue() {
        return sensValue;
    }
}
