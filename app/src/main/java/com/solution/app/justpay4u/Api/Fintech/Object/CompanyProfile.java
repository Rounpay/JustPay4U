package com.solution.app.justpay4u.Api.Fintech.Object;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyProfile {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("emailId")
    @Expose
    public String emailId;
    @SerializedName("phoneNo")
    @Expose
    public String phoneNo;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("accountMobileNo")
    @Expose
    public String accountMobileNo;
    @SerializedName("accountEmailId")
    @Expose
    public String accountEmailId;
    @SerializedName("facebook")
    @Expose
    public String facebook;
    @SerializedName("instagram")
    @Expose
    public String instagram;
    @SerializedName("twitter")
    @Expose
    public String twitter;
    @SerializedName("whatsApp")
    @Expose
    public String whatsApp;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("paymentEnquiry")
    @Expose
    public String paymentEnquiry;
    @SerializedName("headerTitle")
    @Expose
    public String headerTitle;
    @SerializedName("customerCareMobileNos")
    @Expose
    public String customerCareMobileNos;
    @SerializedName("customerCareEmailIds")
    @Expose
    public String customerCareEmailIds;
    @SerializedName("customerPhoneNos")
    @Expose
    public String customerPhoneNos;
    @SerializedName("customerWhatsAppNos")
    @Expose
    public String customerWhatsAppNos;
    @SerializedName("accountPhoneNos")
    @Expose
    public String accountPhoneNos;
    @SerializedName("accountWhatsAppNos")
    @Expose
    public String accountWhatsAppNos;

    public String getPaymentEnquiry() {
        return paymentEnquiry;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public String getCustomerCareMobileNos() {
        if (customerCareMobileNos != null) {
            if (customerCareMobileNos.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return customerCareMobileNos;
            }
        } else {
            return "";
        }
    }

    public String getCustomerCareEmailIds() {
        if (customerCareEmailIds != null) {
            if (customerCareEmailIds.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return customerCareEmailIds;
            }
        } else {
            return "";
        }

    }

    public String getCustomerPhoneNos() {
        if (customerPhoneNos != null) {
            if (customerPhoneNos.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return customerPhoneNos;
            }
        } else {
            return "";
        }
    }

    public String getCustomerWhatsAppNos() {
        if (customerWhatsAppNos != null) {
            if (customerWhatsAppNos.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return customerWhatsAppNos;
            }
        } else {
            return "";
        }
    }

    public String getCustomerWhatsAppNosLink() {
        if (customerWhatsAppNos != null) {
            if (customerWhatsAppNos.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return whatsappLink(customerWhatsAppNos);
            }
        } else {
            return "";
        }
    }

    public String getAccountPhoneNos() {
        if (accountPhoneNos != null) {
            if (accountPhoneNos.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return accountPhoneNos;
            }
        } else {
            return "";
        }
    }

    public String getAccountWhatsAppNos() {
        if (accountWhatsAppNos != null) {
            if (accountWhatsAppNos.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return accountWhatsAppNos;
            }
        } else {
            return "";
        }
    }

    public String getName() {
        if (name != null) {
            if (name.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return name;
            }
        } else {
            return "";
        }
    }

    public String getAddress() {
        if (address != null) {
            if (address.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return address;
            }
        } else {
            return "";
        }

    }

    public String getEmailId() {
        if (emailId != null) {
            if (emailId.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return emailId;
            }
        } else {
            return "";
        }
    }

    public String getPhoneNo() {
        if (phoneNo != null) {
            if (phoneNo.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return phoneNo;
            }
        } else {
            return "";
        }
    }

    public String getMobileNo() {
        if (mobileNo != null) {
            if (mobileNo.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return mobileNo;
            }
        } else {
            return "";
        }
    }

    public String getAccountMobileNo() {
        if (accountMobileNo != null) {
            if (accountMobileNo.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return accountMobileNo;
            }
        } else {
            return "";
        }
    }

    public String getAccountEmailId() {
        if (accountEmailId != null) {
            if (accountEmailId.equalsIgnoreCase("NA")) {
                return "";
            } else {
                return accountEmailId;
            }
        } else {
            return "";
        }
    }

    public String getWhatsApp() {
        if (whatsApp != null) {
            if (whatsApp.equalsIgnoreCase("NA")) {
                return "";
            } else {
                if (!whatsApp.contains("http")) {
                    return "http://" + whatsApp;
                } else {
                    return whatsApp;
                }
            }
        } else {
            return "";
        }
    }

    public String getFacebook() {
        if (facebook != null) {
            if (facebook.equalsIgnoreCase("NA")) {
                return "";
            } else {
                if (!facebook.contains("http")) {
                    return "http://" + facebook;
                } else {
                    return facebook;
                }
            }
        } else {
            return "";
        }
    }

    public String getInstagram() {
        if (instagram != null) {
            if (instagram.equalsIgnoreCase("NA")) {
                return "";
            } else {
                if (!instagram.contains("http")) {
                    return "http://" + instagram;
                } else {
                    return instagram;
                }
            }
        } else {
            return "";
        }
    }

    public String getTwitter() {
        if (twitter != null) {
            if (twitter.equalsIgnoreCase("NA")) {
                return "";
            } else {
                if (!twitter.contains("http")) {
                    return "http://" + twitter;
                } else {
                    return twitter;
                }
            }
        } else {
            return "";
        }
    }

    public String getWebsite() {
        if (website != null) {
            if (website.equalsIgnoreCase("NA")) {
                return "";
            } else {
                if (!website.contains("http")) {
                    return "http://" + website;
                } else {
                    return website;
                }
            }
        } else {
            return "";
        }


    }

    private String whatsappLink(String number) {
        if (number.contains(",")) {
            String[] numberArray = TextUtils.split(number, ",");
            String modifyNumber = "";
            for (String num : numberArray) {
                if (modifyNumber.length() == 0) {
                    modifyNumber = "<a href=" + (String.format("https://api.whatsapp.com/send?phone=%s", "91" + num)) + ">" + num + "</a>";
                } else {
                    modifyNumber = modifyNumber + "," + "<a href=" + (String.format("https://api.whatsapp.com/send?phone=%s", "91" + num)) + ">" + num + "</a>";
                    ;
                }

            }
            return modifyNumber;
        } else {
            return "<a href=" + (String.format("https://api.whatsapp.com/send?phone=%s", "91" + number)) + ">" + number + "</a>";
        }
    }
}
