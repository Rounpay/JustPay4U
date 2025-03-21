package com.solution.app.justpay4u.Fintech.Employee.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.dreamseller.imagepicker.ImagePicker;
import com.dreamseller.imagepicker.OnImagePickedListener;
import com.solution.app.justpay4u.Api.Fintech.Object.AreaWithPincode;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AreaWithPincodeResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.CreateMeatingPurpuse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.CreateMeatingResaon;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetUserByMobileRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.SubmitMeetingRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.CreateMeatingResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.UserByMobileResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.GetLocation;
import com.solution.app.justpay4u.Util.RoundedCornersTransformation;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NewMeetingActivity extends AppCompatActivity {

    private ImagePicker imagePicker;
    private TextView txtSelectImage;
    ImageView image;
    private EditText txtMobileNo;
    private EditText txtname;
    private EditText txtoutletName;
    private EditText txtpincode;
    private TextView area;
    private View areaChooserView;
    private TextView purpose;
    private View purposeChooserView;
    private EditText txtconsumption;
    private CheckBox checkbox;
    private LinearLayout llRechargeCons;
    private EditText txtRechargeCons;
    private LinearLayout llBillCons;
    private EditText txtBillCons;
    private LinearLayout llMoneyTxnCons;
    private EditText txtMoneyTxnCons;
    private LinearLayout llAepsCons;
    private EditText txtAepsCons;
    private LinearLayout llMiniAtmCons;
    private EditText txtMiniAtmCons;
    private LinearLayout llInsuranceCons;
    private EditText txtInsuranceCons;
    private LinearLayout llHotelFlightCons;
    private EditText txtHotelFlightCons;
    private LinearLayout llPanCardCons;
    private EditText txtPanCardCons;
    private LinearLayout llVehicleInsuranceCons;
    private EditText txtVehicleInsuranceCons;
    private LinearLayout llReason;
    private TextView reason;
    private View reasonChooserView;
    private EditText txtremark;
    private Button btnPaymentSubmit;
    private DropDownDialog mDropDownDialog;

    private String dataOfPincode = "";
    ArrayList<AreaWithPincode> pincodeAreasArray = new ArrayList<>();
    ArrayList<String> pincodeAreasStringArray = new ArrayList<>();
    ArrayList<String> purposeStringArray = new ArrayList<>();
    ArrayList<String> reasonStringArray = new ArrayList<>();
    private LoginResponse mLoginDataResponse;
    private CustomLoader loader;
    private String selectedArea, selectedPurpose = "", selectedReason = "";
    private int selectedReasonId = 0;
    private int selectedPurposeId = 0;
    private CreateMeatingResponse mCreateMeatingResponse;
    private double getLattitude, getLongitude;
    private GetLocation mGetLocation;
    private String apiGetArea;
    private File selectedImageFile;
    private boolean isImage;
    private AppPreferences mAppPreferences;
    String deviceId, deviceSerialNum;
    private int selectAreaIndex;
    private int selectPurposeIndex;
    private int selectReasonIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_new_meeting);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            mDropDownDialog = new DropDownDialog(this);
            pincodeAreasStringArray.add("Select Area");
            purposeStringArray.add("Select Purpose");
            reasonStringArray.add("Select Reason");
            findViews();

            new Handler(Looper.getMainLooper()).post(() -> {

                mGetLocation = new GetLocation(this, loader);

                mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                    ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                    ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                });


                CreateMeeting(this);
            });
        });
    }


    private void findViews() {

        txtSelectImage = findViewById(R.id.txtSelectImage);
        image = findViewById(R.id.image);
        txtMobileNo = (EditText) findViewById(R.id.txtMobileNo);
        txtname = (EditText) findViewById(R.id.txtname);
        txtoutletName = (EditText) findViewById(R.id.txtoutletName);
        txtpincode = (EditText) findViewById(R.id.txtpincode);
        area = findViewById(R.id.area);
        areaChooserView = findViewById(R.id.areaChooserView);
        purpose = findViewById(R.id.purpose);
        purposeChooserView = findViewById(R.id.purposeChooserView);
        txtconsumption = (EditText) findViewById(R.id.txtconsumption);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        llRechargeCons = (LinearLayout) findViewById(R.id.ll_rechargeCons);
        txtRechargeCons = (EditText) findViewById(R.id.txtRechargeCons);
        llBillCons = (LinearLayout) findViewById(R.id.ll_billCons);
        txtBillCons = (EditText) findViewById(R.id.txtBillCons);
        llMoneyTxnCons = (LinearLayout) findViewById(R.id.ll_moneyTxnCons);
        txtMoneyTxnCons = (EditText) findViewById(R.id.txtMoneyTxnCons);
        llAepsCons = (LinearLayout) findViewById(R.id.ll_aepsCons);
        txtAepsCons = (EditText) findViewById(R.id.txtAepsCons);
        llMiniAtmCons = (LinearLayout) findViewById(R.id.ll_miniAtmCons);
        txtMiniAtmCons = (EditText) findViewById(R.id.txtMiniAtmCons);
        llInsuranceCons = (LinearLayout) findViewById(R.id.ll_insuranceCons);
        txtInsuranceCons = (EditText) findViewById(R.id.txtInsuranceCons);
        llHotelFlightCons = (LinearLayout) findViewById(R.id.ll_hotelFlightCons);
        txtHotelFlightCons = (EditText) findViewById(R.id.txtHotelFlightCons);
        llPanCardCons = (LinearLayout) findViewById(R.id.ll_panCardCons);
        txtPanCardCons = (EditText) findViewById(R.id.txtPanCardCons);
        llVehicleInsuranceCons = (LinearLayout) findViewById(R.id.ll_vehicleInsuranceCons);
        txtVehicleInsuranceCons = (EditText) findViewById(R.id.txtVehicleInsuranceCons);
        llReason = (LinearLayout) findViewById(R.id.ll_reason);
        reason = findViewById(R.id.reason);
        reasonChooserView = findViewById(R.id.reasonChooserView);
        txtremark = (EditText) findViewById(R.id.txtremark);
        btnPaymentSubmit = (Button) findViewById(R.id.btnPaymentSubmit);
        txtpincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataOfPincode != null && !dataOfPincode.equalsIgnoreCase(s.toString()) && s.toString().trim().length() == 6) {
                    area.setText("Select Area");
                    selectAreaIndex = 0;
                    selectedArea = "Select Area";
                    pincodeAreasArray.clear();
                    PincodeArea();
                }
            }
        });


        areaChooserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownDialog.showDropDownPopup(v, selectAreaIndex, pincodeAreasStringArray, new DropDownDialog.ClickDropDownItem() {
                    @Override
                    public void onClick(int clickPosition, String value, Object object) {
                        selectAreaIndex = clickPosition;
                        area.setText(value + "");
                        selectedArea = value;
                    }
                });
            }
        });


        purposeChooserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownDialog.showDropDownPopup(v, selectPurposeIndex, purposeStringArray, new DropDownDialog.ClickDropDownItem() {
                    @Override
                    public void onClick(int clickPosition, String value, Object object) {
                        selectPurposeIndex = clickPosition;
                        selectedPurpose = value + "";
                        purpose.setText(value + "");
                        if (mCreateMeatingResponse != null && clickPosition != 0) {
                            selectedPurposeId = mCreateMeatingResponse.getData().getPurpuse().get(clickPosition - 1).getPurpuseID();
                        } else {
                            selectedPurposeId = 0;
                        }
                    }
                });
            }
        });

        reasonChooserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownDialog.showDropDownPopup(v, selectReasonIndex, reasonStringArray, new DropDownDialog.ClickDropDownItem() {
                    @Override
                    public void onClick(int clickPosition, String value, Object object) {
                        selectReasonIndex = clickPosition;
                        selectedReason = value + "";
                        reason.setText(value + "");
                        if (mCreateMeatingResponse != null && clickPosition != 0) {
                            selectedReasonId = mCreateMeatingResponse.getData().getResaon().get(clickPosition - 1).getReasonID();
                        } else {
                            selectedReasonId = 0;
                        }
                    }
                });
            }
        });


        txtMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    UserByMobile(NewMeetingActivity.this);
                }
            }
        });

        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                llRechargeCons.setVisibility(View.VISIBLE);
                llBillCons.setVisibility(View.VISIBLE);
                llMiniAtmCons.setVisibility(View.VISIBLE);
                llMoneyTxnCons.setVisibility(View.VISIBLE);
                llAepsCons.setVisibility(View.VISIBLE);
                llPanCardCons.setVisibility(View.VISIBLE);
                llVehicleInsuranceCons.setVisibility(View.VISIBLE);
                llHotelFlightCons.setVisibility(View.VISIBLE);
                llInsuranceCons.setVisibility(View.VISIBLE);
                llReason.setVisibility(View.VISIBLE);
            } else {
                llRechargeCons.setVisibility(View.GONE);
                llBillCons.setVisibility(View.GONE);
                llMiniAtmCons.setVisibility(View.GONE);
                llMoneyTxnCons.setVisibility(View.GONE);
                llAepsCons.setVisibility(View.GONE);
                llPanCardCons.setVisibility(View.GONE);
                llVehicleInsuranceCons.setVisibility(View.GONE);
                llHotelFlightCons.setVisibility(View.GONE);
                llInsuranceCons.setVisibility(View.GONE);
                llReason.setVisibility(View.GONE);
            }
        });

        btnPaymentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        txtSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.choosePicture(true, false);
            }
        });

        imagePicker = new ImagePicker(this, null, new OnImagePickedListener() {
            @Override
            public void onImagePicked(Uri imageUri) {
                Uri imgUri = imageUri;
                selectedImageFile = new File(imgUri.getPath());
                // image.setImageURI(imgUri);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.transform(new RoundedCornersTransformation(NewMeetingActivity.this, (int) getResources().getDimension(R.dimen._5sdp), 0, RoundedCornersTransformation.CornerType.RIGHT));

                Glide.with(NewMeetingActivity.this)
                        .load(selectedImageFile)
                        .apply(requestOptions)
                        .into(image);
                // uploadDocApi(new File(imgUri.getPath()));
            }
        }).setWithImageCrop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }


    void submitForm() {
        if (txtMobileNo.getText().length() == 0) {
            txtMobileNo.setError("Field Can't be empty");
            txtMobileNo.requestFocus();
            return;
        } else if (txtMobileNo.getText().length() != 10) {
            txtMobileNo.setError("Invalid Mobile Number");
            txtMobileNo.requestFocus();
            return;
        } else if (txtname.getText().length() == 0) {
            txtname.setError("Field Can't be empty");
            txtname.requestFocus();
            return;
        } else if (txtoutletName.getText().length() == 0) {
            txtoutletName.setError("Field Can't be empty");
            txtoutletName.requestFocus();
            return;
        } else if (txtpincode.getText().length() == 0) {
            txtpincode.setError("Field Can't be empty");
            txtpincode.requestFocus();
            return;
        } else if (txtpincode.getText().length() != 6) {
            txtpincode.setError("Invalid Pincode");
            txtpincode.requestFocus();
            return;
        } else if (selectAreaIndex == 0) {
            Toast.makeText(this, "Please select area", Toast.LENGTH_SHORT).show();
            return;
        } else if (selectedPurposeId == 0) {
            Toast.makeText(this, "Please select purpose", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtconsumption.getText().length() == 0) {
            txtconsumption.setError("Field Can't be empty");
            txtconsumption.requestFocus();
            return;
        } else if (llRechargeCons.getVisibility() == View.VISIBLE && txtRechargeCons.getText().length() == 0) {
            txtRechargeCons.setError("Field Can't be empty");
            txtRechargeCons.requestFocus();
            return;
        } else if (llBillCons.getVisibility() == View.VISIBLE && txtBillCons.getText().length() == 0) {
            txtBillCons.setError("Field Can't be empty");
            txtBillCons.requestFocus();
            return;
        } else if (llMoneyTxnCons.getVisibility() == View.VISIBLE && txtMoneyTxnCons.getText().length() == 0) {
            txtMoneyTxnCons.setError("Field Can't be empty");
            txtMoneyTxnCons.requestFocus();
            return;
        } else if (llAepsCons.getVisibility() == View.VISIBLE && txtAepsCons.getText().length() == 0) {
            txtAepsCons.setError("Field Can't be empty");
            txtAepsCons.requestFocus();
            return;
        } else if (llMiniAtmCons.getVisibility() == View.VISIBLE && txtMiniAtmCons.getText().length() == 0) {
            txtMiniAtmCons.setError("Field Can't be empty");
            txtMiniAtmCons.requestFocus();
            return;
        } else if (llInsuranceCons.getVisibility() == View.VISIBLE && txtInsuranceCons.getText().length() == 0) {
            txtInsuranceCons.setError("Field Can't be empty");
            txtInsuranceCons.requestFocus();
            return;
        } else if (llHotelFlightCons.getVisibility() == View.VISIBLE && txtHotelFlightCons.getText().length() == 0) {
            txtHotelFlightCons.setError("Field Can't be empty");
            txtHotelFlightCons.requestFocus();
            return;
        } else if (llPanCardCons.getVisibility() == View.VISIBLE && txtPanCardCons.getText().length() == 0) {
            txtPanCardCons.setError("Field Can't be empty");
            txtPanCardCons.requestFocus();
            return;
        } else if (llVehicleInsuranceCons.getVisibility() == View.VISIBLE && txtVehicleInsuranceCons.getText().length() == 0) {
            txtVehicleInsuranceCons.setError("Field Can't be empty");
            txtVehicleInsuranceCons.requestFocus();
            return;
        } else if (llReason.getVisibility() == View.VISIBLE && selectedReasonId == 0) {
            Toast.makeText(this, "Please select reason", Toast.LENGTH_SHORT).show();
            return;
        } /*else if (selectedImageFile == null) {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (getLattitude != 0 && getLongitude != 0) {
            SubmitMeeting(NewMeetingActivity.this);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    getLattitude = lattitude;
                    getLongitude = longitude;
                    SubmitMeeting(NewMeetingActivity.this);
                });
            } else {
                mGetLocation = new GetLocation(NewMeetingActivity.this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    getLattitude = lattitude;
                    getLongitude = longitude;
                    SubmitMeeting(NewMeetingActivity.this);
                });
            }
        }
    }

    void PincodeArea() {
        loader.show();
        ApiFintechUtilMethods.INSTANCE.AreaByPincode(this, txtpincode.getText().toString().trim(), mLoginDataResponse, deviceId, deviceSerialNum, loader, object -> {
            AreaWithPincodeResponse mPincodeAreaResponse = (AreaWithPincodeResponse) object;
            dataOfPincode = txtpincode.getText().toString();
            pincodeAreasArray = mPincodeAreaResponse.getAreas();
            pincodeAreasStringArray.clear();
            pincodeAreasStringArray.add("Select Area");
            for (int i = 0; i < mPincodeAreaResponse.getAreas().size(); i++) {
                pincodeAreasStringArray.add(mPincodeAreaResponse.getAreas().get(i).getArea() + "");

                if (apiGetArea != null && !apiGetArea.isEmpty()) {
                    if (apiGetArea.equalsIgnoreCase(mPincodeAreaResponse.getAreas().get(i).getArea() + "")) {
                        selectAreaIndex = i + 1;
                    }
                }
            }

            /*if (selectAreaIndex != -1) {
                area.setSelection(selectAreaIndex);
            }*/
        });
    }


    public void CreateMeeting(final Activity context) {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<CreateMeatingResponse> call = git.CreateMeeting(new BasicRequest(mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum
                    , mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession())
            );
            call.enqueue(new Callback<CreateMeatingResponse>() {
                @Override
                public void onResponse(Call<CreateMeatingResponse> call, final retrofit2.Response<CreateMeatingResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mCreateMeatingResponse = response.body();
                                if (mCreateMeatingResponse.getStatuscode() == 1) {
                                    if (mCreateMeatingResponse.getData() != null) {
                                        if (mCreateMeatingResponse.getData().getPurpuse() != null && mCreateMeatingResponse.getData().getPurpuse().size() > 0) {
                                            for (CreateMeatingPurpuse purpose : mCreateMeatingResponse.getData().getPurpuse()) {
                                                purposeStringArray.add(purpose.getPurpuseDetail() + "");
                                            }
                                        }

                                        if (mCreateMeatingResponse.getData().getResaon() != null && mCreateMeatingResponse.getData().getResaon().size() > 0) {
                                            for (CreateMeatingResaon reason : mCreateMeatingResponse.getData().getResaon()) {
                                                reasonStringArray.add(reason.getReason() + "");
                                            }
                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Errorok(context, "Data not found.", NewMeetingActivity.this);
                                    }

                                } else {
                                    if (!mCreateMeatingResponse.isVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Errorok(context, mCreateMeatingResponse.getMsg() + "", NewMeetingActivity.this);
                                    }
                                }

                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<CreateMeatingResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {


                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            ApiFintechUtilMethods.INSTANCE.NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void SubmitMeeting(final Activity context) {
        try {
            loader.show();
            if (selectedImageFile != null) {
                isImage = true;
            }
            SubmitMeetingRequest submitMeetingRequest = new SubmitMeetingRequest(isImage, 0, mCreateMeatingResponse != null ? mCreateMeatingResponse.getID : 0,
                    txtname.getText().toString(), txtoutletName.getText().toString(), selectedArea, txtpincode.getText().toString(), selectedPurposeId,
                    selectedPurpose,
                    txtconsumption.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtconsumption.getText().toString()),
                    checkbox.isChecked(),
                    txtRechargeCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtRechargeCons.getText().toString()),
                    txtBillCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtBillCons.getText().toString()),
                    txtMoneyTxnCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtMoneyTxnCons.getText().toString()),
                    txtAepsCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtAepsCons.getText().toString()),
                    txtMiniAtmCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtMiniAtmCons.getText().toString()),
                    txtInsuranceCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtInsuranceCons.getText().toString()),
                    txtHotelFlightCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtHotelFlightCons.getText().toString()),
                    txtPanCardCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtPanCardCons.getText().toString()),
                    txtVehicleInsuranceCons.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(txtVehicleInsuranceCons.getText().toString()),
                    selectedReasonId, selectedReason,
                    txtremark.getText().toString(), 0, txtMobileNo.getText().toString(),
                    getLattitude + "", getLongitude + "",
                    mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum
                    , mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession());
            String req = new Gson().toJson(submitMeetingRequest);
            // Parsing any Media type file
            MultipartBody.Part fileToUpload = null;


            if (selectedImageFile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), selectedImageFile);
                fileToUpload = MultipartBody.Part.createFormData("file", selectedImageFile.getName(), requestBody);
            }

           /* if (selectedImageFile != null) {
                String compressImage=  new ImageCompression(this).compressImage(selectedImageFile.toString());
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), new File(compressImage));
                fileToUpload = MultipartBody.Part.createFormData("file", new File(compressImage).getName() , requestBody);
            }*/

            RequestBody requestStr = RequestBody.create(MediaType.parse("text/plain"), req);

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.PostCreateMeeting(fileToUpload, requestStr);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    selectedImageFile = null;
                                    isImage = false;
                                    image.setImageResource(R.drawable.app_full_logo);
                                    ApiFintechUtilMethods.INSTANCE.Successfulok(response.body().getMsg() + "", NewMeetingActivity.this);


                                } else {
                                    if (!response.body().isVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }

                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {


                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            ApiFintechUtilMethods.INSTANCE.NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UserByMobile(final Activity context) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<UserByMobileResponse> call = git.GetUserByMobile(new GetUserByMobileRequest(txtMobileNo.getText().toString(),
                    mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum
                    , mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession())
            );
            call.enqueue(new Callback<UserByMobileResponse>() {
                @Override
                public void onResponse(Call<UserByMobileResponse> call, final retrofit2.Response<UserByMobileResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                UserByMobileResponse mUserByMobileResponse = response.body();
                                if (mUserByMobileResponse.getStatuscode() == 1) {

                                    if (mUserByMobileResponse.getData() != null) {
                                        if (mUserByMobileResponse.getData().getName() != null && !mUserByMobileResponse.getData().getName().isEmpty()) {
                                            txtname.setText(mUserByMobileResponse.getData().getName());
                                            txtname.setFocusable(false);
                                        } else {
                                            txtname.setFocusableInTouchMode(true);
                                            txtname.setFocusable(true);
                                            txtname.setText("");
                                        }

                                        if (mUserByMobileResponse.getData().getOutletName() != null && !mUserByMobileResponse.getData().getOutletName().isEmpty()) {
                                            txtoutletName.setText(mUserByMobileResponse.getData().getOutletName());
                                            txtoutletName.setFocusable(false);
                                        } else {
                                            txtoutletName.setFocusableInTouchMode(true);
                                            txtoutletName.setFocusable(true);
                                            txtoutletName.setText("");
                                        }
                                        if (mUserByMobileResponse.getData().getPincode() != null && !mUserByMobileResponse.getData().getPincode().isEmpty()) {
                                            txtpincode.setText(mUserByMobileResponse.getData().getPincode());
                                            txtpincode.setFocusable(false);
                                        } else {
                                            txtpincode.setFocusableInTouchMode(true);
                                            txtpincode.setFocusable(true);
                                            txtpincode.setText("");
                                        }

                                        apiGetArea = mUserByMobileResponse.getData().getArea();
                                    }

                                } else {
                                    if (!mCreateMeatingResponse.isVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, mCreateMeatingResponse.getMsg() + "");
                                    }
                                }

                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserByMobileResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {


                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            ApiFintechUtilMethods.INSTANCE.NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mGetLocation != null && (requestCode == mGetLocation.REQUEST_CHECK_ENABLE_SETTINGS
                || requestCode == mGetLocation.REQUEST_CHECK_SETTINGS || requestCode == mGetLocation.INTENT_LOCATION_PERMISSION)) {
            mGetLocation.onActivityResult(requestCode, resultCode, data);
        } else {

            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }

    }


    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
