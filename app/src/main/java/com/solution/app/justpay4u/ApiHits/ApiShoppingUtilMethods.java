package com.solution.app.justpay4u.ApiHits;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.AddShippingAddressRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.AddToCartRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.AllProductListRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.ChangeQuantityRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.CitiesRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.OrderDetailRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.PincodeAreaRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.RemoveFromCartRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.WishListAddRemoveRequest;
import com.solution.app.justpay4u.Api.Shopping.Response.AddToCartResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.AddressListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.AllProductListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.BasicApiResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.CartDetailResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.CheckDeliveryResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.DashbaordInfoResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.GetAllFilterResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.MainCategoryWiseProductResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.NewArrivalAndSaleDataResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.OrderListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.PincodeAreaResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.ProductInfoDetailsResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.PromotionPopupResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.StatesCitiesResponse;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Vishnu on 24-08-2017.
 */

public enum ApiShoppingUtilMethods {

    INSTANCE;

    public static int mLoginTypeID;
    public static String mSessionID;
    public static int mWebsiteID;
    public static String mSession;
    public static String mUserID;
    public static String mMobileNumber;
    public static String mDeviceId, mDeviceSerialNum;


    public ArrayList<Integer> cartProductIdList = new ArrayList<>();
    public int ERRORR_NETWORK = 1;
    public int ERRORR_NO_DATA = 2;
    AlertDialog alertDialogVersion;
    int ERRORR_OTHER_ERROR = 3;
    private AppPreferences mAppPrefrences;

    public void setCartCount(Activity context, int count) {
        if (mAppPrefrences == null) {
            mAppPrefrences = new AppPreferences(context);
        }

        mAppPrefrences.set(ApplicationConstant.INSTANCE.cartCount, count);

    }

    public int getCartCount(Activity context) {
        if (mAppPrefrences == null) {
            mAppPrefrences = new AppPreferences(context);
        }
        return mAppPrefrences.getInt(ApplicationConstant.INSTANCE.cartCount);
    }

    public void setRecentSearch(Activity context, String data) {
        if (mAppPrefrences == null) {
            mAppPrefrences = new AppPreferences(context);
        }
        mAppPrefrences.set(ApplicationConstant.INSTANCE.recentSearch, data);

    }

    public String getRecentSearch(Activity context) {
        if (mAppPrefrences == null) {
            mAppPrefrences = new AppPreferences(context);
        }
        return mAppPrefrences.getString(ApplicationConstant.INSTANCE.recentSearch);
    }

