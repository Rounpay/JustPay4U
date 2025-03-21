package com.solution.app.justpay4u.Util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author pradeep.arige
 */
public enum Utility {
    INSTANCE;
    public EKYCStepsDialog.BankClickCallBack mBankClickCallBack;

    public static String getRupeeAmount(double value) {
        String rupeeAmount = "0.0";
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        rupeeAmount = formatter.format(value);
        return rupeeAmount;

    }

    public static String chckStringNull(String value) {
        String value1 = "";
        try {
            if (value != null &&
                    !value.equalsIgnoreCase(null) &&
                    !value.equalsIgnoreCase("null") &&
                    !value.equalsIgnoreCase("")) {
                value1 = value.trim();
            } else {
                value1 = "";
            }
        } catch (Exception e) {
            value1 = "";
        }
        return value1;
    }

    public String getAmountFormat(String amount) {
        StringBuilder strBind = new StringBuilder(amount);
        strBind.append(".00");
        return strBind.toString();
    }
    public String formatedAmountSixPlace(String value) {
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
                } else if (postfixValue.equalsIgnoreCase(".00000")) {
                    return value.replace(".00000", "");
                } else if (postfixValue.equalsIgnoreCase(".000000")) {
                    return value.replace(".000000", "");
                } else if (postfixValue.equalsIgnoreCase(".0000000")) {
                    return value.replace(".0000000", "");
                } else if (postfixValue.equalsIgnoreCase(".00000000")) {
                    return value.replace(".00000000", "");
                } else if (postfixValue.equalsIgnoreCase(".000000000")) {
                    return value.replace(".000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".0000000000")) {
                    return value.replace(".0000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".00000000000")) {
                    return value.replace(".00000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".000000000000")) {
                    return value.replace(".000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".0000000000000")) {
                    return value.replace(".0000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".00000000000000")) {
                    return value.replace(".00000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".000000000000000")) {
                    return value.replace(".000000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".0000000000000000")) {
                    return value.replace(".0000000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".00000000000000000")) {
                    return value.replace(".00000000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".000000000000000000")) {
                    return value.replace(".000000000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".0000000000000000000")) {
                    return value.replace(".0000000000000000000", "");
                } else if (postfixValue.equalsIgnoreCase(".00000000000000000000")) {
                    return value.replace(".00000000000000000000", "");
                } else {
                    try {
                        return String.format("%.6f", Double.parseDouble(value));
                    } catch (NumberFormatException nfe) {
                        return value;
                    }
                }
            } else {
                return value;
            }

        } else {
            return "0";
        }
    }


    public String getAmountFormawitdot(String amount) {
        Pattern regex = Pattern.compile("[.]");
        Matcher matcher = regex.matcher(amount);
        if (matcher.find()) {
            // Do something
            return amount;
        } else {
            return getAmountFormat(amount);
        }

    }

    public double convertStringToDouble(String data) {
        try {
            NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
            double amountcheck = nf.parse(data).doubleValue();
            return amountcheck;
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0.0;
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

    public String formatedAmountWithOutRupees(String value) {

        if (value != null && !value.isEmpty()) {
            if (value.contains(".")) {
                String postfixValue = value.substring(value.indexOf("."));
                if (postfixValue.equalsIgnoreCase(".0")) {
                    return value.replace(".0", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".00")) {
                    return value.replace(".00", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".000")) {
                    return value.replace(".000", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".0000")) {
                    return value.replace(".0000", "").trim();
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

    public String formatedDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss:SSS aa");
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

    public String formatedDateWithT(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                Date date = inputFormat.parse(dateStr.replace("T", " "));
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return dateStr.replace("T", " ");
            }
        }
        return "";
    } public String formatedDate3(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {

            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                Date date = inputFormat.parse(dateStr.replace("T", " "));
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

    public String formatedDateOfSlash(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
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

    public String formatedOnlyDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss aa");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM");
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

    public String formatedDateTimeOfSlash(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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

    public String formatedDateYMD(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
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

    public boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected void makeLinkClickable(final Context context, SpannableStringBuilder strBuilder, final URLSpan span) {
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
    public String formatedAmountReplaceLastZero(double value) {
        if (value > 0) {
            //BigDecimal bd = new BigDecimal((value + "").replaceAll("[" + 0 +  /*"," +"." +*/ "]+$", ""));
            BigDecimal bd = new BigDecimal((value + "").replaceAll("0*$", ""));
            return bd.toPlainString();

        }
        return "0";
    }

    public void setTextViewHTML(Context context, TextView text, String html) {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(context, strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public boolean isValidString(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 0) {
                return true;
            }
        }

        return false;
    }

    public void openWhatsapp(Activity mContext, String smsNumber) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_MAIN);
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            // sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
            sendIntent.putExtra("jid", "91" + smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            mContext.startActivity(sendIntent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", "91" + smsNumber)));
                if (mContext.getPackageManager().resolveActivity(intent, 0) != null) {
                    mContext.startActivity(intent);
                } else {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", "91" + smsNumber)));
                    mContext.startActivity(intent);
                }
            } catch (Exception ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", "91" + smsNumber)));
                mContext.startActivity(intent);

            }
        }

    }


    public boolean isSpecialCharacter(String s) {

        if (s != null && !s.isEmpty()) {
            Pattern p = Pattern.compile("[^a-zA-Z ]");
            Matcher m = p.matcher(s);
            boolean b = m.find();

            if (b) {
                return true;
            } else {
                return false;
            }
        }else {
            return true;
        }

    }

    public InputFilter getEditTextCharacterFilter() {
        return (source, start, end, dest, dstart, dend) -> {

            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (isCharAllowed(c)) // put your condition here
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        };
    }

    private boolean isCharAllowed(char c) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(String.valueOf(c));
        return ms.matches();
    }

    public String formatedDateWithT2(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                Date date = inputFormat.parse(dateStr.replace("T", " "));
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return dateStr.replace("T", " ");
            }
        }
        return "";
    }

    public String formatedDateTimeOfSlash2(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
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

}
