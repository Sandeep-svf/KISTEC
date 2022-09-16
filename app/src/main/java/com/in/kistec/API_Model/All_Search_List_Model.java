package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.kistec.API_Model.API_Result_Data.All_Search_List_Model_Response;

import java.util.ArrayList;
import java.util.List;

public class All_Search_List_Model {

@SerializedName("success")
@Expose
private String success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<All_Search_List_Model_Response> data = new ArrayList<All_Search_List_Model_Response>();

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

public List<All_Search_List_Model_Response> getData() {
return data;
}

public void setData(List<All_Search_List_Model_Response> data) {
this.data = data;
}

}