
package com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataVehicle {

    @SerializedName("vehicle_details")
    @Expose
    private ArrayList<VehicleDetail> vehicleDetails = null;

    public ArrayList<VehicleDetail> getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(ArrayList<VehicleDetail> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

}
