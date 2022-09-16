package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.kistec.API_Model.API_Result_Data.Status_List_Result;

import java.util.ArrayList;
import java.util.List;

public class Status_List_Model {

@SerializedName("success")
@Expose
private String success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Status_List_Result> data = new ArrayList<Status_List_Result>();

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Status_List_Result> getData() {
return data;
}

public void setData(List<Status_List_Result> data) {
this.data = data;
}

}