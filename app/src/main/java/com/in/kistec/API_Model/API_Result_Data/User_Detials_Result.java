package com.in.kistec.API_Model.API_Result_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_Detials_Result {



@SerializedName("id")
@Expose
private Integer id;
@SerializedName("profile")
@Expose
private String profile;
@SerializedName("national_id")
@Expose
private String nationalId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("email")
@Expose
private String email;
    @SerializedName("city_name")
    @Expose
    private String cityName;

@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("enable_password")
@Expose
private String enablePassword;
@SerializedName("device_token")
@Expose
private Object deviceToken;
@SerializedName("auth_token")
@Expose
private String authToken;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("updated_at")
@Expose
private Object updatedAt;
@SerializedName("created_at")
@Expose
private Object createdAt;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getProfile() {
return profile;
}

public void setProfile(String profile) {
this.profile = profile;
}

public String getNationalId() {
return nationalId;
}

public void setNationalId(String nationalId) {
this.nationalId = nationalId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmail() {
return email;
}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setEmail(String email) {
this.email = email;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getEnablePassword() {
return enablePassword;
}

public void setEnablePassword(String enablePassword) {
this.enablePassword = enablePassword;
}

public Object getDeviceToken() {
return deviceToken;
}

public void setDeviceToken(Object deviceToken) {
this.deviceToken = deviceToken;
}

public String getAuthToken() {
return authToken;
}

public void setAuthToken(String authToken) {
this.authToken = authToken;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Object getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(Object updatedAt) {
this.updatedAt = updatedAt;
}

public Object getCreatedAt() {
return createdAt;
}

public void setCreatedAt(Object createdAt) {
this.createdAt = createdAt;
}

}