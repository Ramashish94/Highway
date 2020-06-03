
package com.highway.common.base.commonModel.otpverify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("driverVerifyBy")
    @Expose
    private String driverVerifyBy;
    @SerializedName("User_Id")
    @Expose
    private String userId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Role_Id")
    @Expose
    private String roleId;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("User_Status")
    @Expose
    private String userStatus;
    @SerializedName("Otp_Status")
    @Expose
    private String otpStatus;
    @SerializedName("isBoolean")
    @Expose
    private String isBoolean;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(String otpStatus) {
        this.otpStatus = otpStatus;
    }

    public String getIsBoolean() {
        return isBoolean;
    }

    public void setIsBoolean(String isBoolean) {
        this.isBoolean = isBoolean;
    }

    public String getDriverVerifyBy() {
        return driverVerifyBy;
    }

    public void setDriverVerifyBy(String driverVerifyBy) {
        this.driverVerifyBy = driverVerifyBy;
    }
}
