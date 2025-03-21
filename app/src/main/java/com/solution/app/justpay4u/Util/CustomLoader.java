package com.solution.app.justpay4u.Util;

import android.app.Dialog;
import android.content.Context;

import com.solution.app.justpay4u.R;


/**
 * Created by admin on 4/9/2016.
 */


public class CustomLoader extends Dialog {

    public CustomLoader(Context context) {
        super(context);
    }

    public CustomLoader(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.custom_dialog_skv);
    }

    public CustomLoader(Context context, boolean cancelable,
                        OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
