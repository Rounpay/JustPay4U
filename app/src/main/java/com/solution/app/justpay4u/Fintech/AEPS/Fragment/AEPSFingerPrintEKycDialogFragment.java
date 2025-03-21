package com.solution.app.justpay4u.Fintech.AEPS.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.solution.app.justpay4u.Api.Fintech.Object.PidDataResp;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AEPSFingerPrintEKycDialogFragment extends DialogFragment {


    TextView errorTv, clickView;
    View closeBtn, loaderView;
    LinearLayout mantraView, morphoView, tatvikView, startekView, precisionView, secugenView, evoluteView;
    TextView mantraTv, morphoTv, tatvikTv, startekTv, precisionTv, secugenTv, evoluteTv;
    Activity contextCOB;

    FragmentManager fragmentManagerCOB;
    int oIdCOB, bioAuthTypeCOB;
    String msgCOB;
    String otpRefIDCOB = "0";
    CustomLoader mProgressDialogCOB;
    LoginResponse LoginDataResponseCOB;
    AppPreferences mAppPreferencesCOB;
    String deviceIdCOB;
    String deviceSerialNumCOB;
    ApiFintechUtilMethods.ApiCallBackTwoMethod mApiCallBackCOB;

    private int INTENT_READ_DEVICE = 876;

    private int selectedDevicePos = -1;


    /*public static AEPSFingerPrintEKycActivity newInstance() {
        return new AEPSFingerPrintEKycActivity();
    }*/

    public static boolean NUL(String paramString, PackageManager paramPackageManager) {
        try {
            paramPackageManager.getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
        }
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aeps_finger_print_ekyc_dialog, container, false);

        setCancelable(false);
        clickView = v.findViewById(R.id.clickView);
        loaderView = v.findViewById(R.id.loaderView);

        errorTv = v.findViewById(R.id.errorTv);

        closeBtn = v.findViewById(R.id.closeBtn);
        mantraView = v.findViewById(R.id.mantraView);
        morphoView = v.findViewById(R.id.morphoView);
        tatvikView = v.findViewById(R.id.tatvikView);
        startekView = v.findViewById(R.id.startekView);
        precisionView = v.findViewById(R.id.precisionView);
        secugenView = v.findViewById(R.id.secugenView);
        evoluteView = v.findViewById(R.id.evoluteView);
        mantraTv = v.findViewById(R.id.mantraTv);
        morphoTv = v.findViewById(R.id.morphoTv);
        tatvikTv = v.findViewById(R.id.tatvikTv);
        startekTv = v.findViewById(R.id.startekTv);
        precisionTv = v.findViewById(R.id.precisionTv);
        secugenTv = v.findViewById(R.id.secugenTv);
        evoluteTv = v.findViewById(R.id.evoluteTv);


        closeBtn.setOnClickListener(view -> dismiss());
        clickView.setOnClickListener(view -> fetchDetaill());


        mantraView.setOnClickListener(view -> {
            if (selectedDevicePos != 0) {
                selectedDevicePos = 0;
                errorTv.setVisibility(View.GONE);
                mantraView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);
                evoluteView.setBackgroundResource(0);

                mantraTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));
                evoluteTv.setTextColor(getResources().getColor(R.color.grey));
            }

        });
        morphoView.setOnClickListener(view -> {
            if (selectedDevicePos != 1) {
                selectedDevicePos = 1;
                errorTv.setVisibility(View.GONE);
                morphoView.setBackgroundResource(R.drawable.rounded_primary_border);
                mantraView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);
                evoluteView.setBackgroundResource(0);

                morphoTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));
                evoluteTv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        tatvikView.setOnClickListener(view -> {
            if (selectedDevicePos != 2) {
                selectedDevicePos = 2;
                errorTv.setVisibility(View.GONE);
                tatvikView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);
                evoluteView.setBackgroundResource(0);

                tatvikTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));
                evoluteTv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        startekView.setOnClickListener(view -> {
            if (selectedDevicePos != 3) {
                selectedDevicePos = 3;
                errorTv.setVisibility(View.GONE);
                startekView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);
                evoluteView.setBackgroundResource(0);

                startekTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));
                evoluteTv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        precisionView.setOnClickListener(view -> {
            if (selectedDevicePos != 4) {
                selectedDevicePos = 4;
                errorTv.setVisibility(View.GONE);
                precisionView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                secugenView.setBackgroundResource(0);
                evoluteView.setBackgroundResource(0);

                precisionTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));
                evoluteTv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        secugenView.setOnClickListener(view -> {
            if (selectedDevicePos != 5) {
                selectedDevicePos = 5;
                errorTv.setVisibility(View.GONE);
                secugenView.setBackgroundResource(R.drawable.rounded_primary_border);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);
                evoluteView.setBackgroundResource(0);

                secugenTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
                evoluteTv.setTextColor(getResources().getColor(R.color.grey));
            }
        });

        evoluteView.setOnClickListener(view -> {
            if (selectedDevicePos != 6) {
                selectedDevicePos = 6;
                errorTv.setVisibility(View.GONE);
                evoluteView.setBackgroundResource(R.drawable.rounded_primary_border);
                secugenView.setBackgroundResource(0);
                morphoView.setBackgroundResource(0);
                tatvikView.setBackgroundResource(0);
                startekView.setBackgroundResource(0);
                precisionView.setBackgroundResource(0);
                mantraView.setBackgroundResource(0);

                evoluteTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                secugenTv.setTextColor(getResources().getColor(R.color.grey));
                morphoTv.setTextColor(getResources().getColor(R.color.grey));
                tatvikTv.setTextColor(getResources().getColor(R.color.grey));
                startekTv.setTextColor(getResources().getColor(R.color.grey));
                precisionTv.setTextColor(getResources().getColor(R.color.grey));
                mantraTv.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        if (msgCOB != null && !msgCOB.isEmpty()) {
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText(msgCOB);
        }
        return v;
    }

    void fetchDetaill() {
        if (selectedDevicePos == -1) {
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText("Please select any one device");
            return;
        }
        errorTv.setVisibility(View.GONE);


        if (selectedDevicePos == 0) {
            // mantra();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity",
                    "Mantra", null);
        } else if (selectedDevicePos == 1) {
            // marpho();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            String pidData;
            if (bioAuthTypeCOB == 2) {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";
            } else {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" otp=\"\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";

            }
            readDevice("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity",
                    "Marpho", pidData);
        } else if (selectedDevicePos == 2) {
            // tatvik();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity",
                    "Tatvik", null);
        } else if (selectedDevicePos == 3) {
            // startek();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.acpl.registersdk", "com.acpl.registersdk.MainActivity",
                    "Startek", null);
        } else if (selectedDevicePos == 4) {
            //  precision();
            Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();

            /*clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            String pidData = "<PidOptions ver=\"1.0\"> <Opts env=\"P\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" timeout=\"10000\" /> </PidOptions>";
            readDevice("com.precision.pb510.rdservice", "com.precision.rdservice.CaptureActivity",
                    "Precision", pidData);*/
        } else if (selectedDevicePos == 5) {
            // secugen();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.secugen.rdservice", "com.secugen.rdservice.Capture", "Secugen", null);
        } else if (selectedDevicePos == 6) {
            // evolute();
            clickView.setVisibility(View.GONE);
            loaderView.setVisibility(View.VISIBLE);
            readDevice("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity", "Evolute", null);
        }


    }

    private void readDevice(String packageName, String serviceName, String name, String piddata) {
        errorTv.setVisibility(View.GONE);
        if (NUL(packageName, getActivity().getPackageManager())) {
            String pidData;
            if (bioAuthTypeCOB == 2) {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";

            } else {
                pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";

            }
            if (piddata != null && !piddata.isEmpty()) {
                pidData = piddata;
            }
            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName(packageName, serviceName));
            localIntent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            localIntent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(localIntent, INTENT_READ_DEVICE);

        } else {
            clickView.setVisibility(View.VISIBLE);
            loaderView.setVisibility(View.GONE);
            openServiceOnPlay(name, packageName);
        }
    }

    void openServiceOnPlay(String name, String packageName) {

        new AlertDialog.Builder(getActivity())
                .setTitle("Get Service")
                .setMessage(name + " RD Services Not Found.Click OK to Download Now.")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));

                    } catch (Exception localException) {
                        errorTv.setVisibility(View.VISIBLE);
                        errorTv.setText("Something went wrong. Please try again later.");
                        localException.printStackTrace();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_READ_DEVICE) {
            clickView.setVisibility(View.VISIBLE);
            loaderView.setVisibility(View.GONE);
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    errorTv.setVisibility(View.GONE);
                    String pidData = data.getStringExtra("PID_DATA");
                    pidData(pidData);
                } else {
                    errorTv.setVisibility(View.VISIBLE);
                    errorTv.setText("Didn't receive any data");
                }
            } else {
                errorTv.setVisibility(View.VISIBLE);
                errorTv.setText("Canceled");
            }
        }
    }

    public void pidData(String paramString) {

        PidDataResp mResp = new PidDataResp();

        try {
            if (paramString != null && !paramString.isEmpty() && paramString.contains("<PidData>")) {
                DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
                Document localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(paramString.getBytes("UTF-8")));
                NodeList localNodeList1 = localDocument.getElementsByTagName("Resp");
                Element localElement2 = (Element) localNodeList1.item(0);
                mResp.setErrCode(localElement2.getAttribute("errCode"));
                mResp.setErrInfo(localElement2.getAttribute("errInfo"));

                if (mResp.getErrCode().equalsIgnoreCase("0") && paramString.contains("<Hmac>")) {
                    // NUL(paramRelativeLayout, "Finger Captured Successfully!", getResources().getColor(R.color.green));
                    Toast.makeText(getActivity(), "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    if (mProgressDialogCOB != null) {
                        mProgressDialogCOB.show();
                    }
                    ApiFintechUtilMethods.INSTANCE.CallOnboarding(contextCOB, bioAuthTypeCOB, true, fragmentManagerCOB, oIdCOB, "",
                            otpRefIDCOB, paramString, false, false, false, null,
                            null, null, mProgressDialogCOB, LoginDataResponseCOB, mAppPreferencesCOB,
                            deviceIdCOB, deviceSerialNumCOB, mApiCallBackCOB);
                } else {
                    errorTv.setVisibility(View.VISIBLE);
                    errorTv.setText("Error Code : " + mResp.getErrCode() + "\n" +
                            "Error Message : " + mResp.getErrInfo() + "");
                }
            } else {
                errorTv.setVisibility(View.VISIBLE);
                errorTv.setText("Didn't receive any data");
            }
        } catch (Exception e) {
            errorTv.setVisibility(View.VISIBLE);
            errorTv.setText(e.getMessage() + "");
            e.printStackTrace();
        }

    }

    void NUL(String s) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Mini ATM Response")
                .setMessage(s)
                /* .setPositiveButton(android.R.string.yes, (dialog, which) -> finish())*/
                .setNegativeButton(android.R.string.yes, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }

    public void NUL(View paramView, String paramString, int paramInt) {
        Snackbar localSnackbar = Snackbar.make(paramView, "" + paramString, Snackbar.LENGTH_LONG);
        View snackBarView = localSnackbar.getView();
        snackBarView.setBackgroundColor(paramInt);
        TextView mainTextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setMaxLines(5);
        TextViewCompat.setTextAppearance(mainTextView, R.style.TextAppearance_AppCompat_Body2);
        localSnackbar.show();

    }

    private String NUL(String paramString, Element paramElement) {
        NodeList localNodeList = paramElement.getElementsByTagName(paramString).item(0).getChildNodes();
        Node localNode = localNodeList.item(0);
        return localNode.getNodeValue();
    }

    public void setOnBoardingData(final Activity context, String msg, FragmentManager fragmentManager, int oid, int bioAuthType,
                                  String otpRefID, CustomLoader mProgressDialog,
                                  LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                                  String deviceId, String deviceSerialNum, ApiFintechUtilMethods.ApiCallBackTwoMethod mApiCallBack) {

        contextCOB = context;
        msgCOB = msg;
        fragmentManagerCOB = fragmentManager;
        oIdCOB = oid;
        bioAuthTypeCOB = bioAuthType;
        otpRefIDCOB = otpRefID;
        mProgressDialogCOB = mProgressDialog;
        LoginDataResponseCOB = LoginDataResponse;
        mAppPreferencesCOB = mAppPreferences;
        deviceIdCOB = deviceId;
        deviceSerialNumCOB = deviceSerialNum;
        mApiCallBackCOB = mApiCallBack;

    }
}
