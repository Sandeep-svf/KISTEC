package com.in.kistec.API_Model.API_Result_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class All_Record_Result {

@SerializedName("profile")
@Expose
private String profile;
@SerializedName("name")
@Expose
private String name;
@SerializedName("mobile")
@Expose
private Long mobile;
@SerializedName("person_id")
@Expose
private String personId;
@SerializedName("about_person")
@Expose
private String aboutPerson;

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

public String getPersonId() {
return personId;
}

public void setPersonId(String personId) {
this.personId = personId;
}

public String getAboutPerson() {
return aboutPerson;
}

public void setAboutPerson(String aboutPerson) {
this.aboutPerson = aboutPerson;
}

}