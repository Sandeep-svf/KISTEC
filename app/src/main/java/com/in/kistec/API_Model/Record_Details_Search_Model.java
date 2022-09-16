package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record_Details_Search_Model {

@SerializedName("success")
@Expose
private String success;
@SerializedName("message")
@Expose
private String message;

@SerializedName("data")
@Expose
private Data data;

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

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}