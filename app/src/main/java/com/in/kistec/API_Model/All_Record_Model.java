package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.kistec.API_Model.API_Result_Data.All_Record_Result;

import java.util.ArrayList;
import java.util.List;

public class All_Record_Model {

@SerializedName("success")
@Expose
private String success;

    @SerializedName("message")
    @Expose
    private String message;

@SerializedName("data")
@Expose
private List<All_Record_Result> data = new ArrayList<All_Record_Result>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public List<All_Record_Result> getData() {
return data;
}

public void setData(List<All_Record_Result> data) {
this.data = data;
}


}