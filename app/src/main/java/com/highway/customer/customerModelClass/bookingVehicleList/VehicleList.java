
package com.highway.customer.customerModelClass.bookingVehicleList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleList {

    @SerializedName("VehicleId")
    @Expose
    private String vehicleId;

    @SerializedName("TimeDuration")
    @Expose
    private String TimeDuration;


    @SerializedName("VehicleTypeId")
    @Expose
    private String vehicleTypeId;
    @SerializedName("VehicleName")
    @Expose
    private String vehicleName;

    @SerializedName("VehicleType")
    @Expose
    private String vehicleType;

    @SerializedName("VehicleFare")
    @Expose
    private String vehicleFare;
    @SerializedName("v_info")
    @Expose
    private VInfo vInfo;
    private boolean selected;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleFare() {
        return vehicleFare;
    }

    public String getTimeDuration() {
        return TimeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        TimeDuration = timeDuration;
    }

    public void setVehicleFare(String vehicleFare) {
        this.vehicleFare = vehicleFare;
    }

    public VInfo getVInfo() {
        return vInfo;
    }

    public void setVInfo(VInfo vInfo) {
        this.vInfo = vInfo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
