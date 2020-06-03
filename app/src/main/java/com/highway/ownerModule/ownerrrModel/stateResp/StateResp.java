
package com.highway.ownerModule.ownerrrModel.stateResp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateResp {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("stateDropdown")
    @Expose
    private List<StateDropdown> stateDropdown = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<StateDropdown> getStateDropdown() {
        return stateDropdown;
    }

    public void setStateDropdown(List<StateDropdown> stateDropdown) {
        this.stateDropdown = stateDropdown;
    }

}
