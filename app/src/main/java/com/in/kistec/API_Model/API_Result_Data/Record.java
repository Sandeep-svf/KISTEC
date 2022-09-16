package com.in.kistec.API_Model.API_Result_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record  {

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
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("password")
@Expose
private String password;
@SerializedName("city_name")
@Expose
private Object cityName;
@SerializedName("device_token")
@Expose
private String deviceToken;
@SerializedName("auth_token")
@Expose
private String authToken;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("updated_at")
@Expose
private String updatedAt;
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

public void setEmail(String email) {
this.email = email;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public Object getCityName() {
return cityName;
}

public void setCityName(Object cityName) {
this.cityName = cityName;
}

public String getDeviceToken() {
return deviceToken;
}

public void setDeviceToken(String deviceToken) {
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

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

public Object getCreatedAt() {
return createdAt;
}

public void setCreatedAt(Object createdAt) {
this.createdAt = createdAt;
}

}