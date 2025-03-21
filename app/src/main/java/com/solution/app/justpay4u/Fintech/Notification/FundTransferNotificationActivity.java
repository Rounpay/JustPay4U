package com.solution.app.justpay4u.Fintech.Notification;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

public class FundTransferNotificationActivity extends AppCompatActivity {

    TextView custCare, name, mobileNo, userId, amount;
    View fundTransferView, rejectView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_fund_transfer_notification);

            custCare = findViewById(R.id.custCare);
            name = findViewById(R.id.name);
            amount = findViewById(R.id.amount);
            mobileNo = findViewById(R.id.mobileNo);
            userId = findViewById(R.id.userId);
            fundTransferView = findViewById(R.id.fundTransferView);
            rejectView = findViewById(R.id.rejectView);

            AppUserListResponse companyProfileData = ApiFintechUtilMethods.INSTANCE.getCompanyProfile(ApiFintechUtilMethods.INSTANCE.getAppPreferences(this));
            if (companyProfileData != null && companyProfileData.getCompanyProfile() != null) {
                String value = "";
                if (companyProfileData.getCompanyProfile().getCustomerCareMobileNos() != null && !companyProfileData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                    value = value + getHtml("Mobile No", companyProfileData.getCompanyProfile().getCustomerCareMobileNos());
                }
                if (companyProfileData.getCompanyProfile().getCustomerPhoneNos() != null && !companyProfileData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                    if (value.length() > 0) {
                        value = value + "<br/>";
                    }
                    value = value + getHtml("Landline No", companyProfileData.getCompanyProfile().getCustomerPhoneNos());
                }
                if (companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink() != null && !companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink().isEmpty()) {
                    if (value.length() > 0) {
                        value = value + "<br/>";
                    }
                    value = value + "Whatsapp - " + companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink();
                }

                Utility.INSTANCE.setTextViewHTML(this, custCare, "Customer Care<br/>" + value);

            } else {
                custCare.setVisibility(View.GONE);
            }

            findViewById(R.id.closeIv).setOnClickListener(v -> finish());
        });

    }

    String getHtml(String prefix, String number) {
        if (number.contains(",")) {
            String[] array = number.split(",");
            String num = "";

            for (int i = 0; i < array.length; i++) {

                if (i > 0) {
                    num = num + ", <a href=tel:" + array[i] + ">" + array[i] + "</a>";
                } else {
                    num = num + "<a href=tel:" + array[i] + ">" + array[i] + "</a>";
                }

            }
            return prefix + " - " + num;
        } else {
            return prefix + " - <a href=tel:" + number + ">" + number + "</a>";
        }
    }

}
