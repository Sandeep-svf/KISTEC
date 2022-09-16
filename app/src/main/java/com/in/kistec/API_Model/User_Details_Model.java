package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.kistec.API_Model.API_Result_Data.User_Detials_Result;

public class User_Details_Model {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private User_Detials_Result userDetialsResult;

public String getSuccess() {
return String.valueOf(success);
}

public void setSuccess(Boolean success) {
this.success = success;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public User_Detials_Result getData() {
return userDetialsResult;
}

public void setData(User_Detials_Result userDetialsResult) {
this.userDetialsResult = userDetialsResult;
}

}