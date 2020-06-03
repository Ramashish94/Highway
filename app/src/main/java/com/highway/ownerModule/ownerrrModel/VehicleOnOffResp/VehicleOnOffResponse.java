package com.highway.ownerModule.ownerrrModel.VehicleOnOffResp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleOnOffResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("vechicle_on_off")
    @Expose
    private String vechicleOnOff;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVechicleOnOff() {
        return vechicleOnOff;
    }

    public void setVechicleOnOff(String vechicleOnOff) {
        this.vechicleOnOff = vechicleOnOff;
    }

}
