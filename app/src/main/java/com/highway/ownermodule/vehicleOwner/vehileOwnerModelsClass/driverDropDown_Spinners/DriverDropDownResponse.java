
package com.highway.ownermodule.vehicleOwner.vehileOwnerModelsClass.driverDropDown_Spinners;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverDropDownResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private DataModel data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

}
