package com.highway.commonretrofit;

import com.highway.common.base.commonModel.bookingHTrip.BookingHTripRequest;
import com.highway.common.base.commonModel.bookingHTrip.BookingHTripResponse;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userRating.UpdateTripRatingByUserReq;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userRating.UpdateTripRatingByUserResp;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdRequest;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdResponse;
import com.highway.common.base.commonModel.login.LoginReqUpdated;
import com.highway.common.base.commonModel.otpverify.VerifyOtpRequest;
import com.highway.common.base.commonModel.otpverify.VerifyOtpResponse;
import com.highway.common.base.commonModel.registration.RegistrationReqUpdated;
import com.highway.common.base.commonModel.registration.RegistrationRespUpdated;
import com.highway.customer.RegisterForPushModel;
import com.highway.customer.customerModelClass.bookingVehicleList.BookingVehicleListRequest;
import com.highway.customer.customerModelClass.bookingVehicleList.BookingVehicleListResponse;
import com.highway.customer.customerModelClass.cancleTripModel.cancleReason.CancelTripReasonRequest;
import com.highway.customer.customerModelClass.cancleTripModel.cancleReason.CancelTripReasonResponse;
import com.highway.customer.customerModelClass.cancleTripModel.cancleTrip.CancelTripByCustomerRequest;
import com.highway.customer.customerModelClass.cancleTripModel.cancleTrip.CancelTripByCustomerResponse;
import com.highway.customer.customerModelClass.customerCurrentTripStatus.GetCustomerCurrentTripStatusReq;
import com.highway.customer.customerModelClass.customerInvoice.CustomerInvoiceReq;
import com.highway.customer.customerModelClass.customerInvoice.CustomerInvoiceResp;
import com.highway.drivermodule.driverModelClass.driverInvoice.DriverInvoiceReq;
import com.highway.drivermodule.driverModelClass.driverInvoice.DriverInvoiceResp;
import com.highway.customer.customerModelClass.driverLocation.NearByDriverLocationResponse;
import com.highway.customer.customerModelClass.selectYoursGoodsType.GoodsTypeDataRequest;
import com.highway.customer.customerModelClass.selectYoursGoodsType.GoodsTypeDataResponse;
import com.highway.customer.customerModelClass.updateReceiverModel.UpdateReceiverPhoneNoAndNameRequest;
import com.highway.customer.customerModelClass.updateReceiverModel.UpdateReceiverPhoneNoAndNameResponse;
import com.highway.drivermodule.driverModelClass.BookingAcceptRejectData;
import com.highway.drivermodule.driverModelClass.BookingAcceptRejectResponse;
import com.highway.customer.customerModelClass.vehicleInfo.BookingVehicleInfoRequest;
import com.highway.customer.customerModelClass.vehicleInfo.BookingVehicleInfoResponse;
import com.highway.drivermodule.driverModelClass.DriverResponse;
import com.highway.drivermodule.driverModelClass.DriverStartTripRequest;
import com.highway.drivermodule.driverModelClass.VehicleCurrentLocation;
import com.highway.drivermodule.driverModelClass.update_driver_location.UpdateDriverLocationReqst;
import com.highway.drivermodule.driverModelClass.update_driver_location.UpdateDriverLocationResp;
import com.highway.drivermodule.drivermodels.DriverDetailRequest;
import com.highway.drivermodule.drivermodels.DriverDetails;
import com.highway.drivermodule.updateTripStatusByDriver.UpdateTripStatusByDriverReq;
import com.highway.drivermodule.updateTripStatusByDriver.UpdateTripStatusByDriverResp;
import com.highway.millUserModule.SpinnerModelForMiller.ApproxLoad.ApproxLoadDropDownRequest;
import com.highway.millUserModule.SpinnerModelForMiller.ApproxLoad.ApproxLoadDropDownResponse;
import com.highway.millUserModule.SpinnerModelForMiller.GoodsTypes.GoodsTypeDropDownRequest;
import com.highway.millUserModule.SpinnerModelForMiller.GoodsTypes.GoodsTypesDropDownResponse;
import com.highway.ownerModule.ownerRequest.vehicleOnOff.VehicleOnOffReq;
import com.highway.ownerModule.ownerrrModel.VehicleOnOffResp.VehicleOnOffResponse;
import com.highway.ownerModule.ownerrrModel.cityResp.CityResp;
import com.highway.ownerModule.ownerrrModel.stateResp.StateResp;
import com.highway.ownerModule.vehileOwnerModelsClass.addNewDriverThroughVehicleOwner.AddNewDriverRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.addNewDriverThroughVehicleOwner.AddNewDriverResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.addVehicle.AddVehicleRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.addVehicle.AddVehicleResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.assignD2V_Model.AssignD2VRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.assignD2V_Model.AssignD2VResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DriverDropDownRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DriverDropDownResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleAssignDropDowanRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleAssignDropDowanResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.GetAllDriverRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.GetAllDriverResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.GetAllVehicleRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.GetAllVehicleResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.vehiceLoadCapicity.VehicleLoadCapicityRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.vehiceLoadCapicity.VehicleLoadCapicityResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleDimensionSize.VehicleDiamensionSizeRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleDimensionSize.VehicleDiamensionSizeResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleTypeDropDowan.VehicleTypeDropDowanRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleTypeDropDowan.VehicleTypeDropDowanResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    // mobile logon
    @POST("api/Login/login_register")
    Call<ResponseBody> loginResponseCall(@Body LoginReqUpdated loginReqUpdated);

    // otp verification
    @POST("api/Login/otp_verify")
    Call<VerifyOtpResponse> verifyOtpResponseCall(@Body VerifyOtpRequest verifyOtpRequest);

    // Registration Details
    @POST("api/Login/signup")
    Call<RegistrationRespUpdated> regDetailsResponseCall(@Body RegistrationReqUpdated registrationReqUpdated);

    // Driver All Trips
    @POST("api/Trip/getAllTripByUserId")
    Call<GetAllTripByUserIdResponse> driverTrips(@Body GetAllTripByUserIdRequest getAllTripByUserIdRequest);

    // Vehicle Owner trip details
    @POST("api/Trip/getAllTripByUserId")
    Call<GetAllTripByUserIdResponse> vehicleOwnerTrip(@Body GetAllTripByUserIdRequest getAllTripByUserIdRequest);

    // Miller all trips details
    @POST("api/Trip/getAllTripByUserId")
    Call<GetAllTripByUserIdResponse> millerTrip(@Body GetAllTripByUserIdRequest getAllTripByUserIdRequest);

    // Customer all trip
    @POST("api/Trip/getAllTripByUserId")
    Call<GetAllTripByUserIdResponse> customerTrip(@Body GetAllTripByUserIdRequest getAllTripByUserIdRequest);

    // Add New Vehicle
    @POST("index.php/api/Vehicle/addVehicle")
    Call<AddVehicleResponse> addVehicleResponseCall(@Body AddVehicleRequest addVehicleRequest);

    // Add new Driver
    @POST("api/Trip/addNewDriver")
    Call<AddNewDriverResponse> addNewDriverResponseCall(@Body AddNewDriverRequest addNewDriverRequest);


    @POST("index.php/api/Login/stateDropdown")
    Call<StateResp> onState();


    @POST("index.php/api/Login/cityDropdown")
    @FormUrlEncoded
    Call<CityResp> onCity(@Field("stateId") String stateId);



    // vehicle type drop dowan --spinner for vehicle owners for Add new Vehicle
    @POST("api/Vehicle/vehicleTypeDropdown")
    Call<VehicleTypeDropDowanResponse> vehicleTypeDropDowanResp(@Body VehicleTypeDropDowanRequest vehicleTypeDropDowanRequest);

    // Driver drop down ----spinners
    @POST("index.php/api/vehicle/driverDropdown")
    Call<DriverDropDownResponse> driverDataResponse(@Body DriverDropDownRequest driverDropDownRequest);

    //vehicle drop dowan --spinner for AssignD2V
    @POST("index.php/api/vehicle/vehicleAssignDropdown")
    Call<VehicleAssignDropDowanResponse> VEHICLE_ASSIGN_DROP_DOWAN_RESPONSE_CALL(@Body VehicleAssignDropDowanRequest vehicleAssignDropDowanRequest);

    // Assign Driver 2 vehicle
    @POST("api/Vehicle/assignDriverToVehicle")
    Call<AssignD2VResponse> assignD2VReq(@Body AssignD2VRequest assignD2VRequest);

    // Goods  Types in miller module ..book load ----Spinners
    @POST("index.php/api/trip/goodType")
    Call<GoodsTypesDropDownResponse> goodsTypesResponse(@Body GoodsTypeDropDownRequest goodsTypeDropDownRequest);

    // Approx load spinners -- for miller Book load
    @POST("index.php/api/trip/approxLoad")
    Call<ApproxLoadDropDownResponse> approxLoadResponse(@Body ApproxLoadDropDownRequest approxLoadDropDownRequest);

    // getAll Driver List for owner;
    @POST("index.php/api/login/getAlldriverDetails")
    Call<GetAllDriverResponse> getAllDriverDetailsRes(@Body GetAllDriverRequest getAllDriverRequest);

    @POST("index.php/api/Vehicle/getAllVehicleDetails")
    Call<GetAllVehicleResponse> getVehicleResponse(@Body GetAllVehicleRequest getAllVehicleRequest);


    @POST("index.php/api/Vehicle/vehicleOnOff")
    Call<VehicleOnOffResponse> getrvehicleOnOff(@Body VehicleOnOffReq vehicleOnOffReq);




    //Goods type DataVehicle Booking With details Activity
    @POST("api/Trip/selectYourGoodType")
    Call<GoodsTypeDataResponse> SELECT_UR_GOODS_TYPE_DATA_RESPONSE_CALL(@Body GoodsTypeDataRequest goodsTypeDataRequest);

    // Add receiver mob no and name
    @POST("api/Login/updateReceiver")
    Call<UpdateReceiverPhoneNoAndNameResponse> UPDATE_RECEIVER_PHONE_NO_AND_NAME_RESPONSE_CALL(@Body UpdateReceiverPhoneNoAndNameRequest updateReceiverPhoneNoAndNameRequest);

    /// vehicle diamension size in add vehicle through vehicle owner
    @POST("api/Vehicle/vehicleDimensionSize")
    Call<VehicleDiamensionSizeResponse> VEHICLE_DIAMENSION_SIZE_RESPONSE_CALL(@Body VehicleDiamensionSizeRequest vehicleDiamensionSizeRequest);

    // vehicle load capicity through vehicle owner
    @POST("api/Vehicle/vehicleLoadingCapicity")
    Call<VehicleLoadCapicityResponse> VEHICLE_LOAD_CAPICITY_RESPONSE_CALL(@Body VehicleLoadCapicityRequest vehicleLoadCapicityRequest);

    // Booking vehicle List
    @POST("api/Vehicle/bookingVehicleList")
    Call<BookingVehicleListResponse> BOOKING_VEHICLE_LIST_RESPONSE_CALL(@Body BookingVehicleListRequest bookingVehicleListRequest);

    // Register for pushnotification
    @POST("api/Notification/registerPushNotification")
    Call<ResponseBody> registerForPush(@Body RegisterForPushModel vehicleInfoRequest);

    // conform booking Request
    @POST("api/Booking/confirmBookingApi")
    Call<BookingHTripResponse> BOOKING_H_TRIP_RESPONSE_CALL(@Body BookingHTripRequest bookingHTripRequest);

    //cancle reason
    @POST("api/Booking/cancelTripReason")
    Call<CancelTripReasonResponse>CANCEL_TRIP_REASON_RESPONSE_CALL (@Body CancelTripReasonRequest cancelTripReasonRequest);

    //cancel trip by customer
    @POST("api/Booking/cancelTripByCustomer")
    Call<CancelTripByCustomerResponse>CANCEL_TRIP_BY_CUSTOMER_RESPONSE_CALL(@Body CancelTripByCustomerRequest cancelTripByCustomerRequest);

    // Vehicle Info
    @POST("api/Booking/bookingInfoForCustomer")
    Call<BookingVehicleInfoResponse> BOOKING_VEHICLE_INFO_RESPONSE_CALL(@Body BookingVehicleInfoRequest bookingVehicleInfoRequest);


    @POST("api/Booking/bookingInfoForCustomer")
    Call<BookingVehicleInfoResponse> getonDriverOnLineOffline(@Body BookingVehicleInfoRequest bookingVehicleInfoRequest);


    // accept/reject booking trip by driver
    @POST("api/Booking/acceptBookTrip")
    Call<BookingAcceptRejectResponse> ACCEPT_REJECT_BOOKING_TRIP_RESPONSE_CALL(@Body BookingAcceptRejectData bookingAcceptReject);

    // get near by driver to the particular location
    @GET("api/Booking/getNearByDriverLocation")
    Call<NearByDriverLocationResponse> GET_NEAR_BY_DRIVER_LOCATION_RESPONSE_CALL();

    // Add location route used by the driver
    @POST("api/Booking/addCurrentLocationOfVehicle")
    Call<DriverResponse> VEHICLE_CURRENT_LOCATION_RESPONSE_CALL(@Body VehicleCurrentLocation vehicleCurrentLocation);

    // Driver start the trip from pickup location
    @POST("api/Booking/addCurrentLocationOfVehicle")
    Call<DriverResponse> DRIVER_START_TRIP_RESPONSE_CALL(@Body DriverStartTripRequest startTripRequest);

    // update trip status by driver
    @POST("api/Booking/updateTripStatusByDriver")
    Call<UpdateTripStatusByDriverResp> UPDATE_TRIP_STATUS_BY_DRIVER_RESP_CALL(@Body UpdateTripStatusByDriverReq updateTripStatusByDriverReq);

    // get driver details by id
    @POST("api/Booking/getDriverCurrentTripStatus")
    Call<DriverDetails> getDriverDetails(@Body DriverDetailRequest driverId);

    // get customer details by id
    @POST("api/Booking/getCustomerCurrentTripStatus")
    Call<DriverDetails> GET_CUSTOMER_CURRENT_TRIP_STATUS_RESPS_CALL(@Body GetCustomerCurrentTripStatusReq getCustomerCurrentTripStatusReq);

    // customer rating
    @POST("api/Trip/updateTripRatingByUser")
    Call<UpdateTripRatingByUserResp>UPDATE_TRIP_RATING_BY_USER_RESP_CALL(@Body UpdateTripRatingByUserReq updateTripRatingByUserReq);

    // DriverInvoice
    @POST("api/Email/driverInvoice")
    Call<DriverInvoiceResp>DRIVER_INVOICE_RESP_CALL(@Body DriverInvoiceReq driverInvoiceReq);

    //CustomerInvoiceReq
    @POST("api/Email/customerInvoice")
    Call<CustomerInvoiceResp>CUSTOMER_INVOICE_RESP_CALL(@Body CustomerInvoiceReq customerInvoiceReq);

    //update driver location
    @POST("api/Booking/updateDriverLocation")
    Call<UpdateDriverLocationResp>UPDATE_DRIVER_LOCATION_RESP_CALL(@Body UpdateDriverLocationReqst customerInvoiceReq);


}