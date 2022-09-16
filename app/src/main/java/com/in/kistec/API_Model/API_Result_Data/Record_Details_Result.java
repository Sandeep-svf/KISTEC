package com.in.kistec.API_Model.API_Result_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record_Details_Result {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("profile")
@Expose
private String profile;
@SerializedName("name")
@Expose
private String name;
@SerializedName("mobile")
@Expose
private Long mobile;
@SerializedName("image")
@Expose
private String image;
@SerializedName("person_id")
@Expose
private String personId;
@SerializedName("person_type")
@Expose
private Integer personType;
@SerializedName("about_person")
@Expose
private String aboutPerson;
@SerializedName("updated_at")
@Expose
private Object updatedAt;
@SerializedName("created_at")
@Expose
private Object createdAt;
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

public String getProfile() {
return profile;
}

public void setProfile(String profile) {
this.profile = profile;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Long getMobile() {
return mobile;
}

public void setMobile(Long mobile) {
this.mobile = mobile;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getPersonId() {
return personId;
}

public void setPersonId(String personId) {
this.personId = personId;
}

public Integer getPersonType() {
return personType;
}

public void setPersonType(Integer personType) {
this.personType = personType;
}

public String getAboutPerson() {
return aboutPerson;
}

public void setAboutPerson(String aboutPerson) {
this.aboutPerson = aboutPerson;
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

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

}