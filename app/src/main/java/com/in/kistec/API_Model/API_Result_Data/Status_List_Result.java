package com.in.kistec.API_Model.API_Result_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status_List_Result {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("profile")
@Expose
private String profile;
@SerializedName("mobile")
@Expose
private Long mobile;
@SerializedName("status")
@Expose
private Integer status;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getProfile() {
return profile;
}

public void setProfile(String profile) {
this.profile = profile;
}

public Long getMobile() {
return mobile;
}

public void setMobile(Long mobile) {
this.mobile = mobile;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

}