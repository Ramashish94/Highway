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
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleAssignDropDowanRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleAssignDropDowanResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.vehiceLoadCapicity.VehicleLoadCapicityRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.vehiceLoadCapicity.VehicleLoadCapicityResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleDimensionSize.VehicleDiamensionSizeRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleDimensionSize.VehicleDiamensionSizeResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.assignD2V_Model.AssignD2VRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.assignD2V_Model.AssignD2VResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DriverDropDownRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DriverDropDownResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.GetAllDriverRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.GetAllDriverResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.GetAllVehicleRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.GetAllVehicleResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleTypeDropDowan.VehicleTypeDropDowanRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.vehicleTypeDropDowan.VehicleTypeDropDowanResponse;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class RestClient {
     // login mob no
    public static void loginUser(LoginReqUpdated loginReqUpdated, Callback<ResponseBody> callback) {
        RetrofitClient.getClient().loginResponseCall(loginReqUpdated).enqueue(callback);
    }

    // verify otp mobile
    public static void otpVerify(VerifyOtpRequest verifyOtpRequest, Callback<VerifyOtpResponse> otpResponseCallback) {
        RetrofitClient.getClient().verifyOtpResponseCall(verifyOtpRequest).enqueue(otpResponseCallback);
    }

    // registration details
    public static void regDetails(RegistrationReqUpdated registrationReqUpdated, Callback<RegistrationRespUpdated> registrationRespUpdatedCallback) {
        RetrofitClient.getClient().regDetailsResponseCall(registrationReqUpdated).enqueue(registrationRespUpdatedCallback);
    }

    // driver trip
    public static void allDriverTrips(GetAllTripByUserIdRequest getAllTripByUserIdRequest, Callback<GetAllTripByUserIdResponse> allDriverTripsResponseCallback) {
        RetrofitClient.getClient().driverTrips(getAllTripByUserIdRequest).enqueue(allDriverTripsResponseCallback);
    }

    // vehicle owner trip
    public static void allVehicleOwnerTrip(GetAllTripByUserIdRequest getAllTripByUserIdRequest, Callback<GetAllTripByUserIdResponse> allHighwayTripsResponseByOwnerCallback) {
        RetrofitClient.getClient().vehicleOwnerTrip(getAllTripByUserIdRequest).enqueue(allHighwayTripsResponseByOwnerCallback);
    }

    //   mill user trip
    public static void allMillerTrip(GetAllTripByUserIdRequest getAllTripByUserIdRequest, Callback<GetAllTripByUserIdResponse> allHighwayTripsResponseByMillerCallback) {
        RetrofitClient.getClient().millerTrip(getAllTripByUserIdRequest).enqueue(allHighwayTripsResponseByMillerCallback);
    }

    // Customer trip
    public static void allCustomerTrip(GetAllTripByUserIdRequest getAllTripByUserIdRequest, Callback<GetAllTripByUserIdResponse> allHighwayTripsResponseByCustomerCallback) {
        RetrofitClient.getClient().customerTrip(getAllTripByUserIdRequest).enqueue(allHighwayTripsResponseByCustomerCallback);
    }

    // Add new vehicle
    public static void addNewVehicle(AddVehicleRequest addVehicleRequest, Callback<AddVehicleResponse> addVehicleResponseCallback) {
        RetrofitClient.getClient().addVehicleResponseCall(addVehicleRequest).enqueue(addVehicleResponseCallback);
    }


    //add new Driver

    public static void addNewDriver(AddNewDriverRequest addNewDriverRequest, Callback<AddNewDriverResponse> addNewDriverResponseCallback) {
        RetrofitClient.getClient().addNewDriverResponseCall(addNewDriverRequest).enqueue(addNewDriverResponseCallback);
    }


    // state
    public static void onState(Callback<StateResp> addNewDriverResponseCallback) {
        RetrofitClient.getClient().onState().enqueue(addNewDriverResponseCallback);
    }

    // City
    public static void onCity(String stateId, Callback<CityResp> addNewDriverResponseCallback) {
        RetrofitClient.getClient().onCity(stateId).enqueue(addNewDriverResponseCallback);
    }



    public static void registerForPush(RegisterForPushModel obj, Callback<ResponseBody> addNewDriverResponseCallback) {
        RetrofitClient.getClient().registerForPush(obj).enqueue(addNewDriverResponseCallback);
    }

    // driver drop dowan ---spinners for owner
    public static void getDriverList(DriverDropDownRequest driverDropDownRequest, Callback<DriverDropDownResponse> driverDropDownResponseCallback) {
        RetrofitClient.getClient().driverDataResponse(driverDropDownRequest).enqueue(driverDropDownResponseCallback);
    }

    // vehicle Type drop dowan -- spinners for owner for add new vehicle
    public static void getVehicleTypeList(VehicleTypeDropDowanRequest vehicleTypeDropDowanRequest, Callback<VehicleTypeDropDowanResponse> vehicleTypeDropDowanResponseCallback) {
        RetrofitClient.getClient().vehicleTypeDropDowanResp(vehicleTypeDropDowanRequest).enqueue(vehicleTypeDropDowanResponseCallback);
    }

    // vehicle list spinner in AssignD2s /////////////////////////////////////
    public static void getVehicleAssignList(VehicleAssignDropDowanRequest vehicleAssignDropDowanRequest, Callback<VehicleAssignDropDowanResponse> vehicleAssignDropDowanResponseCallback) {
        RetrofitClient.getClient().VEHICLE_ASSIGN_DROP_DOWAN_RESPONSE_CALL(vehicleAssignDropDowanRequest).enqueue(vehicleAssignDropDowanResponseCallback);
    }

  /*  public static void getVehicleList(Callback<VehicleDropDownResponse> callback) {
        RetrofitClient.getClient().vehicleData().enqueue(callback);   // ByPart
    }*/

    // Approx Load spinners
    public static void getApproxLoadList(ApproxLoadDropDownRequest approxLoadDropDownRequest, Callback<ApproxLoadDropDownResponse> approxLoadResponseCallback) {
        RetrofitClient.getClient().approxLoadResponse(approxLoadDropDownRequest).enqueue(approxLoadResponseCallback);
    }

    // Goods types Spinners in miller module ...book load
    public static void getGoodsTypesLIst(GoodsTypeDropDownRequest goodsTypeDropDownRequest, Callback<GoodsTypesDropDownResponse> goodsTypeResponseCallback) {
        RetrofitClient.getClient().goodsTypesResponse(goodsTypeDropDownRequest).enqueue(goodsTypeResponseCallback);
    }

    // getAll driver details
    public static void getAllDriverDetails(GetAllDriverRequest getAllDriverRequest, Callback<GetAllDriverResponse> getAllDriverResponseCallback) {
        RetrofitClient.getClient().getAllDriverDetailsRes(getAllDriverRequest).enqueue(getAllDriverResponseCallback);
    }

    // getAll Vehicle details
    public static void getAllVehicleDetails(GetAllVehicleRequest getAllVehicleRequest, Callback<GetAllVehicleResponse> getAllVehicleResponseCallback) {
        RetrofitClient.getClient().getVehicleResponse(getAllVehicleRequest).enqueue(getAllVehicleResponseCallback);
    }

    public static void getvehicleOnOff(VehicleOnOffReq getAllVehicleRequest, Callback<VehicleOnOffResponse> getAllVehicleResponseCallback) {
        RetrofitClient.getClient().getrvehicleOnOff(getAllVehicleRequest).enqueue(getAllVehicleResponseCallback);
    }

    // Assign Driver 2 vehicle
    public static void assign_D2V(AssignD2VRequest assignD2VRequest, Callback<AssignD2VResponse> assignD2VResponseCallback) {
        RetrofitClient.getClient().assignD2VReq(assignD2VRequest).enqueue(assignD2VResponseCallback);
    }

    //Goods type DataVehicle Booking With details Activity
    public static void selectUrGoodsType(GoodsTypeDataRequest goodsTypeDataRequest, Callback<GoodsTypeDataResponse> selectUrGoodsTypeDataResponseCallback) {
        RetrofitClient.getClient().SELECT_UR_GOODS_TYPE_DATA_RESPONSE_CALL(goodsTypeDataRequest).enqueue(selectUrGoodsTypeDataResponseCallback);
    }

    // update receiver no
    public static void updateReceiverNameOrNumber(UpdateReceiverPhoneNoAndNameRequest updateReceiverPhoneNoAndNameRequest, Callback<UpdateReceiverPhoneNoAndNameResponse> updateReceiverPhoneNoAndNameResponseCallback) {
        RetrofitClient.getClient().UPDATE_RECEIVER_PHONE_NO_AND_NAME_RESPONSE_CALL(updateReceiverPhoneNoAndNameRequest).enqueue(updateReceiverPhoneNoAndNameResponseCallback);
    }

    //Vehicle diamention size
    public static void vehicleDiamension(VehicleDiamensionSizeRequest vehicleDiamensionSizeRequest, Callback<VehicleDiamensionSizeResponse> vehicleDiamensionSizeResponseCallback) {
        RetrofitClient.getClient().VEHICLE_DIAMENSION_SIZE_RESPONSE_CALL(vehicleDiamensionSizeRequest).enqueue(vehicleDiamensionSizeResponseCallback);
    }

    // vehicle load capicirty through vehicle ownwer
    public static void vehicleLoadCapicity(VehicleLoadCapicityRequest vehicleLoadCapicityRequest, Callback<VehicleLoadCapicityResponse> vehicleLoadCapicityResponseCallback) {
        RetrofitClient.getClient().VEHICLE_LOAD_CAPICITY_RESPONSE_CALL(vehicleLoadCapicityRequest).enqueue(vehicleLoadCapicityResponseCallback);
    }

    // Booking vehicle list through customer
    public static void getBookingVehicleList(BookingVehicleListRequest bookingVehicleListRequest, Callback<BookingVehicleListResponse> bookingVehicleListResponseCallback) {
        RetrofitClient.getClient().BOOKING_VEHICLE_LIST_RESPONSE_CALL(bookingVehicleListRequest).enqueue(bookingVehicleListResponseCallback);
    }

    // conform booking
    public static void confirmBooking(BookingHTripRequest bookingHTripRequest, Callback<BookingHTripResponse> bookingHTripRespCallback) {
        RetrofitClient.getClient().BOOKING_H_TRIP_RESPONSE_CALL(bookingHTripRequest).enqueue(bookingHTripRespCallback);
    }



    // cancel reason type
    public static void getCancleReason(CancelTripReasonRequest cancelTripReasonRequest, Callback<CancelTripReasonResponse> cancelTripReasonResponseCallback) {
        RetrofitClient.getClient().CANCEL_TRIP_REASON_RESPONSE_CALL(cancelTripReasonRequest).enqueue(cancelTripReasonResponseCallback);
    }

    // cancel trip
    public static void getCancelledTripByCust(CancelTripByCustomerRequest cancelTripByCustomerRequest, Callback<CancelTripByCustomerResponse> cancelTripByCustomerResponseCallback) {
        RetrofitClient.getClient().CANCEL_TRIP_BY_CUSTOMER_RESPONSE_CALL(cancelTripByCustomerRequest).enqueue(cancelTripByCustomerResponseCallback);
    }

    // booking vehicle info
    public static void getInfo(BookingVehicleInfoRequest bookingVehicleInfoRequest, Callback<BookingVehicleInfoResponse> bookingVehicleInfoResponseCallback) {
        RetrofitClient.getClient().BOOKING_VEHICLE_INFO_RESPONSE_CALL(bookingVehicleInfoRequest).enqueue(bookingVehicleInfoResponseCallback);
    }
    public static void onDriverOnLineOffline(BookingVehicleInfoRequest bookingVehicleInfoRequest, Callback<BookingVehicleInfoResponse> bookingVehicleInfoResponseCallback) {
        RetrofitClient.getClient().getonDriverOnLineOffline(bookingVehicleInfoRequest).enqueue(bookingVehicleInfoResponseCallback);
    }



    // accept/reject booking by driver
    public static void acceptRejectBookingTrip(BookingAcceptRejectData acceptRejectData, Callback<BookingAcceptRejectResponse> acceptRejectResponseCallback) {
        RetrofitClient.getClient().ACCEPT_REJECT_BOOKING_TRIP_RESPONSE_CALL(acceptRejectData).enqueue(acceptRejectResponseCallback);
    }

    // get near by driver to the particular location
    public static void getNearByDriverLocation(Callback<NearByDriverLocationResponse> driverLocationResponseCallback) {
        RetrofitClient.getClient().GET_NEAR_BY_DRIVER_LOCATION_RESPONSE_CALL().enqueue(driverLocationResponseCallback);
    }

    // Add location route used by the driver
    public static void addCurrentLocationOfVehicle(VehicleCurrentLocation vehicleCurrentLocation, Callback<DriverResponse> driverLocationResponseCallback) {
        RetrofitClient.getClient().VEHICLE_CURRENT_LOCATION_RESPONSE_CALL(vehicleCurrentLocation).enqueue(driverLocationResponseCallback);
    }

    // Driver start the trip from pickup location
    public static void addCurrentLocationOfVehicle(DriverStartTripRequest startTripRequest, Callback<DriverResponse> driverLocationResponseCallback) {
        RetrofitClient.getClient().DRIVER_START_TRIP_RESPONSE_CALL(startTripRequest).enqueue(driverLocationResponseCallback);
    }

    // updated trip status by driver
    public static void updateTripStatusByDriver(UpdateTripStatusByDriverReq updateTripStatusByDriverReq, Callback<UpdateTripStatusByDriverResp>updateTripStatusByDriverRespCallback){
        RetrofitClient.getClient().UPDATE_TRIP_STATUS_BY_DRIVER_RESP_CALL(updateTripStatusByDriverReq).enqueue(updateTripStatusByDriverRespCallback);
    }


    // get driver details
    public static void getDriverDetails(DriverDetailRequest obj, Callback<DriverDetails>updateTripStatusByDriverRespCallback){
        RetrofitClient.getClient().getDriverDetails(obj).enqueue(updateTripStatusByDriverRespCallback);
    }

    // get Customer details
   /* public static void getCustomerDetails(DriverDetailRequest obj, Callback<DriverDetails>updateTripStatusByDriverRespCallback){
        RetrofitClient.getClient().getCustomerDetails(obj).enqueue(updateTripStatusByDriverRespCallback);
    }*/
    public static void getCustomerDetails(GetCustomerCurrentTripStatusReq obj, Callback<DriverDetails>customerTripResponseCallback) {
        RetrofitClient.getClient().GET_CUSTOMER_CURRENT_TRIP_STATUS_RESPS_CALL(obj).enqueue(customerTripResponseCallback);
    }

    // user reating
    public static void getRatingUser(UpdateTripRatingByUserReq updateTripRatingByUserReq, Callback<UpdateTripRatingByUserResp>updateTripRatingByUserRespCallback){
        RetrofitClient.getClient().UPDATE_TRIP_RATING_BY_USER_RESP_CALL(updateTripRatingByUserReq).enqueue(updateTripRatingByUserRespCallback);
    }

    // invoice driver
    public static void getDriverInvoice(DriverInvoiceReq driverInvoiceReq, Callback<DriverInvoiceResp>driverInvoiceRespCallback){
        RetrofitClient.getClient().DRIVER_INVOICE_RESP_CALL(driverInvoiceReq).enqueue(driverInvoiceRespCallback);
    }

    // invoice customer
     public static void getCustomerInvoice(CustomerInvoiceReq customerInvoiceReq, Callback<CustomerInvoiceResp>customerInvoiceRespCallback){
        RetrofitClient.getClient().CUSTOMER_INVOICE_RESP_CALL(customerInvoiceReq).enqueue(customerInvoiceRespCallback);
     }

    // update driver location
    public static void getUpdateDriverLocation(UpdateDriverLocationReqst updateDriverLocationReqst, Callback<UpdateDriverLocationResp>updateDriverLocationRespCallback){
        RetrofitClient.getClient().UPDATE_DRIVER_LOCATION_RESP_CALL(updateDriverLocationReqst).enqueue(updateDriverLocationRespCallback);
    }

}