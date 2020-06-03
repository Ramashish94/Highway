
package com.highway.ownerModule.ownerrrModel.cityResp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResp {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("cityDropdown")
    @Expose
    private List<CityDropdown> cityDropdown = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CityDropdown> getCityDropdown() {
        return cityDropdown;
    }

    public void setCityDropdown(List<CityDropdown> cityDropdown) {
        this.cityDropdown = cityDropdown;
    }

}
