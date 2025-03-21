package com.solution.app.justpay4u.Util.DropdownDialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.AllowedWallet;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletMappings;
import com.solution.app.justpay4u.Api.Networking.Object.GetTopupDetailsByUserIdData;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingDashboardActivity;

import java.util.ArrayList;
import java.util.List;

public class DropDownDialog {

    Activity mContext;
    private PopupWindow popup;

    public DropDownDialog(Activity mContext) {
        this.mContext = mContext;
    }

    public void showDropDownShopAreaPopup(View v, final int selectPos, List<DropDownModel> mDropDownList, ClickDropDownItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        DropDownListAdapter mDropDownListAdapter = new DropDownListAdapter(mContext, true, mDropDownList, selectPos, (clickPosition, value1, object) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, value1, object);
            }

        });
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }

    public void showDropDownBalanceHomePopup(View v, ArrayList<BalanceData> mDropDownList, int layoutView) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(layoutView, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        DropDownBalanceHomeListAdapter mDropDownListAdapter = new DropDownBalanceHomeListAdapter(mContext, mDropDownList);
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, mContext instanceof ShoppingDashboardActivity ? margine : -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }

    public void showDropDownBalancePopup(View v, final int selectPos, List<BalanceData> mDropDownList, ClickDropDownBalanceItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        DropDownBalanceListAdapter mDropDownListAdapter = new DropDownBalanceListAdapter(mContext, false, mDropDownList, selectPos, (clickPosition, value1, balanceData) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, value1, balanceData);
            }

        });
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }


    public void showDropDownPopup(View v, final int selectPos, int listType, List<AllowedWallet> mDropDownList,
                                  ClickDropDownAllowedWalletItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        /*listType : 1= BuisnessType, 2= PackageType, 3= WalletType*/
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        DropDownAllowWalletAdapter mDropDownListAdapter = new DropDownAllowWalletAdapter(mContext, listType,
                mDropDownList, selectPos, (clickPosition, item) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, item);
            }

        });
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }


    public void showDropDownPopup(View v, final int selectPos, List<DropDownModel> mDropDownList, ClickDropDownItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        DropDownListAdapter mDropDownListAdapter = new DropDownListAdapter(mContext, false, mDropDownList, selectPos, (clickPosition, value1, object) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, value1, object);
            }

        });
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }
    public void newDrop(View v,String isTo, final int selectPos, List<MoveToWalletMappings> mDropDownList, ClickDropDownItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        List<MoveToWalletMappings> mDropDownListnew =new ArrayList<>();
        for (int i=0;i<=mDropDownList.size()-1;i++){
            if (mDropDownList.get(i).getisFrenchies()){
            mDropDownListnew.add(mDropDownList.get(i));
            }
          }

        DropDownListAdapterNeww mDropDownListAdapter = new DropDownListAdapterNeww(mContext,isTo, false, mDropDownListnew, selectPos, (clickPosition, value1, object) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, value1, object);
            }

        });
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }
//    public void showDropDownPopupNew(View v,  final int selectPos,  ArrayList<BalanceData>  mDropDownList, ClickDropDownItem mClickDropDownItem) {
//        if (popup != null && popup.isShowing()) {
//            return;
//        }
//        popup = new PopupWindow(mContext);
//        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
//        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
//        LinearLayout view = layout.findViewById(R.id.viewContainer);
//        view.getLayoutParams().width = v.getMeasuredWidth();
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//
//
//        DropDownListAdapterNew mDropDownListAdapter = new DropDownListAdapterNew(mContext, false, mDropDownList, selectPos, (clickPosition, value1, object) -> {
//            if (popup != null) {
//                popup.dismiss();
//            }
//
//
//            if (mClickDropDownItem != null) {
//                mClickDropDownItem.onClick(clickPosition, value1, object);
//            }
//
//        });
//        recyclerView.setAdapter(mDropDownListAdapter);
//
//        popup.setContentView(layout);
//        popup.setFocusable(true);
//        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
//        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            popup.setOverlapAnchor(true);
//            popup.showAsDropDown(v, -margine, -margine);
//        } else {
//            // Show anchored to button
//            int[] location = new int[2];
//            v.getLocationOnScreen(location);
//            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));
//
//        }
//        // popup.showAsDropDown(v);
//
//    }

    public void showDropDownPopup(View v, final int selectPos,int listType, List<GetTopupDetailsByUserIdData> mDropDownList,
                                  ClickDropDownTopupDetailByUserItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        /*listType : 1= BuisnessType, 2= PackageType, 3= WalletType*/
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        DropDownTopupDetailByUserListAdapter mDropDownListAdapter = new DropDownTopupDetailByUserListAdapter(mContext, listType, mDropDownList, selectPos, (clickPosition,  item) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition,  item);
            }

        });
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }

    public void showDropDownPopup(View v, final int selectPos, ArrayList<String> mDropDownList, ClickDropDownItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        popup = new PopupWindow(mContext);
        View layout = mContext.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        DropDownStringListAdapter mDropDownStringListAdapter = new DropDownStringListAdapter(mContext, mDropDownList, selectPos, (clickPosition, value1, object) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, value1, object);
            }

        });
        recyclerView.setAdapter(mDropDownStringListAdapter);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        int margine = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        //  popup.showAsDropDown(v);

    }


    public interface ClickDropDownItem {
        void onClick(int clickPosition, String value, Object object);
    }

    public interface ClickDropDownBalanceItem {
        void onClick(int clickPosition, String value, BalanceData balanceData);
    }public interface ClickDropDownTopupDetailByUserItem {
        void onClick(int clickPosition,  GetTopupDetailsByUserIdData item);
    }

    public interface ClickDropDownAllowedWalletItem {
        void onClick(int clickPosition, AllowedWallet item);
    }
}
