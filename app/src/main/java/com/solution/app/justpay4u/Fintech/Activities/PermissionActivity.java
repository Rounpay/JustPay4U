package com.solution.app.justpay4u.Fintech.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.solution.app.justpay4u.R;

public class PermissionActivity extends AppCompatActivity {

    private View iconNotification,notificationLabel,notificationDescription,switchNotificationView;
    private SwitchCompat switchNotification;
    private LinearLayout switchCameraView;
    private SwitchCompat switchCamera;
    private LinearLayout switchLocationView;
    private SwitchCompat switchLocation;
    private LinearLayout switchStorageView;
    private SwitchCompat switchStorage;
    private LinearLayout switchPhoneView;
    private SwitchCompat switchPhone;
    private AppCompatTextView nextBtn;
    boolean isNextClicable;

    private static final int REQUEST_PERMISSIONS = 123;

    private boolean isSettingClick;
    private Dialog alertDialogWarning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_permission);
            findViews();
        });


    }


    private void findViews() {
        iconNotification = findViewById(R.id.iconNotification);
        notificationLabel = findViewById(R.id.notificationLabel);
        notificationDescription = findViewById(R.id.notificationDescription);
        switchNotificationView = findViewById(R.id.switchNotificationView);
        switchNotification = findViewById(R.id.switchNotification);
        switchCameraView = findViewById(R.id.switchCameraView);
        switchCamera = findViewById(R.id.switchCamera);
        switchLocationView = findViewById(R.id.switchLocationView);
        switchLocation = findViewById(R.id.switchLocation);
        switchStorageView = findViewById(R.id.switchStorageView);
        switchStorage = findViewById(R.id.switchStorage);
        switchPhoneView = findViewById(R.id.switchPhoneView);
        switchPhone = findViewById(R.id.switchPhone);
        nextBtn = findViewById(R.id.nextBtn);
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            iconNotification.setVisibility(View.VISIBLE);
            notificationLabel.setVisibility(View.VISIBLE);
            notificationDescription.setVisibility(View.VISIBLE);
            switchNotificationView.setVisibility(View.VISIBLE);
        }else {
            iconNotification.setVisibility(View.GONE);
            notificationLabel.setVisibility(View.GONE);
            notificationDescription.setVisibility(View.GONE);
            switchNotificationView.setVisibility(View.GONE);
        }
        nextBtn.setOnClickListener(v -> {
            if (isNextClicable) {
                setResult(RESULT_OK);
                finish();
            }
        });

        setUiData();
    }


    void setUiData() {
        int count = 0;
        isNextClicable = false;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            switchPhone.setChecked(false);
            switchPhoneView.setOnClickListener(v ->
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSIONS));
                    /*requestPermission(Manifest.permission.READ_PHONE_STATE, "Phone Permission",
                    getString(R.string.phone_permission_msg), R.drawable.ic_smartphone_col, R.color.color_accent));*/
        } else {
            switchPhone.setChecked(true);
            switchPhoneView.setClickable(false);
            count++;
        }


       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            switchContact.setChecked(false);
            switchContactView.setOnClickListener(v ->
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_PERMISSIONS));
                   *//* requestPermission(Manifest.permission.READ_CONTACTS, "Contact Permission",
                    getString(R.string.contact_permission_msg), R.drawable.ic_contact_col, R.color.color_amber));*//*
        } else {
            switchContact.setChecked(true);
            switchContactView.setClickable(false);
            count++;
        }*/

        if (android.os.Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                switchStorage.setChecked(false);
                switchStorageView.setOnClickListener(v ->
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSIONS));
                    /*requestPermission(Manifest.permission.READ_MEDIA_IMAGES , "Storage Permission",
                    getString(R.string.storage_permission_msg), R.drawable.ic_storage_col, R.color.lightDarkGreen));*/
            } else {
                switchStorage.setChecked(true);
                switchStorageView.setClickable(false);
                count++;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                switchStorage.setChecked(false);
                switchStorageView.setOnClickListener(v ->
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS));
                    /*requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Storage Permission",
                    getString(R.string.storage_permission_msg), R.drawable.ic_storage_col, R.color.lightDarkGreen));*/
            } else {
                switchStorage.setChecked(true);
                switchStorageView.setClickable(false);
                count++;
            }
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            switchCamera.setChecked(false);
            switchCameraView.setOnClickListener(v ->
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS));
                    /*requestPermission(Manifest.permission.CAMERA, "Camera Permission",
                    getString(R.string.camera_permission_msg), R.drawable.ic_camera_col, R.color.color_indigo));*/
        } else {
            switchCamera.setChecked(true);
            switchCameraView.setClickable(false);
            count++;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            switchLocation.setChecked(false);
            switchLocationView.setOnClickListener(v ->
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS));
                    /*requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, "Location Permission",
                    getString(R.string.location_permission_msg), R.drawable.ic_location_col, R.color.color_orange));*/
        } else {
            switchLocation.setChecked(true);
            switchLocationView.setClickable(false);
            count++;
        }

        if (android.os.Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                switchNotification.setChecked(false);
                switchNotificationView.setOnClickListener(v ->
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_PERMISSIONS));
                    /*requestPermission(Manifest.permission.READ_MEDIA_IMAGES , "Storage Permission",
                    getString(R.string.storage_permission_msg), R.drawable.ic_storage_col, R.color.lightDarkGreen));*/
            } else {
                switchNotification.setChecked(true);
                switchNotificationView.setClickable(false);
                count++;
            }
        }
        if (android.os.Build.VERSION.SDK_INT >= 33?count == 5:count == 4) {
            isNextClicable = true;
            nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

    }

  /*  private void requestPermission(String permission, String title, String msg, int iconSource, int colorBg) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showWarningDialog(title, msg, iconSource, colorBg);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_PERMISSIONS);
        }
    }*/

   /* private void requestPermission(String permission1, String permission2, String title, String msg, int iconSource, int colorBg) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission1)) {
            showWarningDialog(title, msg, iconSource, colorBg);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission2)) {
            showWarningDialog(title, msg, iconSource, colorBg);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission1, permission2}, REQUEST_PERMISSIONS);
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {

            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            for (int permission : grantResults) {
                permissionCheck = permissionCheck + permission;
            }
            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

                setUiData();
            } else {
                showDialogOnCancel(permissions);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void showDialogOnCancel(String[] permissions) {
        if (permissions.length > 0) {
            if (permissions[0].contains("PHONE")) {
                showWarningDialog("Phone Permission", getString(R.string.phone_permission_msg), R.drawable.ic_smartphone_col, R.color.color_accent);
            } else if (permissions[0].contains("CAMERA")) {
                showWarningDialog("Camera Permission", getString(R.string.camera_permission_msg), R.drawable.ic_camera_col, R.color.color_indigo);
            } else if (permissions[0].contains("LOCATION")) {
                showWarningDialog("Location Permission", getString(R.string.location_permission_msg), R.drawable.ic_location_col, R.color.color_orange);
            } /*else if (permissions[0].contains("CONTACTS")) {
                showWarningDialog("Contact Permission", getString(R.string.contact_permission_msg), R.drawable.ic_contact_col, R.color.color_amber);
            }*/ else if (permissions[0].contains("STORAGE")) {
                showWarningDialog("Storage Permission", getString(R.string.storage_permission_msg), R.drawable.ic_storage_col, R.color.lightDarkGreen);
            }else if (android.os.Build.VERSION.SDK_INT >= 33 && permissions[0].contains("NOTIFICATIONS")) {
                showWarningDialog("Notification Permission", getString(R.string.notification_permission_msg), R.drawable.ic_notifications, R.color.color_teal);
            }
        }
    }


    void showWarningDialog(String title, String msg, int iconSource, int colorBg) {


        try {
            if (alertDialogWarning != null && alertDialogWarning.isShowing()) {
                return;
            }
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_permission_grant, null);

            ImageView iconIv = dialogView.findViewById(R.id.icon);
            TextView msgTv = dialogView.findViewById(R.id.msg);
            TextView setting = dialogView.findViewById(R.id.setting);
            TextView skip = dialogView.findViewById(R.id.skip);
            TextView titleTv = dialogView.findViewById(R.id.title);

            alertDialogWarning = new Dialog(this, R.style.alert_dialog_light);
            alertDialogWarning.setCancelable(false);
            alertDialogWarning.setContentView(dialogView);
            alertDialogWarning.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


            iconIv.setImageResource(iconSource);
            ViewCompat.setBackgroundTintList(iconIv, ColorStateList.valueOf(colorBg));
            msgTv.setText(msg);
            titleTv.setText(title);
            //   iconTv.setImageResource(ServiceIcon.INSTANCE.parentIcon(parentId));

            setting.setOnClickListener(v -> {
                isSettingClick = true;
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                alertDialogWarning.dismiss();
            });
            skip.setOnClickListener(v -> alertDialogWarning.dismiss());


            alertDialogWarning.show();


        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isSettingClick) {
            isSettingClick = false;
            setUiData();
        }
    }


    @Override
    public void onBackPressed() {
        if (isNextClicable) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}
