
package com.highway.ownerModule.ownerrrModel.cityResp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityDropdown {

    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("CityName")
    @Expose
    private String cityName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
