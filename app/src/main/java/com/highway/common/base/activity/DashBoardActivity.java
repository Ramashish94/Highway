package com.highway.common.base.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.highway.R;
import com.highway.common.base.HighwayApplication;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CancelTrip;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CompletedTrip;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.OngoingTrip;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.UpcomingTrip;
import com.highway.common.base.firebaseService.MyFirebaseServiceMessaging;
import com.highway.commonretrofit.RestClient;
import com.highway.customer.RegisterForPushModel;
import com.highway.customer.customerActivity.BookingConformedActivity;
import com.highway.customer.customerActivity.WebViewActivity;
import com.highway.customer.customerFragment.DashBordFragmentForCustomer;
import com.highway.customer.customerFragment.InvoiceBottomDialogFragmentForCustomer;
import com.highway.customer.customerFragment.NewBookingFragment;
import com.highway.customer.customerFragment.RatingBottomDialogFragmentForCustomer;
import com.highway.customer.customerModelClass.customerCurrentTripStatus.CustomerTripStatus;
import com.highway.customer.customerModelClass.customerCurrentTripStatus.GetCustomerCurrentTripStatusReq;
import com.highway.customer.customerModelClass.customerCurrentTripStatus.GetCustomerCurrentTripStatusResp;
import com.highway.drivermodule.driverActivity.DriverAllTripsActivity;
import com.highway.drivermodule.driverFragment.DashBoardFragmentForDriver;
import com.highway.drivermodule.driverFragment.DriverOnlineFragment;
import com.highway.drivermodule.driverFragment.IncomingRequestFragmentForDriver;
import com.highway.drivermodule.driverFragment.InvoiceBottomDialogFragmentForDriver;
import com.highway.drivermodule.driverFragment.RatingBottomDialogFragmentForDriver;
import com.highway.drivermodule.drivermodels.DriverDetailRequest;
import com.highway.drivermodule.drivermodels.DriverDetails;
import com.highway.drivermodule.drivermodels.TripStatus;
import com.highway.millUserModule.milluserFragment.BookLoadFragmentForMillUser;
import com.highway.millUserModule.milluserFragment.DashBoardFragmentForMillUser;
import com.highway.ownermodule.vehicleOwner.vehicleOwnerfragment.AddDriverFragmentForVehicleOwner;
import com.highway.ownermodule.vehicleOwner.vehicleOwnerfragment.AddVehicleFragmentForVehicleOwner;
import com.highway.ownermodule.vehicleOwner.vehicleOwnerfragment.Assign_D2V_FragmentForVehicleOwner;
import com.highway.ownermodule.vehicleOwner.vehicleOwnerfragment.DashBoardFragmentForVehicleOwner;
import com.highway.ownermodule.vehicleOwner.vehicleOwnerfragment.GetAllDriverFragmentForVehicleOwner;
import com.highway.ownermodule.vehicleOwner.vehicleOwnerfragment.GetAllVehicleFragmentForVehicleOwner;
import com.highway.utils.BaseUtil;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highway.utils.Constants.DROPPED;
import static com.highway.utils.Constants.INVOICE;
import static com.highway.utils.Constants.PICKEDUP;
import static com.highway.utils.Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY;
import static com.highway.utils.Constants.RATING;
import static com.highway.utils.Constants.TRIP_ACCEPTED;
import static com.highway.utils.Constants.TRIP_NEW;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyFirebaseServiceMessaging.OnMessageRecievedFromPush {

    private AppBarConfiguration mAppBarConfiguration;
    // for Driver

    private DashBoardFragmentForMillUser dashBoardFragmentForMillUser;
    private DashBoardFragmentForDriver dashBoardFragmentForDriver;
    private DriverOnlineFragment driverOnlineFragment;
    private DashBordFragmentForCustomer dashBordFragmentForCustomer;
    private DashBoardFragmentForVehicleOwner dashBoardFragmentForVehicleOwner;
    private DashBoardActivity dashBoardActivity;
    private AddVehicleFragmentForVehicleOwner addVehicleFragmentForVehicleOwner;
    private AddDriverFragmentForVehicleOwner addDriverFragmentForVehicleOwner;
    private Assign_D2V_FragmentForVehicleOwner assign_d2V_fragmentForVehicleOwner;
    private GetAllVehicleFragmentForVehicleOwner getAllVehicleFragmentForVehicleOwner;
    private BookLoadFragmentForMillUser bookLoadFragmentForMillUser;
    GetAllDriverFragmentForVehicleOwner getAllDriverFragmentForVehicleOwner;

    private List<CompletedTrip> completedTrips = new ArrayList<>();
    private List<OngoingTrip> ongoingTrips = new ArrayList<>();
    private List<UpcomingTrip> upcomingTrips = new ArrayList<>();
    private List<CancelTrip> cancelTrips = new ArrayList<>();
    private IncomingRequestFragmentForDriver incomingFragment;
    private BookingConformedActivity bookingConformed;


    public List<CancelTrip> getCancelTrips() {
        return cancelTrips;
    }

    public void setCancelTrips(List<CancelTrip> cancelTrips) {
        this.cancelTrips = cancelTrips;
    }

    public List<CompletedTrip> getCompletedTrips() {
        return completedTrips;
    }

    public void setCompletedTrips(List<CompletedTrip> completedTrips) {
        this.completedTrips = completedTrips;
    }

    public List<OngoingTrip> getOngoingTrips() {
        return ongoingTrips;
    }

    public void setOngoingTrips(List<OngoingTrip> ongoingTrips) {
        this.ongoingTrips = ongoingTrips;
    }

    public List<UpcomingTrip> getUpcomingTrips() {
        return upcomingTrips;
    }

    public void setUpcomingTrips(List<UpcomingTrip> upcomingTrips) {
        this.upcomingTrips = upcomingTrips;
    }


    public Toolbar dashBoardToolbar;
    public AppBarLayout appBarLayout;
    private CircleImageView nevCircularUserImgView;
    private TextView nevUserName, nevUserMobNo;
    String name, image, mobNo;
    private NavigationView navigationView;
    String userRole;
    private MenuItem newBooking, myBooking, millBooking, addVehicle, wallet, notification, rateCard, help,
            about, share, send, gallery, tCondition, logout, addDriver, assignD2V, getAllVehicle, getAllDriver, bookload;
    public JSONObject pushData;
    private String TAG = getClass().getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));

        setContentView(R.layout.activity_dash_board);
