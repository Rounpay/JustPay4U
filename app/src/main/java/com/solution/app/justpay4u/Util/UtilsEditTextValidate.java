package com.solution.app.justpay4u.Util;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.solution.app.justpay4u.R;


public class UtilsEditTextValidate {

    public boolean validateName(Activity activity, EditText nameEt) {
        if (nameEt.getText().toString().trim().isEmpty()) {
            nameEt.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, nameEt);
            return false;
        } else {
            nameEt.setError(null);
        }

        return true;
    }

    public boolean validateName(Activity activity, TextView nameEt) {
        if (nameEt.getText().toString().trim().isEmpty()) {
            nameEt.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, nameEt);
            return false;
        } else {
            nameEt.setError(null);
        }

        return true;
    }


    public boolean validatePincode(Activity activity, EditText pincodeEt) {
        if (pincodeEt.getText().toString().trim().isEmpty()) {
            pincodeEt.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, pincodeEt);
            return false;
        } else if (pincodeEt.getText().toString().length() < 6 || pincodeEt.getText().toString().length() > 6) {
            pincodeEt.setError(activity.getString(R.string.invalidPincode));
            requestFocus(activity, pincodeEt);
            return false;
        } else {
            pincodeEt.setError(null);
        }

        return true;
    }

    public boolean validatePassword(Activity activity, EditText password) {
        if (password.getText().toString().trim().isEmpty()) {
            password.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, password);
            return false;
        } else {
            password.setError(null);
        }

        return true;
    }

    public boolean validateConfiormPassword(Activity activity, EditText password, EditText confiormPassword) {
        if (confiormPassword.getText().toString().trim().isEmpty()) {
            confiormPassword.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, confiormPassword);
            return false;
        } else if (!confiormPassword.getText().toString().equalsIgnoreCase(password.getText().toString())) {
            confiormPassword.setError(activity.getString(R.string.errorPassword));
            requestFocus(activity, confiormPassword);
            return false;
        } else {
            confiormPassword.setError(null);
        }

        return true;
    }


    public boolean validateEmailMobile(Activity activity, EditText emailEt) {
        String enterText = emailEt.getText().toString();

        if (enterText.trim().isEmpty()) {
            emailEt.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, emailEt);
            return false;
        } else {
            try {
                long num = Long.parseLong(enterText);
                if (String.valueOf(num).length() != 10) {
                    emailEt.setError(activity.getString(R.string.errorMobile));
                    requestFocus(activity, emailEt);
                    return false;
                }

            } catch (NumberFormatException e) {
                return validateEmail(activity, emailEt);
            }
            emailEt.setError(null);
        }

        return true;
    }

    public boolean validateEmailMobileWithoutLength(Activity activity, EditText emailEt) {
        String enterText = emailEt.getText().toString();

        if (enterText.trim().isEmpty()) {
            emailEt.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, emailEt);
            return false;
        } else {
            try {
                long num = Long.parseLong(enterText);

               /* if (String.valueOf(num).length() != 10) {
                    emailEt.setError(activity.getString(R.string.errorMobile));
                    requestFocus(activity, emailEt);
                    return false;
                }*/

            } catch (NumberFormatException e) {
                return validateEmail(activity, emailEt);
            }
            emailEt.setError(null);
        }

        return true;
    }


    public boolean validateMobile(Activity activity, EditText mobiltEt) {
        if (mobiltEt.getText().toString().trim().isEmpty()) {
            mobiltEt.setError(activity.getString(R.string.fieldEmpty));
            requestFocus(activity, mobiltEt);
            return false;
        } else if (mobiltEt.getText().toString().length() != 10) {
            mobiltEt.setError(activity.getString(R.string.errorMobile));
            requestFocus(activity, mobiltEt);
            return false;
        } else {
            mobiltEt.setError(null);
        }

        return true;
    }

    public boolean validateEmail(Activity activity, EditText emailEt) {
        if (emailEt.getText().toString().trim().isEmpty() ||
                !emailEt.getText().toString().contains("@") ||
                !emailEt.getText().toString().contains(".")) {
            emailEt.setError(activity.getString(R.string.errorEmail));
            requestFocus(activity, emailEt);
            return false;
        } else {
            emailEt.setError(null);
        }

        return true;
    }

    private void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }
}
