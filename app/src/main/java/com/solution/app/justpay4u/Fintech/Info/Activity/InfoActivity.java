package com.solution.app.justpay4u.Fintech.Info.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.solution.app.justpay4u.Api.Fintech.Object.Banners;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.CustomPagerAdapter;
import com.solution.app.justpay4u.Fintech.Info.Adapter.InfoContactDataListAdapter;
import com.solution.app.justpay4u.Fintech.Info.Model.InfoContactDataItem;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

public class InfoActivity extends AppCompatActivity {


    View inviteView, bannerView;
    ImageView whatsappShareView, facebookShareView, twitterShareView, mailShareView, shareView;
    ImageView whatsappShareView1, facebookShareView1, twitterShareView1, mailShareView1, shareView1;
    View followUsView;
    LinearLayout image_count;
    ViewPager pager;
    TextView shareContent;
    private String websiteLink, fbLink, instagramLink, twiLink;
    private LoginResponse mLoginDataResponse;
    private LinearLayout llBank;
    private LinearLayout llContactus;
    private View customerCareView;
    private RecyclerView customerCareRecyclerView;
    private View paymentInqueryView;
    private RecyclerView accountRecyclerView;
    private LinearLayout addressView;
    private TextView address;
    private LinearLayout facebookView;
    private TextView facebbokLink;
    private LinearLayout instagramView;
    private TextView instaLink;
    private LinearLayout twitterView;
    private TextView twitterLink;
    private LinearLayout websiteView;
    private TextView website;
    private LinearLayout Mobiletollfree;
    private LinearLayout DTHtollfree;
    private TextView currentVersion;
    private AppPreferences mAppPreferences;
    private AppUserListResponse companyData;
    private String deviceId, deviceSerialNum;
    private Runnable runnable;
    private Handler handler;
    private CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_info);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            setFindId();
            setClickView();
            new Handler(Looper.getMainLooper()).post(() -> {
                companyData = ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences);
                setContactData(companyData);
                getDetail();
            });

            if (mLoginDataResponse.isReferral()) {
                inviteView.setVisibility(View.GONE);
                ShareConentApi();
            } else {
                bannerView.setVisibility(View.GONE);
                inviteView.setVisibility(View.VISIBLE);
            }
        });

    }


    void setFindId() {
        image_count = findViewById(R.id.image_count);
        pager = findViewById(R.id.pager);
        shareContent = findViewById(R.id.shareContent);
        inviteView = findViewById(R.id.inviteView);
        bannerView = findViewById(R.id.bannerView);
        llBank = findViewById(R.id.ll_bank);
        llContactus = findViewById(R.id.ll_contactus);
        customerCareView = findViewById(R.id.customerCareView);
        customerCareRecyclerView = findViewById(R.id.customerCareRecyclerView);
        paymentInqueryView = findViewById(R.id.paymentInqueryView);
        accountRecyclerView = findViewById(R.id.accountRecyclerView);
        addressView = findViewById(R.id.addressView);
        address = findViewById(R.id.address);
        facebookView = findViewById(R.id.facebookView);
        facebbokLink = findViewById(R.id.facebbokLink);
        instagramView = findViewById(R.id.instagramView);
        instaLink = findViewById(R.id.instaLink);
        twitterView = findViewById(R.id.twitterView);
        twitterLink = findViewById(R.id.twitterLink);
        websiteView = findViewById(R.id.websiteView);
        website = findViewById(R.id.website);
        Mobiletollfree = findViewById(R.id.Mobiletollfree);
        DTHtollfree = findViewById(R.id.DTHtollfree);
        currentVersion = findViewById(R.id.currentVersion);
        whatsappShareView = findViewById(R.id.whatsappShareView);
        facebookShareView = findViewById(R.id.facebookShareView);
        twitterShareView = findViewById(R.id.twitterShareView);
        mailShareView = findViewById(R.id.mailShareView);
        shareView = findViewById(R.id.shareView);
        whatsappShareView1 = findViewById(R.id.whatsappShareView1);
        facebookShareView1 = findViewById(R.id.facebookShareView1);
        twitterShareView1 = findViewById(R.id.twitterShareView1);
        mailShareView1 = findViewById(R.id.mailShareView1);
        shareView1 = findViewById(R.id.shareView1);
        followUsView = findViewById(R.id.followUsView);
        currentVersion.setText("Current Version : " + BuildConfig.VERSION_NAME);
        customerCareRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.privacy).setOnClickListener(v -> {
            String url = ApplicationConstant.INSTANCE.baseUrl+"/privacy";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        findViewById(R.id.Terms).setOnClickListener(v -> {
            String url = ApplicationConstant.INSTANCE.baseUrl+"/t-c";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

    }

    void setClickView() {
        websiteView.setOnClickListener(v -> browseLink(websiteLink));
        twitterView.setOnClickListener(v -> browseLink(twiLink));
        instagramView.setOnClickListener(v -> browseLink(instagramLink));
        facebookView.setOnClickListener(v -> browseLink(fbLink));
        Mobiletollfree.setOnClickListener(v -> {
            Intent i = new Intent(this, DthSupportActivity.class);
            i.putExtra("From", "Prepaid");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);


        });
        shareView.setOnClickListener(v -> shareIt(null));
        whatsappShareView.setOnClickListener(v -> shareIt("com.whatsapp"));
        facebookShareView.setOnClickListener(v -> shareIt("com.facebook.katana"));
        twitterShareView.setOnClickListener(v -> shareIt("com.twitter.android"));
        mailShareView.setOnClickListener(v -> shareIt("email"));

        shareView1.setOnClickListener(v -> shareIt(null));
        whatsappShareView1.setOnClickListener(v -> shareIt("com.whatsapp"));
        facebookShareView1.setOnClickListener(v -> shareIt("com.facebook.katana"));
        twitterShareView1.setOnClickListener(v -> shareIt("com.twitter.android"));
        mailShareView1.setOnClickListener(v -> shareIt("email"));


        /*playstorelink.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
            }
        });*/
        DTHtollfree.setOnClickListener(v -> {
            Intent i = new Intent(this, DthSupportActivity.class);
            i.putExtra("From", "DTH");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        llBank.setOnClickListener(v -> {
            Intent i = new Intent(this, BankDetailActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
    }

    void browseLink(String mLink) {
        try {
            Intent dialIntent = new Intent(Intent.ACTION_VIEW);
            dialIntent.setData(Uri.parse(mLink + ""));
            startActivity(dialIntent);
        } catch (Exception e) {
            Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mLink + ""));
            startActivity(dialIntent);
        }
    }

    private void shareIt(String packageName) {

        String shareMessage = getString(R.string.share_msg, ApplicationConstant.INSTANCE.inviteUrl + mLoginDataResponse.getData().getUserID());

        try {
            if (packageName != null && packageName.equalsIgnoreCase("sms")) {
                Uri uri = Uri.parse("smsto:");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", shareMessage);
                startActivity(Intent.createChooser(intent, "choose one"));
            } else if (packageName != null && packageName.equalsIgnoreCase("email")) {
                Uri uri = Uri.parse("mailto:");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent, "choose one"));
            } else {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                if (packageName != null) {
                    shareIntent.setPackage(packageName);
                }
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }


        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (packageName.equalsIgnoreCase("sms")) {
                sendSms(shareMessage);
            } else if (packageName.equalsIgnoreCase("email")) {
                sendEmail(shareMessage);
            } else if (packageName.contains("facebook")) {
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + ApplicationConstant.INSTANCE.inviteUrl + mLoginDataResponse.getData().getUserID();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            } else if (packageName.contains("whatsapp")) {
                String sharerUrl = "https://wa.me/send?text=" + shareMessage;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            } else if (packageName.contains("twitter")) {
                String sharerUrl = "https://twitter.com/intent/tweet?text=" + shareMessage;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sorry, App not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendEmail(String shareMessage) {
        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "choose one"));
        } catch (ActivityNotFoundException anfe) {

        } catch (Exception e) {

        }
    }

    void sendSms(String shareMessage) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
            {
                String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); //Need to change the build to API 19
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                if (defaultSmsPackageName != null)//Can be null in case that there is no default, then the user would be able to choose any app that support this intent.
                {
                    sendIntent.setPackage(defaultSmsPackageName);
                }
                startActivity(Intent.createChooser(sendIntent, "choose one"));

            } else //For early versions, do what worked for you before.
            {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", shareMessage);
                startActivity(Intent.createChooser(sendIntent, "choose one"));
            }
        } catch (ActivityNotFoundException anfe) {

        } catch (Exception e) {

        }
    }

    void getDetail() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                ApiFintechUtilMethods.INSTANCE.GetCompanyProfile(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                    companyData = (AppUserListResponse) object;
                    setContactData(companyData);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    private ArrayList<InfoContactDataItem> getListData(int type, String editableData) {
        int icon = R.drawable.ic_call_black_24dp;
        int iconMain = R.drawable.ic_contact_mobile;
        String headerText = "Call Us";
        if (type == 1) {
            icon = R.drawable.ic_call_black_24dp;
            iconMain = R.drawable.ic_contact_mobile;
            headerText = "Call Us";
        }
        if (type == 2) {
            icon = R.drawable.ic_call_black_24dp;
            iconMain = R.drawable.ic_telephone;
            headerText = "Call Us";
        }
        if (type == 3) {
            icon = R.drawable.ic_whatsapp_outline;
            iconMain = R.drawable.ic_whatsapp;
            headerText = "Whatsapp";
        }
        if (type == 4) {
            icon = R.drawable.ic_email_outline;
            iconMain = R.drawable.ic_email;
            headerText = "Email";
        }
        ArrayList<InfoContactDataItem> mSupportDataItems = new ArrayList<>();
        if (editableData.contains(",")) {
            String[] emailArray = editableData.split(",");
            if (emailArray.length > 0) {
                for (String value : emailArray) {
                    mSupportDataItems.add(new InfoContactDataItem(icon, headerText, type, value.trim(), iconMain));
                }
            }
        } else {
            mSupportDataItems.add(new InfoContactDataItem(icon, headerText, type, editableData.trim(), iconMain));
        }
        return mSupportDataItems;
    }

    void setContactData(AppUserListResponse mContactData) {
        if (mContactData != null && mContactData.getCompanyProfile() != null) {
            llContactus.setVisibility(View.VISIBLE);
            ArrayList<InfoContactDataItem> mSupportDataCustomerContacts = new ArrayList<>();

            if (mContactData.getCompanyProfile().getCustomerCareMobileNos() != null && !mContactData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                mSupportDataCustomerContacts.addAll(getListData(1, mContactData.getCompanyProfile().getCustomerCareMobileNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerPhoneNos() != null && !mContactData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                mSupportDataCustomerContacts.addAll(getListData(2, mContactData.getCompanyProfile().getCustomerPhoneNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerWhatsAppNos() != null && !mContactData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {

                mSupportDataCustomerContacts.addAll(getListData(3, mContactData.getCompanyProfile().getCustomerWhatsAppNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerCareEmailIds() != null && !mContactData.getCompanyProfile().getCustomerCareEmailIds().isEmpty()) {

                mSupportDataCustomerContacts.addAll(getListData(4, mContactData.getCompanyProfile().getCustomerCareEmailIds()));
            }


            if (mSupportDataCustomerContacts.size() > 0) {
                customerCareView.setVisibility(View.VISIBLE);

                InfoContactDataListAdapter mInfoContactDataListAdapter = new InfoContactDataListAdapter(this, mSupportDataCustomerContacts);
                customerCareRecyclerView.setAdapter(mInfoContactDataListAdapter);
            } else {
                customerCareView.setVisibility(View.GONE);
            }


            ArrayList<InfoContactDataItem> mSupportDataAccountCallItems = new ArrayList<>();
            if (mContactData.getCompanyProfile().getAccountMobileNo() != null && !mContactData.getCompanyProfile().getAccountMobileNo().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(1, mContactData.getCompanyProfile().getAccountMobileNo()));
            }

            if (mContactData.getCompanyProfile().getAccountPhoneNos() != null && !mContactData.getCompanyProfile().getAccountPhoneNos().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(2, mContactData.getCompanyProfile().getAccountPhoneNos()));
            }

            if (mContactData.getCompanyProfile().getAccountWhatsAppNos() != null && !mContactData.getCompanyProfile().getAccountWhatsAppNos().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(3, mContactData.getCompanyProfile().getAccountWhatsAppNos()));
            }
            if (mContactData.getCompanyProfile().getAccountEmailId() != null && !mContactData.getCompanyProfile().getAccountEmailId().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(4, mContactData.getCompanyProfile().getAccountEmailId()));
            }

            if (mSupportDataAccountCallItems.size() > 0) {
                paymentInqueryView.setVisibility(View.VISIBLE);
                InfoContactDataListAdapter mInfoContactDataListAdapter = new InfoContactDataListAdapter(this, mSupportDataAccountCallItems);
                accountRecyclerView.setAdapter(mInfoContactDataListAdapter);
            } else {
                paymentInqueryView.setVisibility(View.GONE);
            }

            boolean isFolloUsViewAvailable = false;
            if (mContactData.getCompanyProfile().getAddress() != null && !mContactData.getCompanyProfile().getAddress().isEmpty()) {
                addressView.setVisibility(View.VISIBLE);
                isFolloUsViewAvailable = true;
                address.setText(Html.fromHtml(mContactData.getCompanyProfile().getAddress()));
            } else {
                addressView.setVisibility(View.GONE);
            }

            if (mContactData.getCompanyProfile().getWebsite() != null && !mContactData.getCompanyProfile().getWebsite().isEmpty()) {
                websiteView.setVisibility(View.VISIBLE);
                isFolloUsViewAvailable = true;
                websiteLink = mContactData.getCompanyProfile().getWebsite();
                ApiFintechUtilMethods.INSTANCE.setTextViewHTML(this, website, "<a href=" + mContactData.getCompanyProfile().getWebsite() + ">Open Website</a>");
            } else {
                websiteView.setVisibility(View.GONE);
            }
            if (mContactData.getCompanyProfile().getFacebook() != null && !mContactData.getCompanyProfile().getFacebook().isEmpty()) {
                facebookView.setVisibility(View.VISIBLE);
                isFolloUsViewAvailable = true;
                fbLink = mContactData.getCompanyProfile().getFacebook();
                ApiFintechUtilMethods.INSTANCE.setTextViewHTML(this, facebbokLink, "<a href=" + mContactData.getCompanyProfile().getFacebook() + ">Follow Us</a>");
            } else {
                facebookView.setVisibility(View.GONE);
            }
            if (mContactData.getCompanyProfile().getTwitter() != null && !mContactData.getCompanyProfile().getTwitter().isEmpty()) {
                twitterView.setVisibility(View.VISIBLE);
                isFolloUsViewAvailable = true;
                twiLink = mContactData.getCompanyProfile().getTwitter();
                ApiFintechUtilMethods.INSTANCE.setTextViewHTML(this, twitterLink, "<a href=" + mContactData.getCompanyProfile().getTwitter() + ">Follow Us</a>");
            } else {
                twitterView.setVisibility(View.GONE);
            }
            if (mContactData.getCompanyProfile().getInstagram() != null && !mContactData.getCompanyProfile().getInstagram().isEmpty()) {
                instagramView.setVisibility(View.VISIBLE);
                isFolloUsViewAvailable = true;
                instagramLink = mContactData.getCompanyProfile().getInstagram();
                ApiFintechUtilMethods.INSTANCE.setTextViewHTML(this, instaLink, "<a href=" + mContactData.getCompanyProfile().getInstagram() + ">Follow Us</a>");
            } else {
                instagramView.setVisibility(View.GONE);
            }


            if (isFolloUsViewAvailable) {
                followUsView.setVisibility(View.VISIBLE);
            } else {
                followUsView.setVisibility(View.GONE);
            }
        } else {
            llContactus.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    public void ShareConentApi() {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetAppRefferalContent(new BasicRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    //Log.e("banner", "is : " + new Gson().toJson(response.body()).toString());
                    try {
                        loader.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1) {
                                ArrayList<Banners> bannerList = response.body().getRefferalImage();
                                if (bannerList != null && bannerList.size() > 0) {

                                    bannerView.setVisibility(View.VISIBLE);
                                    shareContent.setText(Html.fromHtml(formatedContent(response.body().getRefferalContent() + "")));

                                    CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(bannerList, InfoActivity.this, 2);
                                    pager.setAdapter(mCustomPagerAdapter);
                                    pager.setOffscreenPageLimit(mCustomPagerAdapter.getCount());
                                    if (bannerList.size() > 1) {
                                        int mDotsCount = pager.getAdapter().getCount();
                                        TextView[] mDotsText = new TextView[mDotsCount];
                                        //here we set the dots
                                        for (int i = 0; i < mDotsCount; i++) {
                                            mDotsText[i] = new TextView(InfoActivity.this);
                                            mDotsText[i].setText(".");
                                            mDotsText[i].setTextSize(50);
                                            mDotsText[i].setTypeface(null, Typeface.BOLD);
                                            mDotsText[i].setTextColor(getResources().getColor(R.color.grey));
                                            image_count.addView(mDotsText[i]);
                                        }


                                        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                            @Override
                                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                for (int i = 0; i < mDotsCount; i++) {
                                                    mDotsText[i].setTextColor(getResources().getColor(R.color.grey));
                                                }
                                                mDotsText[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                                            }

                                            @Override
                                            public void onPageSelected(int position) {

                                            }

                                            @Override
                                            public void onPageScrollStateChanged(int state) {

                                            }
                                        });

                                        postDelayedScrollNext();
                                    }

                                } else {
                                    bannerView.setVisibility(View.GONE);
                                    inviteView.setVisibility(View.VISIBLE);
                                }

                            }
                        } else {
                            bannerView.setVisibility(View.GONE);
                            inviteView.setVisibility(View.VISIBLE);
                            //ApiUtilMethods.INSTANCE.apiErrorHandle(InfoActivity.this, response.code(), response.message());;
                        }
                    } catch (Exception e) {
                        loader.dismiss();
                        bannerView.setVisibility(View.GONE);
                        inviteView.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    bannerView.setVisibility(View.GONE);
                    inviteView.setVisibility(View.VISIBLE);
                    loader.dismiss();
                }
            });

        } catch (Exception e) {
            loader.dismiss();
            bannerView.setVisibility(View.GONE);
            inviteView.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    String formatedContent(String value) {

        if (value.contains("*")) {
            Pattern p = Pattern.compile("\\*([^\\*]*)\\*");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("*" + m.group(1) + "*", "<b>" + m.group(1) + "</b>");

            }
        }
        if (value.contains("_")) {
            Pattern p = Pattern.compile("\\_([^\\_]*)\\_");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("_" + m.group(1) + "_", "<i>" + m.group(1) + "</i>");

            }
        }

        if (value.contains("~")) {
            Pattern p = Pattern.compile("\\~([^\\~]*)\\~");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("~" + m.group(1) + "~", "<s>" + m.group(1) + "</s>");

            }
        }
        if (value.contains("```")) {
            Pattern p = Pattern.compile("\\```([^\\```]*)\\```");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("```" + m.group(1) + "```", "<tt>" + m.group(1) + "</tt>");

            }
        }
        return value;
    }

    private void postDelayedScrollNext() {
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            public void run() {

                if (pager.getAdapter() != null) {
                    if (pager.getCurrentItem() == pager.getAdapter().getCount() - 1) {
                        pager.setCurrentItem(0);
                        postDelayedScrollNext();
                        return;
                    }
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                    // postDelayedScrollNext(position+1);
                    postDelayedScrollNext();
                }

                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        };
        handler.postDelayed(runnable, 2000);

    }

    @Override
    public void onDestroy() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroy();
    }
}