    public String formatedDateOfSlash(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            String formateDate = null;
            SimpleDateFormat inputFormat;

            inputFormat = new SimpleDateFormat("dd/MM/yyyy");


            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            try {
                Date date = inputFormat.parse(dateStr);
                formateDate = outputFormat.format(date);
                return formateDate;
            } catch (ParseException e) {
                e.printStackTrace();
                return dateStr;
            }


        }
        return dateStr;
    }

    public String getAmountFormat(String amount) {
        StringBuilder strBind = new StringBuilder(amount);
        strBind.append(".00");
        return strBind.toString();
    }


    public void NetworkError(final Activity context, String title, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCustomImage(R.drawable.ic_connection_lost_24dp)
                .show();
    }

    public void NetworkError(final Activity context) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Network Error!")
                .setContentText("Slow or No Internet Connection.")
                .setCustomImage(R.drawable.ic_connection_lost_24dp)
                .show();
    }

    public void Processing(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Warning(message);
    }

    public void ProcessingWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Warning(title, message);
    }

    public void Failed(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Failed(message);
    }

    public void Successful(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Successful(message);
    }

    public void SuccessfulWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.SuccessfulWithTitle(title, message);
    }

    public void SuccessfulWithFinsh(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.SuccessfulWithFinsh(message);
    }

    public void Successfulok(final String message, Activity activity) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity);
        customAlertDialog.Successfulok(message, activity);
    }

    public void SuccessfulWithFinish(final String message, Activity activity, boolean isCancelable) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity);
        customAlertDialog.SuccessfulWithFinsh(message, isCancelable);
    }

    public void Errorok(final Activity context, final String message, Activity activity) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Errorok(message, activity);
    }

    public void Error(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Error(message);
    }

    public void ErrorWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.ErrorWithTitle(title, message);
    }

    public void Alert(final Activity context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setContentText(message)
                .setCustomImage(R.drawable.ic_done_black_24dp)
                .show();
    }

    public boolean isNetworkAvialable(Activity context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean isValidMobile(String mobile) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String mobilePattern = "^(?:0091|\\\\+91|0)[7-9][0-9]{9}$";
        String mobileSecPattern = "[7-9][0-9]{9}$";

        return mobile.matches(mobilePattern) || mobile.matches(mobileSecPattern);
    }

    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public String formatedDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat;
            if (dateStr.length() == 19) {
                inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            } else {
                inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            }

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                Date date = inputFormat.parse(dateStr);
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return dateStr.replace("T", " ");
            }
        }
        return "";
    }

    public String formatedDate2(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                Date date = inputFormat.parse(dateStr);
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return dateStr;
            }
        }
        return "";
    }

    public String formatedAmountWithRupees(String value) {

        if (value != null && !value.isEmpty()) {
            if (value.contains(".")) {
                String postfixValue = value.substring(value.indexOf("."));
                if (postfixValue.equalsIgnoreCase(".0")) {
                    return "\u20B9 " + value.replace(".0", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".00")) {
                    return "\u20B9 " + value.replace(".00", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".000")) {
                    return "\u20B9 " + value.replace(".000", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".0000")) {
                    return "\u20B9 " + value.replace(".0000", "").trim();
                } else {
                    try {
                        return "\u20B9 " + String.format("%.2f", Double.parseDouble(value.trim()));
                    } catch (NumberFormatException nfe) {
                        return "\u20B9 " + value.trim();
                    }
                }
            } else {
                return "\u20B9 " + value.trim();
            }

        } else {
            return "\u20B9 0";
        }
    }

    public String formatedAmount(String value) {
        if (value != null && !value.isEmpty()) {
            if (value.contains(".")) {
                String postfixValue = value.substring(value.indexOf("."));
                if (postfixValue.equalsIgnoreCase(".0")) {
                    return value.replace(".0", "");
                } else if (postfixValue.equalsIgnoreCase(".00")) {
                    return value.replace(".00", "");
                } else if (postfixValue.equalsIgnoreCase(".000")) {
                    return value.replace(".000", "");
                } else if (postfixValue.equalsIgnoreCase(".0000")) {
                    return value.replace(".0000", "");
                } else {
                    try {
                        return String.format("%.2f", Double.parseDouble(value.trim()));
                    } catch (NumberFormatException nfe) {
                        return value.trim();
                    }
                }
            } else {
                return value.trim();
            }

        } else {
            return "0";
        }
    }


    /*public void openImageDialog(final Context context, String imageurl) {

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true);


        Glide.with(context)
                .asBitmap()
                .load(ApplicationConstant.INSTANCE.baseUrl + imageurl)
                .apply(requestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                        if (resource != null) {


                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View dialogLayout = inflater.inflate(R.layout.image_dialog_layout_shopping, null);
                            final ImageView dialogImage = dialogLayout.findViewById(R.id.dialogImage);
                            final ImageView btn_ok = dialogLayout.findViewById(R.id.btn_ok);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            alertDialog = builder.create();
                            alertDialog.setView(dialogLayout);
                            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });


                            alertDialog.show();
                            dialogImage.setImageBitmap(resource);

                            ////////////////////////////////////////////////////////

                        } else {
                            alertDialog.dismiss();
                        }


                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


    }*/

    public void versionDialog(final Context mContext) {
        if (alertDialogVersion != null && alertDialogVersion.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext); // create an alert box
        builder.setTitle("Alert!!");
        builder.setMessage("New Update Available.");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        alertDialogVersion = builder.create();
        alertDialogVersion.show();
        alertDialogVersion.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVersionUpdate(mContext);
            }
        });
    }

    private void goToVersionUpdate(Context mContext) {

        try {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
        } catch (android.content.ActivityNotFoundException anfe) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" +
                            BuildConfig.APPLICATION_ID)));
        }
        // finish();
    }

    protected void makeLinkClickable(final Activity context, SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(span.getURL()))
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                } catch (android.content.ActivityNotFoundException anfe) {

                }
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    public void setTextViewHTML(Activity context, TextView text, String html) {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(context, strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void displayingOnFailuireMessage(Activity context, String failuireMssg) {
        if (failuireMssg != null && failuireMssg.contains("No address associated with hostname")) {
            Error(context, context.getResources().getString(R.string.network_error));
        } else {
            Error(context, failuireMssg);
        }
    }


    public void GetDashboardInfo(final Activity context, final ApiHitCallBack mApiHitCallBack) {


        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<DashbaordInfoResponse> call = git.getWebsiteInfo(ApplicationConstant.INSTANCE.baseUrl,
                    mUserID, mWebsiteID + "");
            call.enqueue(new Callback<DashbaordInfoResponse>() {
                @Override
                public void onResponse(Call<DashbaordInfoResponse> call, final retrofit2.Response<DashbaordInfoResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            DashbaordInfoResponse data = response.body();
                            if (data != null && data.getStatus() && data.getDashbaordInfoData() != null) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DashbaordInfoResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void GetNewArrivalOnSale(final Activity context, final ApiHitCallBack mApiHitCallBack) {


        try {


            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<NewArrivalAndSaleDataResponse> call = git.getNewArrivalOnSaleApi(mWebsiteID + "", mUserID);
            call.enqueue(new Callback<NewArrivalAndSaleDataResponse>() {
                @Override
                public void onResponse(Call<NewArrivalAndSaleDataResponse> call, final retrofit2.Response<NewArrivalAndSaleDataResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            NewArrivalAndSaleDataResponse data = response.body();
                            if (data != null && data.getStatus() && data.getNewArrivalAndSaleData() != null) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<NewArrivalAndSaleDataResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void GetMainCategoryWiseProducts(final Activity context, int mainCatId, int catId, final ApiHitCallBack mApiHitCallBack) {


        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<MainCategoryWiseProductResponse> call = git.getMainCategoriesList(mUserID, mWebsiteID + "", mainCatId, catId);
            call.enqueue(new Callback<MainCategoryWiseProductResponse>() {
                @Override
                public void onResponse(Call<MainCategoryWiseProductResponse> call, final retrofit2.Response<MainCategoryWiseProductResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            MainCategoryWiseProductResponse data = response.body();

                            if (data != null && data.getStatus()) {

                                if (data.getMainCategoryWiseProductData() != null && data.getMainCategoryWiseProductData().size() > 0) {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onSucess(data);
                                    }
                                } else {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                    }
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MainCategoryWiseProductResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void GetMultiMainCategoryWiseProducts(final Activity context, String mainCatId, int catId, final ApiHitCallBack mApiHitCallBack) {


        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<MainCategoryWiseProductResponse> call = git.getMultiMaincategoriesList(mUserID, mWebsiteID + "", mainCatId, catId);
            call.enqueue(new Callback<MainCategoryWiseProductResponse>() {
                @Override
                public void onResponse(Call<MainCategoryWiseProductResponse> call, final retrofit2.Response<MainCategoryWiseProductResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            MainCategoryWiseProductResponse data = response.body();

                            if (data != null && data.getStatus()) {

                                if (data.getMainCategoryWiseProductData() != null && data.getMainCategoryWiseProductData().size() > 0) {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onSucess(data);
                                    }
                                } else {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                    }
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MainCategoryWiseProductResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void GetProductInfo(final Activity context, String posId, final ApiHitCallBack mApiHitCallBack) {


        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<ProductInfoDetailsResponse> call = git.getProductInfo(mWebsiteID + "", posId, mUserID);
            call.enqueue(new Callback<ProductInfoDetailsResponse>() {
                @Override
                public void onResponse(Call<ProductInfoDetailsResponse> call, final retrofit2.Response<ProductInfoDetailsResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            ProductInfoDetailsResponse data = response.body();
                            if (data != null && data.getStatus() && data.getProductDetail() != null) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductInfoDetailsResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void GetPromotionPopup(final Activity context, final ApiHitCallBack mApiHitCallBack) {


        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<PromotionPopupResponse> call = git.getPromotionPopUp(mWebsiteID + "");
            call.enqueue(new Callback<PromotionPopupResponse>() {
                @Override
                public void onResponse(Call<PromotionPopupResponse> call, final retrofit2.Response<PromotionPopupResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            PromotionPopupResponse data = response.body();
                            if (data != null && data.getStatus() && data.getData() != null) {


                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data.getData());
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PromotionPopupResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void GetRecentViewProductInfo(final Activity context, final ApiHitCallBack mApiHitCallBack) {


        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<MainCategoryWiseProductResponse> call = git.getRecentViewItems(mWebsiteID + "", mUserID);
            call.enqueue(new Callback<MainCategoryWiseProductResponse>() {
                @Override
                public void onResponse(Call<MainCategoryWiseProductResponse> call, final retrofit2.Response<MainCategoryWiseProductResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            MainCategoryWiseProductResponse data = response.body();
                            if (data != null && data.getStatus() && data.getMainCategoryWiseProductData() != null && data.getMainCategoryWiseProductData().size() > 0) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MainCategoryWiseProductResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void GetSimilarProductInfo(final Activity context, String posId, final ApiHitCallBack mApiHitCallBack) {


        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<MainCategoryWiseProductResponse> call = git.getAllSimilarItems(mUserID, mWebsiteID + "", posId);
            call.enqueue(new Callback<MainCategoryWiseProductResponse>() {
                @Override
                public void onResponse(Call<MainCategoryWiseProductResponse> call, final retrofit2.Response<MainCategoryWiseProductResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            MainCategoryWiseProductResponse data = response.body();
                            if (data != null && data.getStatus() && data.getMainCategoryWiseProductData() != null && data.getMainCategoryWiseProductData().size() > 0) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MainCategoryWiseProductResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void WishListAddRemove(final Activity context, boolean isWishlist, String posId,
                                  final ApiHitCallBack mApiHitCallBack) {


        try {
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<BasicApiResponse> call;
            if (isWishlist) {
                call = git.DeleteWishList(new WishListAddRemoveRequest(posId, mUserID, mWebsiteID + ""));
            } else {
                call = git.InsertWishList(new WishListAddRemoveRequest(posId, mUserID, mWebsiteID + ""));
            }

            call.enqueue(new Callback<BasicApiResponse>() {
                @Override
                public void onResponse(Call<BasicApiResponse> call, final retrofit2.Response<BasicApiResponse> response) {

                    if (response != null) {
                        try {
                            if (response.body() != null) {
                                if (response.body().getStatus()) {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onError(ERRORR_NO_DATA, response.body().getMessage());
                                    }
                                }


                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicApiResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

   /* public void GetRecentViewProductInfo(final Activity context, final ApiHitCallBack mApiHitCallBack) {


        try {
            String loginId;
            if (mLoginResponse != null && mLoginResponse.getData() != null) {
                loginId = mUserID;
            } else if (getBrowserId(context) != null && !getBrowserId(context).isEmpty()) {
                loginId = getBrowserId(context);
            } else {
                loginId = "";
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<MainCategoryWiseProductResponse> call = git.getRecentViewItems(mWebsiteID+"", loginId);
            call.enqueue(new Callback<MainCategoryWiseProductResponse>() {
                @Override
                public void onResponse(Call<MainCategoryWiseProductResponse> call, final retrofit2.Response<MainCategoryWiseProductResponse> response) {
                    //Log.e("GetAllProductDetail", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        try {
                            MainCategoryWiseProductResponse data = response.body();
                            if (data != null && data.getStatus() && data.getMainCategoryWiseProductData() != null && data.getMainCategoryWiseProductData().size() > 0) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.something_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MainCategoryWiseProductResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.something_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }
*/

    public void getAllPlroductList(final Activity context, final ArrayList<String> filterOptionTypeIdList, final String filterType,
                                   final int filterTypeId, final String keyWordId, final String orderBy,
                                   final String orderByType, final int startIndex, final ApiHitCallBack mApiHitCallBack) {


        try {
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<AllProductListResponse> call = git.getAllProductsetList(new AllProductListRequest(filterOptionTypeIdList, startIndex,
                    20, orderBy, orderByType, mWebsiteID + "", filterType, filterTypeId, keyWordId, mUserID + ""));
            call.enqueue(new Callback<AllProductListResponse>() {
                @Override
                public void onResponse(Call<AllProductListResponse> call, final retrofit2.Response<AllProductListResponse> response) {
                    if (response != null) {

                        try {
                            AllProductListResponse data = response.body();
                            if (data != null && data.getStatus() && data.getData() != null && data.getData().getProductSetList() != null && data.getData().getProductSetList().size() > 0) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, null);
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<AllProductListResponse> call, Throwable t) {

                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, t.getMessage());
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, null);
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, null);
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void getAllFilterList(final Activity context, final String filterType, final int filterTypeId, final ApiHitCallBack mApiHitCallBack) {


        try {
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<GetAllFilterResponse> call = git.getAllFilterList(new AllProductListRequest(null, 0,
                    20, "", "", mWebsiteID + "", filterType, filterTypeId, null, mUserID + ""));
            call.enqueue(new Callback<GetAllFilterResponse>() {
                @Override
                public void onResponse(Call<GetAllFilterResponse> call, final retrofit2.Response<GetAllFilterResponse> response) {
                    if (response != null) {

                        try {
                            GetAllFilterResponse data = response.body();
                            if (data != null && data.getStatus() && data.getData() != null && data.getData().getFilterLists() != null && data.getData().getFilterLists().size() > 0) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(data);
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, null);
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAllFilterResponse> call, Throwable t) {

                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, t.getMessage());
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, null);
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, null);
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void AddCart(final Activity context, final String POSID, final String Qty, final ApiHitCallBack mApiHitCallBack) {

        try {
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<AddToCartResponse> call = git.AddToCartApi(new AddToCartRequest(Qty, POSID, POSID,
                    mUserID + "",
                    mLoginTypeID + "", ApplicationConstant.INSTANCE.APP_ID,
                    mDeviceId, "", BuildConfig.VERSION_NAME, mDeviceSerialNum, mSessionID,
                    mSession,
                    mWebsiteID + ""));
            call.enqueue(new Callback<AddToCartResponse>() {
                @Override
                public void onResponse(Call<AddToCartResponse> call, final retrofit2.Response<AddToCartResponse> response) {

                    if (response != null) {
                        try {
                            if (response.body() != null) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(response.body());
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, null);
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddToCartResponse> call, Throwable t) {


                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, t.getMessage());
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, null);
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void getCartDetails(final Activity context, CustomLoader loader, final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<CartDetailResponse> call = git.CartDetail(new BasicRequest(
                    mUserID, mLoginTypeID,
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<CartDetailResponse>() {

                @Override
                public void onResponse(Call<CartDetailResponse> call, retrofit2.Response<CartDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            CartDetailResponse mCartDetailResponse = response.body();
                            if (mCartDetailResponse != null) {

                                if (mCartDetailResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mCartDetailResponse);
                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mCartDetailResponse.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mCartDetailResponse.getMsg() + "");
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mCartDetailResponse.getMsg() + "");
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<CartDetailResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage() + "");
        }

    }

    public void CheckDeliveryApi(final String pincode, final ApiHitCallBack mApiHitCallBack) {

        try {
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<CheckDeliveryResponse> call = git.CheckDeliveryApi(pincode);
            call.enqueue(new Callback<CheckDeliveryResponse>() {
                @Override
                public void onResponse(Call<CheckDeliveryResponse> call, final retrofit2.Response<CheckDeliveryResponse> response) {

                    if (response != null) {
                        try {
                            if (response.body() != null) {

                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onSucess(response.body());
                                }


                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, null);
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CheckDeliveryResponse> call, Throwable t) {


                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, t.getMessage());
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, null);
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }


    public void cahangeQuantity(final Activity context, int quantity, int itemId, CustomLoader loader, final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<BasicResponse> call = git.ChangeQuantity(new ChangeQuantityRequest(itemId, quantity,
                    mUserID, mLoginTypeID + "",
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {

                                if (mBasicResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mBasicResponse.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mBasicResponse.getMsg() + "");
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mBasicResponse.getMsg() + "");
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage() + "");
        }

    }

    public void RemoveFromCart(final Activity context, int itemId, CustomLoader loader, final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<BasicResponse> call = git.RemoveFromCart(new RemoveFromCartRequest(itemId,
                    mUserID, mLoginTypeID + "",
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {

                                if (mBasicResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mBasicResponse.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mBasicResponse.getMsg() + "");
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mBasicResponse.getMsg() + "");
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage() + "");
        }

    }

    public void getAddressList(final Activity context, CustomLoader loader, final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<AddressListResponse> call = git.GetShippingAddresses(new BasicRequest(
                    mUserID, mLoginTypeID ,
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<AddressListResponse>() {

                @Override
                public void onResponse(Call<AddressListResponse> call, retrofit2.Response<AddressListResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            AddressListResponse mResponse = response.body();
                            if (mResponse != null) {

                                if (mResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mResponse.getMsg() + "");
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<AddressListResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage() + "");
        }

    }

    public void getStatesList(final Activity context, CustomLoader loader, boolean isFromClick, final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<StatesCitiesResponse> call = git.GetStates(new BasicRequest(
                    mUserID, mLoginTypeID ,
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<StatesCitiesResponse>() {

                @Override
                public void onResponse(Call<StatesCitiesResponse> call, retrofit2.Response<StatesCitiesResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            StatesCitiesResponse mResponse = response.body();
                            if (mResponse != null) {

                                if (mResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (isFromClick) {
                                        Error(context, mResponse.getMsg() + "");
                                    }
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }
                                if (isFromClick) {
                                    Error(context, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (isFromClick) {
                                apiErrorHandle(context, response.code(), response.message());
                            }
                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (isFromClick) {
                            Error(context, e.getMessage() + "");
                        }
                    }
                }

                @Override
                public void onFailure(Call<StatesCitiesResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (isFromClick) {
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(1);
                                }
                                NetworkError(context);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(1);
                                }
                                ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    Error(context, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (isFromClick) {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (isFromClick) {
                Error(context, e.getMessage() + "");
            }
        }

    }

    public void GetPincodeAreaRequest(final Activity context, String pincode, final CustomLoader loader,
                                      ApiCallBack mApiCallBack) {
        try {
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<PincodeAreaResponse> call = git.GetAreabyPincodeRequest(new PincodeAreaRequest(pincode,
                    ApplicationConstant.INSTANCE.APP_ID,
                    mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum, mLoginTypeID,
                    mUserID, mSessionID,
                    mSession)
            );
            call.enqueue(new Callback<PincodeAreaResponse>() {
                @Override
                public void onResponse(Call<PincodeAreaResponse> call, final retrofit2.Response<PincodeAreaResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getAreas() != null && response.body().getAreas().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Area not available or may be pincode doesn't exist.");
                                    }

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<PincodeAreaResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCitiesList(final Activity context, CustomLoader loader, int stateId, final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<StatesCitiesResponse> call = git.GetCities(new CitiesRequest(stateId,
                    mUserID, mLoginTypeID + "",
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<StatesCitiesResponse>() {

                @Override
                public void onResponse(Call<StatesCitiesResponse> call, retrofit2.Response<StatesCitiesResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            StatesCitiesResponse mResponse = response.body();
                            if (mResponse != null) {

                                if (mResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }

                                    Error(context, mResponse.getMsg() + "");

                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }

                                Error(context, context.getResources().getString(R.string.some_thing_error));

                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }

                            apiErrorHandle(context, response.code(), response.message());

                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage() + "");

                    }
                }

                @Override
                public void onFailure(Call<StatesCitiesResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }

                        }

                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }

                        Error(context, context.getResources().getString(R.string.some_thing_error));

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }

            Error(context, e.getMessage() + "");

        }

    }

    public void AddAddress(final Activity context, boolean isDeleted, boolean isDefault, int id, String title, String customerName,
                           String mobileNo, String address, int cityID, int stateID, String area, String pin, String landmark,
                           CustomLoader loader,
                           final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<StatesCitiesResponse> call = git.AddShippingAddress(new AddShippingAddressRequest(isDeleted, isDefault, id, title, customerName, mobileNo, address,
                    cityID, stateID, area, pin, landmark,
                    mUserID, mLoginTypeID + "",
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<StatesCitiesResponse>() {

                @Override
                public void onResponse(Call<StatesCitiesResponse> call, retrofit2.Response<StatesCitiesResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            StatesCitiesResponse mResponse = response.body();
                            if (mResponse != null) {

                                if (mResponse.getStatuscode() == 1) {

                                    Successful(context, mResponse.getMsg() + "");
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }

                                    Error(context, mResponse.getMsg() + "");

                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }

                                Error(context, context.getResources().getString(R.string.some_thing_error));

                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }

                            apiErrorHandle(context, response.code(), response.message());

                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage() + "");

                    }
                }

                @Override
                public void onFailure(Call<StatesCitiesResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }

                        }

                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }

                        Error(context, context.getResources().getString(R.string.some_thing_error));

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }

            Error(context, e.getMessage() + "");

        }

    }

    public void GetWishListProducts(final Activity context, final ApiHitCallBack mApiHitCallBack) {


        try {
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
            Call<MainCategoryWiseProductResponse> call = git.getAllWishListItems(mUserID + "", mWebsiteID + "");
            call.enqueue(new Callback<MainCategoryWiseProductResponse>() {
                @Override
                public void onResponse(Call<MainCategoryWiseProductResponse> call, final retrofit2.Response<MainCategoryWiseProductResponse> response) {


                    if (response != null) {
                        try {
                            MainCategoryWiseProductResponse data = response.body();
                            if (data != null && data.getStatus()) {

                                if (data.getMainCategoryWiseProductData() != null && data.getMainCategoryWiseProductData().size() > 0) {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onSucess(data);
                                    }
                                } else {
                                    if (mApiHitCallBack != null) {
                                        mApiHitCallBack.onError(ERRORR_NO_DATA, "Wishlist not available");
                                    }
                                }


                            } else if (data != null && !data.getStatus()) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, data.getMessage() + "");
                                }
                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NO_DATA, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } catch (Exception ex) {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MainCategoryWiseProductResponse> call, Throwable t) {
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_NETWORK, context.getResources().getString(R.string.err_msg_network));
                                }

                            } else {
                                if (mApiHitCallBack != null) {
                                    mApiHitCallBack.onError(ERRORR_OTHER_ERROR, t.getMessage());
                                }
                            }

                        } else {
                            if (mApiHitCallBack != null) {
                                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiHitCallBack != null) {
                            mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ise.getMessage());
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (mApiHitCallBack != null) {
                mApiHitCallBack.onError(ERRORR_OTHER_ERROR, ex.getMessage());
            }
        }
    }

    public void getOrderList(final Activity context, int orderId, CustomLoader loader,
                             final ApiResponseCallBack mApiCallBack) {
        try {

            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<OrderListResponse> call = git.GetOrders(new OrderDetailRequest(orderId,
                    mUserID, mLoginTypeID + "",
                    ApplicationConstant.INSTANCE.APP_ID, mDeviceId,
                    "", BuildConfig.VERSION_NAME, mDeviceSerialNum,
                    mSessionID, mSession));

            call.enqueue(new Callback<OrderListResponse>() {

                @Override
                public void onResponse(Call<OrderListResponse> call, retrofit2.Response<OrderListResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            OrderListResponse mOrderListResponse = response.body();
                            if (mOrderListResponse != null) {

                                if (mOrderListResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mOrderListResponse);
                                    }
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    Error(context, mOrderListResponse.getMsg() + "");
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(0);
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<OrderListResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage() + "");
        }

    }


    public void apiErrorHandle(Activity context, int code, String msg) {
        if (code == 401) {
            ErrorWithTitle(context, "UNAUTHENTICATED " + code, msg + "");
        } else if (code == 404) {
            ErrorWithTitle(context, "API ERROR " + code, msg + "");
        } else if (code >= 400 && code < 500) {
            ErrorWithTitle(context, "CLIENT ERROR " + code, msg + "");
        } else if (code >= 500 && code < 600) {

            ErrorWithTitle(context, "SERVER ERROR " + code, msg + "");
        } else {
            ErrorWithTitle(context, "FATAL/UNKNOWN ERROR " + code, msg + "");
        }
    }

    public void apiFailureError(Activity context, Throwable t) {
        if (t instanceof UnknownHostException || t instanceof IOException) {
            NetworkError(context, "Network Error", "Poor Internet Connectivity found!!");
        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
        } else {
            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
            } else {
                Error(context, context.getResources().getString(R.string.some_thing_error));
            }
        }
    }


    public interface DialogOTPCallBack {
        void onPositiveClick(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog);

        void onResetCallback(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog);
    }

    public interface ApiHitCallBack {
        void onSucess(Object response);

        void onError(int ErrorType, String msg);
    }


    public interface ApiCallBack {
        void onSucess(Object object);
    }


    public interface ApiCallBackTwoMethod {
        void onSucess(Object object);

        void onError(Object object);
    }


    public interface ApiResponseCallBack {
        void onSucess(Object var1);

        void onError(int var1);
    }

    public interface DialogCallBack {
        void onPositiveClick(String value);

        void onResetCallback(String value);

        void onCancelClick();
    }


}
