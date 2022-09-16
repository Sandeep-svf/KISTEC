package com.in.kistec.API_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in.kistec.API_Model.API_Result_Data.ContactUsResult;

public class ContactUsModel {

@SerializedName("success")
@Expose
private String success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("record")
@Expose
private ContactUsResult contactUsResult;

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

    public ContactUsResult getRecord2() {
        return contactUsResult;
    }

    public void setRecord2(ContactUsResult contactUsResult) {
        this.contactUsResult = contactUsResult;
    }
}