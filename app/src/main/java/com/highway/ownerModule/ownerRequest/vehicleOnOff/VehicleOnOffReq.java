package com.highway.ownerModule.ownerRequest.vehicleOnOff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleOnOffReq {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("VehicleId")
    @Expose
    private String vehicleId;
    @SerializedName("VehicleOnOff")
    @Expose
    private String vehicleOnOff;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleOnOff() {
        return vehicleOnOff;
    }

    public void setVehicleOnOff(String vehicleOnOff) {
        this.vehicleOnOff = vehicleOnOff;
    }

}


