
package com.highway.ownerModule.ownerrrModel.stateResp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateDropdown {

    @SerializedName("StateId")
    @Expose
    private String stateId;
    @SerializedName("StateName")
    @Expose
    private String stateName;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}
