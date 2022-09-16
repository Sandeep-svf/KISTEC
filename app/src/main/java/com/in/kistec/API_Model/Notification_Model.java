package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.kistec.API_Model.API_Result_Data.Notification_Result;

import java.util.ArrayList;
import java.util.List;

public class Notification_Model {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Notification_Result> data = new ArrayList<Notification_Result>();

public Boolean getSuccess() {
return success;
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

public List<Notification_Result> getData() {
return data;
}

public void setData(List<Notification_Result> data) {
this.data = data;
}

}