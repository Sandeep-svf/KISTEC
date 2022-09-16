package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.kistec.API_Model.API_Result_Data.Record_Details_Result;

public class Record_Details_Model {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("data")
@Expose
private Record_Details_Result recordDetailsResult;

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public Record_Details_Result getData() {
return recordDetailsResult;
}

public void setData(Record_Details_Result recordDetailsResult) {
this.recordDetailsResult = recordDetailsResult;
}

}