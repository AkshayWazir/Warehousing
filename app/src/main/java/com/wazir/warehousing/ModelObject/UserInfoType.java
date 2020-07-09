package com.wazir.warehousing.ModelObject;

public class UserInfoType {
    String userId, userType, userToken;

    public UserInfoType() {
        userId = "";
        userType = "";
        userToken = "";
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
