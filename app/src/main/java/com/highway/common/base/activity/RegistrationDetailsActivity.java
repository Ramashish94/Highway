package com.highway.common.base.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.highway.R;
import com.highway.common.base.commonModel.registration.RegistrationReqUpdated;
import com.highway.common.base.commonModel.registration.RegistrationRespUpdated;
import com.highway.commonretrofit.RestClient;
import com.highway.utils.CameraUtils;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationDetailsActivity extends AppCompatActivity {

    private ImageView imgDetailbackArrow, imgDetailProfile, imgCalenderDatePicker;
    private EditText edtTxtUserName, edtTxtUserEmail, edtTxtUserMobile, edtTxtUserDobDate, edtTxtUserAddress;
    private RadioButton userRadioMale, userRadioFemale, userRadioDiver, userRadioCustomer, userRadioMillUser, userRadioOwner;
    private String gender;
    private String userRole;
    private Button btnSubmit;

    private RadioGroup radiogroup_Gender;
    private RadioGroup radioGroup_RoleUser;

    String userName, userEmail, userMobNo, userDobDate, userAddress, userId;
    private DatePickerDialog datePickerDialog;

    private Toolbar regToolbar;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int CAMERA_CAPTURE_FRONT_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_BACK_IMAGE_REQUEST_CODE = 101;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    //public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;
    private String base64UserImg;
    private String customerRoleId;
    private String driverRoleId;
    private String millerRoleId;
    private String ownerRoleID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_details);

        initView();  // finding by id
        setOnClickListener();
        //calenderPickOperation();         //  dob calender picker
        //radioGenderGroupOperation();    // gender group operation
        backArrowOperation();
    }


    public void initView() {

        regToolbar = findViewById(R.id.regToolbar);
        setSupportActionBar(regToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        regToolbar.setTitle("");
        regToolbar.setSubtitle("");

        imgDetailbackArrow = findViewById(R.id.regBackArrow);
        imgDetailProfile = findViewById(R.id.regUserImg);

        edtTxtUserName = findViewById(R.id.edtTxtInputUserName);
        edtTxtUserEmail = findViewById(R.id.edtTxtInputUserEmail);
        btnSubmit = findViewById(R.id.btnSubmitDetails);



    }

    public void setOnClickListener() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regDetailValidationOperation();

            }
        });

        imgDetailProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage();
                } else {
                    requestCameraPermission(MEDIA_TYPE_IMAGE);
                }
            }
        });
    }


    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                captureImage();
                            } else {
                                //captureVideo();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);   // start the image capture Intent
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);  // get the file url
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                previewCapturedImage();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    private void previewCapturedImage() {
        try {
            imgDetailProfile.setVisibility(View.VISIBLE);

            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);

            base64UserImg = getEncoded64ImageStringFromBitmap(bitmap);

            imgDetailProfile.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(RegistrationDetailsActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    // Img Uploading on Server in base 64 bit
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    public void backArrowOperation() {
        imgDetailbackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void calenderPickOperation() {
        imgCalenderDatePicker.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;// mHour, mMinute;

            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(RegistrationDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                        //dobDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        edtTxtUserDobDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }


    public void radioGenderGroupOperation() {
        radiogroup_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton genderrg = (RadioButton) group.findViewById(checkedId);
                if (null != genderrg) {
                    gender = genderrg.getText().toString();
                    Toast.makeText(RegistrationDetailsActivity.this, genderrg.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean inputValidation() {

        userName = edtTxtUserName.getText().toString().trim();
        userEmail = edtTxtUserEmail.getText().toString().trim();

        if (userName.isEmpty()) {
            edtTxtUserName.setError("enter a valid email address");
            return false;
        } else {
            edtTxtUserName.setError(null);
        }

        if (userEmail.isEmpty()) {
            edtTxtUserEmail.setError("enter a valid email address");
            return false;
        } else {
            edtTxtUserEmail.setError(null);
        }

        if (!validEmail(edtTxtUserEmail)) {   // email validation
            Toast.makeText(getApplicationContext(), "Enter valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validEmail(EditText edtTxtUserEmail) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; // onClick of button perform this simplest code.

        if (edtTxtUserEmail.getText().toString().matches(emailPattern)) {
            return true;
        } else {
            return false;
        }
    }


    public void regDetailValidationOperation() {

        if (inputValidation()) {

            RegistrationReqUpdated registrationReqUpdated = new RegistrationReqUpdated();
            registrationReqUpdated.setName(userName);
            registrationReqUpdated.setEmail(userEmail);
            userId = HighwayPrefs.getString(getApplicationContext(), Constants.ID);
            registrationReqUpdated.setUserId(userId);

            if (Utils.isInternetConnected(this)) {
                Utils.showProgressDialog(getApplicationContext());

                RestClient.regDetails(registrationReqUpdated, new Callback<RegistrationRespUpdated>() {
                    @Override
                    public void onResponse(Call<RegistrationRespUpdated> call, Response<RegistrationRespUpdated> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus()) {
                                if (response.body().getUser().getUserStatus().equalsIgnoreCase("1")) {
                                    Intent intent = new Intent(RegistrationDetailsActivity.this, DashBoardActivity.class);
                                    HighwayPrefs.putString(getApplicationContext(), Constants.ROLEID, response.body().getUser().getRoleId());
                                    HighwayPrefs.putString(getApplicationContext(), Constants.NAME, response.body().getUser().getName());
                                    HighwayPrefs.putString(getApplicationContext(), Constants.USERMOBILE, response.body().getUser().getMobile());
                                    HighwayPrefs.putString(getApplicationContext(), Constants.User_statuss, response.body().getUser().getUserStatus());
                                    System.out.println("User Status signup" + response.body().getUser().getUserStatus());
                                    /* use our requirement  */
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    HighwayPrefs.putString(getApplicationContext(), Constants.EMAIL, response.body().getUser().getEmail());
                                    HighwayPrefs.getString(getApplicationContext(), Constants.ID);
                                    startActivity(intent);
                                    finish();
                                }
                            } else if (response.body().getUser().getUserStatus().equalsIgnoreCase("0")) {
                                Toast.makeText(RegistrationDetailsActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegistrationDetailsActivity.this, "Pls Enter your details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegistrationRespUpdated> call, Throwable t) {
                        Toast.makeText(RegistrationDetailsActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    // onBacked pressed registration
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
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

}
