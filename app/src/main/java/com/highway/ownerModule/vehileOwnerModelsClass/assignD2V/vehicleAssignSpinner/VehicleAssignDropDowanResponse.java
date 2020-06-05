
package com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleAssignDropDowanResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("vehicleData")
    @Expose
    private VehicleData vehicleData= new VehicleData();

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public VehicleData getVehicleData() {
        return vehicleData;
    }

    public void setVehicleData(VehicleData vehicleData) {
        this.vehicleData = vehicleData;
    }

}
