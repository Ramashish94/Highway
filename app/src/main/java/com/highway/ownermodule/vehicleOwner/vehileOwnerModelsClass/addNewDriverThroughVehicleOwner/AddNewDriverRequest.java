package com.highway.ownermodule.vehicleOwner.vehileOwnerModelsClass.addNewDriverThroughVehicleOwner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewDriverRequest {

@SerializedName("owner_id")
@Expose
private String ownerId;
@SerializedName("driverName")
@Expose
private String driverName;
@SerializedName("driverMobile")
@Expose
private String driverMobile;
@SerializedName("driverEmail")
@Expose
private String driverEmail;
@SerializedName("driverDLNo")
@Expose
private String driverDLNo;
@SerializedName("driverAddress")
@Expose
private String driverAddress;
@SerializedName("dlexpiryDate")
@Expose
private String dlexpiryDate;
@SerializedName("vehicleId")
@Expose
private String vehicleId;

public String getOwnerId() {
return ownerId;
}

public void setOwnerId(String ownerId) {
this.ownerId = ownerId;
}

public String getDriverName() {
return driverName;
}

public void setDriverName(String driverName) {
this.driverName = driverName;
}

public String getDriverMobile() {
return driverMobile;
}

public void setDriverMobile(String driverMobile) {
this.driverMobile = driverMobile;
}

public String getDriverEmail() {
return driverEmail;
}

public void setDriverEmail(String driverEmail) {
this.driverEmail = driverEmail;
}

public String getDriverDLNo() {
return driverDLNo;
}

public void setDriverDLNo(String driverDLNo) {
this.driverDLNo = driverDLNo;
}

public String getDriverAddress() {
return driverAddress;
}

public void setDriverAddress(String driverAddress) {
this.driverAddress = driverAddress;
}

public String getDlexpiryDate() {
return dlexpiryDate;
}

public void setDlexpiryDate(String dlexpiryDate) {
this.dlexpiryDate = dlexpiryDate;
}

public String getVehicleId() {
return vehicleId;
}

public void setVehicleId(String vehicleId) {
this.vehicleId = vehicleId;
}

}