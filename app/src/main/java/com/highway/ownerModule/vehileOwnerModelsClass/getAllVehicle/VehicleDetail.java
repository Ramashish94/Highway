
package com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleDetail  {

    @SerializedName("VehicleName")
    @Expose
    private String vehicleName;

    @SerializedName("VehicleId")
    @Expose
    private String VehicleId;



    @SerializedName("VehicleOnOff")
    @Expose
    private String VehicleOnOff;

    @SerializedName("VehicleImage")
    @Expose
    private String VehicleImage;




    @SerializedName("VehicleNumber")
    @Expose
    private String vehicleNumber;
    @SerializedName("VehicleModelNo")
    @Expose
    private String vehicleModelNo;
    @SerializedName("VehicleDescription")
    @Expose
    private String vehicleDescription;
    @SerializedName("VehicleCapacity")
    @Expose
    private String vehicleCapacity;
    @SerializedName("VehicleSize")
    @Expose
    private String vehicleSize;
    @SerializedName("DriverId")
    @Expose
    private Object driverId;
    @SerializedName("DriverName")
    @Expose
    private String driverName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("DLNumber")
    @Expose
    private Object dLNumber;
    @SerializedName("ExpiryDate")
    @Expose
    private Object expiryDate;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleModelNo() {
        return vehicleModelNo;
    }

    public void setVehicleModelNo(String vehicleModelNo) {
        this.vehicleModelNo = vehicleModelNo;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

    public String getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(String vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    public Object getDriverId() {
        return driverId;
    }

    public void setDriverId(Object driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getDLNumber() {
        return dLNumber;
    }

    public void setDLNumber(Object dLNumber) {
        this.dLNumber = dLNumber;
    }

    public Object getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Object expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getVehicleOnOff() {
        return VehicleOnOff;
    }

    public void setVehicleOnOff(String vehicleOnOff) {
        VehicleOnOff = vehicleOnOff;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVehicleImage() {
        return VehicleImage;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public void setVehicleImage(String vehicleImage) {
        VehicleImage = vehicleImage;
    }
}
