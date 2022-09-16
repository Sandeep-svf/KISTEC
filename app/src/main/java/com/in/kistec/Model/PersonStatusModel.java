package com.in.kistec.Model;

public class PersonStatusModel {
    private int personProfileStatus;
    private String personNameStatus, mobileNumberStatus, status;


    public int getPersonProfileStatus() {
        return personProfileStatus;
    }

    public void setPersonProfileStatus(int personProfileStatus) {
        this.personProfileStatus = personProfileStatus;
    }

    public String getPersonNameStatus() {
        return personNameStatus;
    }

    public void setPersonNameStatus(String personNameStatus) {
        this.personNameStatus = personNameStatus;
    }

    public String getMobileNumberStatus() {
        return mobileNumberStatus;
    }

    public void setMobileNumberStatus(String mobileNumberStatus) {
        this.mobileNumberStatus = mobileNumberStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
