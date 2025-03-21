package com.solution.app.justpay4u.Shopping.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.Api.Shopping.Object.PincodeArea;
import com.solution.app.justpay4u.Api.Shopping.Response.PincodeAreaResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.StatesCitiesResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;

import java.util.ArrayList;

public class AddAddressShoppingActivity extends AppCompatActivity {


    private final ArrayList<DropDownModel> arrayListArea = new ArrayList<>();
    RadioGroup radioGroup;
    String selectArea;
    private EditText name;
    private EditText mobile;
    private TextView state;
    private TextView area;
    private TextView city;
    private EditText pincode;
    private EditText landmark;
    private EditText address;
    private Button btnSubmit;
    private CustomLoader loader;
    private StatesCitiesResponse mStatesResponse, mCitiesResponse;
    private CustomAlertDialog mCustomAlertDialog;
    private int selectedStateId;
    private int selectedCityId, selectedAreaId;
    private String addressType = "Home";
    private String dataOfPincode = "";
    private int selectedAreaPos = -1;
    private DropDownDialog mDropDownDialog;
    private Address mAddress;
    private int addresId;
    private boolean isDefault = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_add_address);
            mCustomAlertDialog = new CustomAlertDialog(this);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mDropDownDialog = new DropDownDialog(this);
            mAddress = (Address) getIntent().getSerializableExtra("Address");
            findViews();
        });

    }

    private void findViews() {

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        View areaView = findViewById(R.id.areaView);
        state = findViewById(R.id.state);
        area = findViewById(R.id.area);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        landmark = findViewById(R.id.landmark);
        address = findViewById(R.id.address);
        radioGroup = findViewById(R.id.radioGroup);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> submitAddress());

        areaView.setOnClickListener(v -> {
            clickAreaView(v);
        });
        pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataOfPincode != null && !dataOfPincode.equalsIgnoreCase(s.toString()) && s.toString().trim().length() == 6) {
                    area.setText("");
                    city.setText("");
                    state.setText("");
                    arrayListArea.clear();
                    getArea();
                }
            }
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.homeRadio) {
                addressType = "HOME";
            }
            if (checkedId == R.id.officeRadio) {
                addressType = "OFFICE";
            }
            if (checkedId == R.id.otherRadio) {
                addressType = "OTHER";
            }
        });

        if (mAddress != null) {
            setAddressUi();
        }
    }

    void setAddressUi() {
        btnSubmit.setText("Update Address");
        name.setText(mAddress.getCustomerName() + "");
        mobile.setText(mAddress.getMobileNo() + "");
        pincode.setText(mAddress.getPinCode() + "");
        state.setText(mAddress.getState() + "");
        city.setText(mAddress.getCity() + "");
        landmark.setText(mAddress.getLandmark() + "");
        address.setText(mAddress.getAddressOnly() + "");
        area.setText(mAddress.getArea() + "");
        selectedCityId = mAddress.getCityID();
        selectedStateId = mAddress.getStateID();
        selectedAreaId = mAddress.getAreaID();
        addresId = mAddress.getId();
        isDefault = mAddress.isDefault();
        selectArea = mAddress.getArea();
        if (mAddress.getTitle().toLowerCase().equalsIgnoreCase("office")) {
            radioGroup.check(R.id.officeRadio);
        } else if (mAddress.getTitle().toLowerCase().equalsIgnoreCase("home")) {
            radioGroup.check(R.id.homeRadio);
        } else {
            radioGroup.check(R.id.otherRadio);
        }
    }

    void clickAreaView(View v) {
        pincode.setError(null);
        if (TextUtils.isEmpty(pincode.getText())) {
            pincode.setError("Please Enter Valid Pincode");
            pincode.requestFocus();
            return;
        } else if (pincode.getText().toString().replaceAll(" ", "").length() != 6) {
            pincode.setError("Please Enter 6 Digit Pincode");
            pincode.requestFocus();
            return;
        } else if (arrayListArea == null || arrayListArea.size() == 0) {

            pincode.setError("Please Enter Valid Pincode");
            pincode.requestFocus();
            return;
        }

        mDropDownDialog.showDropDownShopAreaPopup(v, selectedAreaPos, arrayListArea, (clickPosition, value, object) -> {

                    if (selectedAreaPos != clickPosition) {
                        PincodeArea mPincodeArea = (PincodeArea) object;


                        area.setText(mPincodeArea.getArea() + "");
                        city.setText(mPincodeArea.getCity() + "");
                        state.setText(mPincodeArea.getStatename() + "");

                        selectedAreaPos = clickPosition;
                        selectArea = mPincodeArea.getArea() + "";
                        selectedAreaId = mPincodeArea.getId();
                        selectedCityId = mPincodeArea.getCityID();
                        selectedStateId = mPincodeArea.getStateID();
                    }
                }
        );
    }


    void getStatesList(Activity mContext, boolean isFromClick) {
        if (loader != null && isFromClick) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.getStatesList(mContext, loader, isFromClick, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {

                mStatesResponse = (StatesCitiesResponse) object;
                if (mStatesResponse != null && mStatesResponse.getStatesCities() != null &&
                        mStatesResponse.getStatesCities().size() > 0) {
                    if (isFromClick) {
                        mCustomAlertDialog.openStateCitiesListDialog(mContext, mStatesResponse.getStatesCities(), true, mItem -> {
                            state.setText(mItem.getStateName() + "");
                            selectedStateId = mItem.getStateID();
                            state.setError(null);
                            getCityList(mContext, false);
                        });
                    }
                } else {
                    if (isFromClick) {
                        ApiShoppingUtilMethods.INSTANCE.ErrorWithTitle(mContext, "Sorry", "States not available please try after some time");
                    }
                }
            }

            @Override
            public void onError(int error) {

            }
        });

    }


    void getArea() {
        if (loader != null) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.GetPincodeAreaRequest(this, pincode.getText().toString().trim(), loader, object -> {
            PincodeAreaResponse mPincodeAreaResponse = (PincodeAreaResponse) object;
            dataOfPincode = pincode.getText().toString();

            for (int i = 0; i < mPincodeAreaResponse.getAreas().size(); i++) {
                if (area.getText().toString().trim().equalsIgnoreCase(mPincodeAreaResponse.getAreas().get(i).getArea() + "")) {
                    selectedAreaPos = i;
                    selectArea = mPincodeAreaResponse.getAreas().get(i).getArea() + "";
                    selectedAreaId = mPincodeAreaResponse.getAreas().get(i).getId();
                    selectedCityId = mPincodeAreaResponse.getAreas().get(i).getCityID();
                    selectedStateId = mPincodeAreaResponse.getAreas().get(i).getStateID();
                }
                arrayListArea.add(new DropDownModel(mPincodeAreaResponse.getAreas().get(i).getArea() + "", mPincodeAreaResponse.getAreas().get(i)));
            }
        });
    }

    void getCityList(Activity mContext, boolean isFromClick) {
        if (loader != null) {
            loader.show();
        }
        mCitiesResponse = null;
        ApiShoppingUtilMethods.INSTANCE.getCitiesList(mContext, loader, selectedStateId, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {

                mCitiesResponse = (StatesCitiesResponse) object;
                if (mCitiesResponse != null && mCitiesResponse.getStatesCities() != null &&
                        mCitiesResponse.getStatesCities().size() > 0) {
                    if (isFromClick) {
                        mCustomAlertDialog.openStateCitiesListDialog(mContext, mCitiesResponse.getStatesCities(), false, mItem -> {
                            city.setText(mItem.getCityName() + "");
                            selectedCityId = mItem.getCityID();
                            city.setError(null);
                        });
                    }
                } else {

                    ApiShoppingUtilMethods.INSTANCE.ErrorWithTitle(mContext, "Sorry", "Cities not available please try after some time");

                }
            }

            @Override
            public void onError(int error) {

            }
        });

    }


    void submitAddress() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Please enter customer name first.");
            name.requestFocus();
            return;
        } else if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please enter mobile no first.");
            mobile.requestFocus();
            return;
        } else if (mobile.getText().toString().length() != 10) {
            mobile.setError("Please enter valid mobile no.");
            mobile.requestFocus();
            return;
        } else if (pincode.getText().toString().isEmpty()) {
            pincode.setError("Please enter pincode first.");
            pincode.requestFocus();
            return;
        } else if (pincode.getText().toString().length() != 6) {
            pincode.setError("Please enter valid pincode.");
            pincode.requestFocus();
            return;
        } else if (area.getText().toString().isEmpty()) {
            area.setError("Please select area first.");
            area.requestFocus();
            return;
        } else if (city.getText().toString().isEmpty()) {
            city.setError("Please select valid area to city.");
            city.requestFocus();
            return;
        } else if (state.getText().toString().isEmpty()) {
            state.setError("Please select valid area to state.");
            state.requestFocus();
            return;
        } else if (landmark.getText().toString().isEmpty()) {
            landmark.setError("Please enter landmark first.");
            landmark.requestFocus();
            return;
        } else if (address.getText().toString().isEmpty()) {
            address.setError("Please enter address first.");
            address.requestFocus();
            return;
        } else if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select address type first.", Toast.LENGTH_SHORT).show();
            return;
        }

        addAddress(this);
    }


    void addAddress(Activity mContext) {
        if (loader != null) {
            loader.show();
        }

        ApiShoppingUtilMethods.INSTANCE.AddAddress(mContext, false, isDefault, addresId, addressType, name.getText().toString(), mobile.getText().toString(),
                address.getText().toString(), selectedCityId, selectedStateId, selectArea, pincode.getText().toString(), landmark.getText().toString(),
                loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        setResult(RESULT_OK);

                    }

                    @Override
                    public void onError(int error) {

                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
