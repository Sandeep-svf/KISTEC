package com.in.kistec.API_Model.API_Result_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class All_Search_List_Model_Response {
    @SerializedName("record_id")
    @Expose
    private Integer recordId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("person_id")
    @Expose
    private String personId;
    @SerializedName("person_name")
    @Expose
    private String personName;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("national_id")
    @Expose
    private String nationalId;
    @SerializedName("city_name")
    @Expose
    private Object cityName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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

    public Object getCityName() {
        return cityName;
    }

    public void setCityName(Object cityName) {
        this.cityName = cityName;
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
}