//        intent = getIntent();
//        notificationType = getIntent().getIntExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, 0);

        //setOnClickListenerOperation();

        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();
        // Add network connectivity change action.
        intentFilter.addAction(Constants.PUSH_ACTION);

        // Set broadcast receiver priority.
        intentFilter.setPriority(100);
        //  registerReceiver(new PushReciever(), intentFilter);
        //showDialog(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(DashBoardActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();

            RegisterForPushModel obj = new RegisterForPushModel();
            obj.setUserId(HighwayPrefs.getString(DashBoardActivity.this, Constants.ID));
            obj.setTokenId(newToken);

            RestClient.registerForPush(obj, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        if (response.code() == 200 && response.body() != null) {
                            Log.d("New Token Updated", response.body().string().toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("New Token Updated", "Failed to update Token");

                }
            });

            Log.e("newToken", newToken);
        });


        MyFirebaseServiceMessaging myFirebaseServiceMessaging = new MyFirebaseServiceMessaging();
        myFirebaseServiceMessaging.setPushListener(this);
        getPushData();
        navigationInitView();
        updateNavViewHeader();
        // navAccordingRoleId();// According RoleId Navigation Icon


    }

    public void navigationInitView() {
        dashBoardToolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        setSupportActionBar(dashBoardToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view);
        //btnLogOut = findViewById(R.id.btnLogout);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, dashBoardToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_dash_board);
        nevCircularUserImgView = headerView.findViewById(R.id.imageView);
        nevUserName = headerView.findViewById(R.id.userProfileName);
        nevUserMobNo = headerView.findViewById(R.id.userMobileNo);

        // navigation menuItem
        Menu menues = navigationView.getMenu();
        newBooking = menues.findItem(R.id.nav_new_booking);
        myBooking = menues.findItem(R.id.nav_my_booking);
        addVehicle = menues.findItem(R.id.nav_add_vehicle);
        assignD2V = menues.findItem(R.id.nav_assign_D2V);
        millBooking = menues.findItem(R.id.nav_add_millBooking);
        wallet = menues.findItem(R.id.nav_wallet);
        notification = menues.findItem(R.id.nav_notification);
        rateCard = menues.findItem(R.id.nav_rate_card);
        help = menues.findItem(R.id.nav_help);
        about = menues.findItem(R.id.nav_about);
        share = menues.findItem(R.id.nav_share);
        send = menues.findItem(R.id.nav_send);
        gallery = menues.findItem(R.id.nav_gallery);
        tCondition = menues.findItem(R.id.nav_trmCondition);
        addDriver = menues.findItem(R.id.nav_add_driver);
        getAllVehicle = menues.findItem(R.id.nav_add_getAllVehicle);
        getAllDriver = menues.findItem(R.id.nav_add_getAllDriver);
        bookload = menues.findItem(R.id.nav_add_bookLoad);
        logout = menues.findItem(R.id.nav_logout);
    }

    public void updateNavViewHeader() {
        image = HighwayPrefs.getString(getApplicationContext(), Constants.IMAGE);
        name = HighwayPrefs.getString(getApplicationContext(), Constants.NAME);
        mobNo = HighwayPrefs.getString(getApplicationContext(), Constants.USERMOBILE);

        nevUserName.setText(name);
        nevUserMobNo.setText(mobNo);

        if (!TextUtils.isEmpty(image)) {
            Picasso.with(this).load(image)
                    .error(R.drawable.highway_logo)
                    .into(nevCircularUserImgView);
        } else {
            Picasso.with(this)
                    .load(R.drawable.highway_logo)
                    .error(R.drawable.highway_logo)
                    .into(nevCircularUserImgView);
        }
    }

    public void navAccordingRoleId() {
        userRole = HighwayPrefs.getString(getApplicationContext(), Constants.ROLEID);
        switch (userRole) {

            case "1":                                     // Admin
                break;

            case "2":                                    //  mill user
                Fragment fragment2 = NewBookingFragment.newInstance();
                replaceFragment(fragment2, "");
                newBooking.setVisible(true);
                myBooking.setVisible(true);
                millBooking.setVisible(false);
                bookload.setVisible(true);
                addVehicle.setVisible(false);
                assignD2V.setVisible(false);
                wallet.setVisible(true);
                notification.setVisible(false);
                rateCard.setVisible(false);
                help.setVisible(false);
                about.setVisible(true);
                share.setVisible(true);
                send.setVisible(false);
                gallery.setVisible(false);
                tCondition.setVisible(true);
                addDriver.setVisible(false);
                getAllVehicle.setVisible(false);
                getAllDriver.setVisible(false);
                logout.setVisible(true);
                break;

            case "3":                                              // Driver

                newBooking.setVisible(false);
                myBooking.setVisible(true);
                millBooking.setVisible(false);
                assignD2V.setVisible(false);
                bookload.setVisible(false);
                addVehicle.setVisible(false);
                wallet.setVisible(false);
                notification.setVisible(false);
                rateCard.setVisible(false);
                help.setVisible(false);
                about.setVisible(true);
                share.setVisible(false);
                send.setVisible(false);
                gallery.setVisible(false);
                tCondition.setVisible(false);
                addDriver.setVisible(false);
                getAllVehicle.setVisible(false);
                getAllDriver.setVisible(false);
                logout.setVisible(true);


                if (pushData != null) {
                    incomingFragment = IncomingRequestFragmentForDriver.newInstance();
                    Bundle bundle = new Bundle();
                    incomingFragment.setArguments(bundle);
                    replaceFragment(incomingFragment, "Online");

                } else {
                    driverOnlineFragment = DriverOnlineFragment.newInstance();
                    replaceFragment(driverOnlineFragment, "Online");
                    getDriverDetails();

                }
                break;

            case "4":                                   //  Customer
                Fragment fragment4 = NewBookingFragment.newInstance();
                replaceFragment(fragment4, "");
                newBooking.setVisible(true);
                myBooking.setVisible(true);
                millBooking.setVisible(false);
                assignD2V.setVisible(false);
                bookload.setVisible(false);
                addVehicle.setVisible(false);
                wallet.setVisible(true);
                assignD2V.setVisible(false);
                notification.setVisible(false);
                rateCard.setVisible(false);
                help.setVisible(false);
                about.setVisible(true);
                share.setVisible(true);
                send.setVisible(false);
                gallery.setVisible(false);
                tCondition.setVisible(true);
                addDriver.setVisible(false);
                getAllVehicle.setVisible(false);
                getAllDriver.setVisible(false);
                logout.setVisible(true);

                getCustomerDetails();
                break;

            case "5":                              // Owner .. vehicle Owner
                Fragment fragment5 = NewBookingFragment.newInstance();
                replaceFragment(fragment5, "");
                newBooking.setVisible(false);
                myBooking.setVisible(true);
                millBooking.setVisible(false);
                bookload.setVisible(false);
                addVehicle.setVisible(true);
                addDriver.setVisible(true);
                assignD2V.setVisible(true);
                getAllDriver.setVisible(true);
                getAllVehicle.setVisible(true);
                wallet.setVisible(false);
                notification.setVisible(false);
                rateCard.setVisible(false);
                help.setVisible(false);
                about.setVisible(true);
                share.setVisible(true);
                send.setVisible(false);
                gallery.setVisible(false);
                tCondition.setVisible(true);
                logout.setVisible(true);
                break;
        }
    }

    // onBacked pressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        //  for Nav Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        // Exit on Double click
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        //  1 admin  //  2 mill user   // 3  driver  // 4  customer   // 5 owner
        switch (id) {
            case R.id.nav_new_booking:
                dashBoardToolbar.setTitle("New Booking");
                Fragment newBookingFragment = NewBookingFragment.newInstance();
                replaceFragment(newBookingFragment, "");
                break;

            case R.id.nav_my_booking:
                dashBoardToolbar.setTitle("My Booking");
                switch (userRole) {
                    case "1":
                        /*fragment = DashBordFragmentForCustomer.newInstance();
                        replaceFragment(fragment);*/
                        break;
                    case "2":
                        if (dashBoardFragmentForMillUser == null) {
                            dashBoardFragmentForMillUser = DashBoardFragmentForMillUser.newInstance();
                        }
                        replaceFragment(dashBoardFragmentForMillUser, "");
                        break;

                    case "3":
                        startActivity(new Intent(this, DriverAllTripsActivity.class));
                        break;

                    case "4":
                        if (dashBoardFragmentForVehicleOwner == null) {
                            dashBoardFragmentForVehicleOwner = DashBoardFragmentForVehicleOwner.newInstance();
                        }
                        replaceFragment(dashBoardFragmentForVehicleOwner, "");
//                        dashBordFragmentForCustomer = DashBordFragmentForCustomer.newInstance();
//                        replaceFragment(dashBordFragmentForCustomer);
                        break;

                    case "5":
                        if (dashBoardFragmentForVehicleOwner == null) {
                            dashBoardFragmentForVehicleOwner = DashBoardFragmentForVehicleOwner.newInstance();
                        }
                        replaceFragment(dashBoardFragmentForVehicleOwner, "");

                        break;
                }
                break;

            case R.id.nav_add_bookLoad:
                dashBoardToolbar.setTitle("Book Load");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":
                        if (bookLoadFragmentForMillUser == null) {
                            bookLoadFragmentForMillUser = BookLoadFragmentForMillUser.newInstance();
                        }
                        replaceFragment(bookLoadFragmentForMillUser, "");

                        break;

                    case "3":

                        break;

                    case "4":
                        break;

                    case "5":

                        break;
                }
                break;

            case R.id.nav_add_vehicle:
                dashBoardToolbar.setTitle("Add Vehicle");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":
                        if (addVehicleFragmentForVehicleOwner == null) {
                            addVehicleFragmentForVehicleOwner = AddVehicleFragmentForVehicleOwner.newInstance();
                        }
                        replaceFragment(addVehicleFragmentForVehicleOwner, "");
                        break;
                }
                break;

            case R.id.nav_add_driver:
                dashBoardToolbar.setTitle("Add Driver");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":
                        if (addDriverFragmentForVehicleOwner == null) {
                        }
                        addDriverFragmentForVehicleOwner = AddDriverFragmentForVehicleOwner.newInstance();
                        replaceFragment(addDriverFragmentForVehicleOwner, "");
                        break;
                }
                break;

            case R.id.nav_assign_D2V:
                dashBoardToolbar.setTitle("Assign D2V");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        if (assign_d2V_fragmentForVehicleOwner == null) {
                        }
                        assign_d2V_fragmentForVehicleOwner = Assign_D2V_FragmentForVehicleOwner.newInstance();
                        replaceFragment(assign_d2V_fragmentForVehicleOwner, "");
                        break;
                }
                break;

            case R.id.nav_add_getAllDriver:
                dashBoardToolbar.setTitle("All Driver Details");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":
                        if (getAllDriverFragmentForVehicleOwner == null) {
                            getAllDriverFragmentForVehicleOwner = GetAllDriverFragmentForVehicleOwner.newInstance();
                        }
                        replaceFragment(getAllDriverFragmentForVehicleOwner, "");
                        break;
                }
                break;

            case R.id.nav_add_getAllVehicle:
                dashBoardToolbar.setTitle("All Vehicle List");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":

                        if (getAllVehicleFragmentForVehicleOwner == null) {
                            getAllVehicleFragmentForVehicleOwner = GetAllVehicleFragmentForVehicleOwner.newInstance();
                        }
                        replaceFragment(getAllVehicleFragmentForVehicleOwner, "");
                        break;
                }
                break;

            case R.id.nav_wallet:
                dashBoardToolbar.setTitle("Wallet");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }

               /* Intent intent1 = new Intent(this, WebViewActivity.class);
                startActivity(intent1);*/
                break;

            case R.id.nav_notification:
                dashBoardToolbar.setTitle("Notification");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case R.id.nav_rate_card:
                dashBoardToolbar.setTitle("Rate Card");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case R.id.nav_help:
                dashBoardToolbar.setTitle("Help");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;

            case R.id.nav_trmCondition:
                // dashBoardToolbar.setTitle("Term & Condition");  // term and condition
                switch (userRole) {
                    case "1":
                        break;
                    case "2":
                        Intent intent2 = new Intent(DashBoardActivity.this, WebViewActivity.class);
                        intent2.putExtra("title", "Terms & Conditions");
                        startActivity(intent2);
                        break;
                    case "3":
                        Intent intent3 = new Intent(DashBoardActivity.this, WebViewActivity.class);
                        intent3.putExtra("title", "Terms & Conditions");
                        startActivity(intent3);
                        break;
                    case "4":
                        Intent intent4 = new Intent(DashBoardActivity.this, WebViewActivity.class);
                        intent4.putExtra("title", "Terms & Conditions");
                        startActivity(intent4);
                        break;
                    case "5":
                        Intent intent5 = new Intent(DashBoardActivity.this, WebViewActivity.class);
                        intent5.putExtra("title", "Terms & Conditions");
                        startActivity(intent5);
                        break;
                }
                break;


            case R.id.nav_about:

                //  dashBoardToolbar.setTitle("About");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                Intent intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_share:
                //   dashBoardToolbar.setTitle("Share");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":

                        break;
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hello friends, the best app for Highway is now available at: https://play.google.com/store/apps/details?id=com.dnamedical");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;

            case R.id.nav_send:
                dashBoardToolbar.setTitle("Send");
                switch (userRole) {
                    case "1":
                        break;
                    case "2":

                        break;
                    case "3":

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                }
                break;


            case R.id.nav_logout:
                logOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void replaceFragment(Fragment fragment, String tag) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment, tag);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut() {
        HighwayPrefs.putBoolean(getApplicationContext(), Constants.LOGGED_IN, false);
        Intent intent = new Intent(DashBoardActivity.this, LoginOptionActivity.class);
        startActivity(intent);
        finish();
    }

    public void showDialog(DashBoardActivity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.new_trip_request);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FrameLayout mDialogNo = dialog.findViewById(R.id.accept);
        mDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        FrameLayout mDialogOk = dialog.findViewById(R.id.reject);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Okay", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                pushData = new JSONObject(intent.getStringExtra(PUSH_NEW_BOOKING_TRIP_DATA_KEY));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(userRole) && userRole.equalsIgnoreCase("3")) {
                incomingFragment = IncomingRequestFragmentForDriver.newInstance();
                Bundle bundle = new Bundle();
                incomingFragment.setArguments(bundle);
                replaceFragment(incomingFragment, "Online");
            }

            if (!TextUtils.isEmpty(userRole) && userRole.equalsIgnoreCase("4")) {
                getCustomerDetails();
            }

        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navAccordingRoleId();


    }


    private void getPushData() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(PUSH_NEW_BOOKING_TRIP_DATA_KEY)) {
            try {
                pushData = new JSONObject(getIntent().getStringExtra(PUSH_NEW_BOOKING_TRIP_DATA_KEY));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e(TAG, BaseUtil.jsonFromModel(pushData));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mMessageReceiver != null) {
                unregisterReceiver(mMessageReceiver);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyData")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    public void showInvoiceBottomSheetDriver() {
        InvoiceBottomDialogFragmentForDriver addPhotoBottomDialogFragment =
                InvoiceBottomDialogFragmentForDriver.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                InvoiceBottomDialogFragmentForDriver.TAG);
    }


    public void showInVoiceBottomSheetCustomer() {
        InvoiceBottomDialogFragmentForCustomer addPhotoBottomDialogFragment =
                InvoiceBottomDialogFragmentForCustomer.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                InvoiceBottomDialogFragmentForDriver.TAG);
    }


    public void showratingBottomSheetDriver() {
        RatingBottomDialogFragmentForDriver addPhotoBottomDialogFragment =
                RatingBottomDialogFragmentForDriver.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                InvoiceBottomDialogFragmentForDriver.TAG);
    }


    public void showratingBottomSheetForCustomer() {
        RatingBottomDialogFragmentForCustomer addPhotoBottomDialogFragment =
                RatingBottomDialogFragmentForCustomer.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                RatingBottomDialogFragmentForCustomer.class.getSimpleName());
    }


    @Override
    public void onPushData(JSONObject jsonObject) {
        if (!TextUtils.isEmpty(userRole)) {
            incomingFragment = IncomingRequestFragmentForDriver.newInstance();
            Bundle bundle = new Bundle();
            incomingFragment.setArguments(bundle);
            pushData = jsonObject;
            replaceFragment(incomingFragment, "Online");

        }
    }


    private void getDriverDetails() {
        DriverDetailRequest driverData = new DriverDetailRequest();

        driverData.setDriverId(HighwayPrefs.getString(this, Constants.ID));
        RestClient.getDriverDetails(driverData, new Callback<DriverDetails>() {
            @Override
            public void onResponse(Call<DriverDetails> call, Response<DriverDetails> response) {
                if (response != null && response.code() == 200 && response.body() != null) {
                    TripStatus tripStatus = response.body().getDriverTripStatus();
                    Log.d("Driver Details", "" + tripStatus.getCurrentTripStatus());

                    if (tripStatus.getRatingStatus().equalsIgnoreCase("0")) {
                        HighwayApplication.getInstance().setCurrentTripId(tripStatus.getBookingTripId());
                        HighwayApplication.getInstance().setUserDetails(tripStatus);
                        incomingFragment = IncomingRequestFragmentForDriver.newInstance();
                        Bundle bundle = new Bundle();
                        incomingFragment.setArguments(bundle);
                        replaceFragment(incomingFragment, "Online");
                    }

                }
            }

            @Override
            public void onFailure(Call<DriverDetails> call, Throwable t) {
                Log.d("User Details", "" + t.getMessage());

            }
        });


    }


   /* private void getCustomerDetails() {
        DriverDetailRequest data = new DriverDetailRequest();
            data.setDriverId(HighwayPrefs.getString(this, Constants.ID));
            RestClient.getCustomerDetails(data, new Callback<DriverDetails>() {
                @Override
                public void onResponse(Call<DriverDetails> call, Response<DriverDetails> response) {
                    if (response != null && response.code() == 200 && response.body() != null) {
                        Log.d("User Details", "" + response.body().getDriverTripStatus().getCurrentTripStatus());


                    }
                }

                @Override
                public void onFailure(Call<DriverDetails> call, Throwable t) {
                    Log.d("User Details", "" + t.getMessage());

                }
            });
    }*/


    public void getCustomerDetails() {
        GetCustomerCurrentTripStatusReq customerTripRequest = new GetCustomerCurrentTripStatusReq();
        customerTripRequest.setCustomerId(HighwayPrefs.getString(this, Constants.ID));

        RestClient.getCustomerDetails(customerTripRequest, new Callback<GetCustomerCurrentTripStatusResp>() {
            @Override
            public void onResponse(Call<GetCustomerCurrentTripStatusResp> call, Response<GetCustomerCurrentTripStatusResp> response) {

                if (response != null && response.code() == 200 && response.body() != null) {
                    CustomerTripStatus customerTripStatus = response.body().getTripStatus();
                    //  Log.d("Customer Details", "" + customerTripStatus.getCurrentTripStatus());

                    if (customerTripStatus.getRatingStatus().equalsIgnoreCase("0")) {
                        HighwayApplication.getInstance().setCurrentTripId(customerTripStatus.getBookingTripId());
                        HighwayApplication.getInstance().setUserDetails(customerTripStatus);
                        HighwayApplication.getInstance().setCurrentTripId(customerTripStatus.getBookingTripId());


                        if (customerTripStatus.getCurrentTripStatus().equalsIgnoreCase(TRIP_ACCEPTED)
                                || customerTripStatus.getCurrentTripStatus().equalsIgnoreCase(PICKEDUP)
                                || customerTripStatus.getCurrentTripStatus().equalsIgnoreCase(TRIP_NEW)) {
                            Intent intent = new Intent(DashBoardActivity.this, BookingConformedActivity.class);
                            startActivity(intent);
                        } else if (customerTripStatus.getCurrentTripStatus().equalsIgnoreCase(DROPPED)) {
                            showInVoiceBottomSheetCustomer();
                        } else if (customerTripStatus.getCurrentTripStatus().equalsIgnoreCase(INVOICE)) {
                            showInVoiceBottomSheetCustomer();
                        } else if (customerTripStatus.getCurrentTripStatus().equalsIgnoreCase(RATING)) {
                            showratingBottomSheetForCustomer();
                        }



                       /* dashBordFragmentForCustomer = DashBordFragmentForCustomer.newInstance();
                       Bundle bundle = new Bundle();
                       dashBordFragmentForCustomer.setArguments(bundle);
                       replaceFragment(dashBordFragmentForCustomer, " ");*/
                    }

                }
            }

            @Override
            public void onFailure(Call<GetCustomerCurrentTripStatusResp> call, Throwable t) {
                Log.d("User Details", "" + t.getMessage());
                Toast.makeText(DashBoardActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
