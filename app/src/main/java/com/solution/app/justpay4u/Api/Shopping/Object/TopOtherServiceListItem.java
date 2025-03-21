package com.solution.app.justpay4u.Api.Shopping.Object;

public class TopOtherServiceListItem {

    int type;
    String name;
    int icon;

    public TopOtherServiceListItem() {

    }

    public TopOtherServiceListItem(int type, String name, int icon) {
        this.type = type;
        this.name = name;
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }


    /*public ArrayList<TopOtherServiceListItem> getList(boolean isRecharge, boolean isAddMoneyEnable, boolean isMoveWallet) {
        ArrayList<TopOtherServiceListItem> mTopOtherServiceListItems = new ArrayList<>();

        if (isAddMoneyEnable) {
            mTopOtherServiceListItems.add(new TopOtherServiceListItem(1, "Add Money", R.drawable.ic_add_money_icon));
        }
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(3, "Fund Request", R.drawable.ic_fund_request_icon));
        if (isRecharge) {
            if (UtilMethods.INSTANCE.mLoginResponse.getData().getRoleID().equalsIgnoreCase("3") ||
                    UtilMethods.INSTANCE.mLoginResponse.getData().getRoleID().equalsIgnoreCase("2")) {
                mTopOtherServiceListItems.add(new TopOtherServiceListItem(2, "Recharge & Bills", R.drawable.ic_recharge_bill_payment));
                mTopOtherServiceListItems.add(new TopOtherServiceListItem(8, "Recharge Report", R.drawable.ic_recharge_report_icon));

            } else {
                mTopOtherServiceListItems.add(new TopOtherServiceListItem(2, "Services & Reports", R.drawable.ic_recharge_bill_payment));

            }

        }

        if (UtilMethods.INSTANCE.mLoginResponse != null && (UtilMethods.INSTANCE.mLoginResponse.getData().getRoleID().equalsIgnoreCase("3") ||
                UtilMethods.INSTANCE.mLoginResponse.getData().getRoleID().equalsIgnoreCase("2"))) {
            mTopOtherServiceListItems.add(new TopOtherServiceListItem(7, "Reports", R.drawable.ic_transaction));

        }
        if (isMoveWallet) {
            mTopOtherServiceListItems.add(new TopOtherServiceListItem(4, "Move Balance", R.drawable.ic_move_balance));
        }
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(9, "Support", R.drawable.ic_support_icon));

        //
        *//*mTopOtherServiceListItems.add(new TopOtherServiceListItem(4, "Invite Friends", R.drawable.ic_invite_friend));*//*
        *//*mTopOtherServiceListItems.add(new TopOtherServiceListItem(5, "Offers", R.drawable.ic_offers));*//*
        *//*mTopOtherServiceListItems.add(new TopOtherServiceListItem(6, "Gold Member", R.drawable.ic_gold_member));*//*
        return mTopOtherServiceListItems;
    }*/
    /*public List<TopOtherServiceListItem> getServiceList() {
        List<TopOtherServiceListItem> mTopOtherServiceListItems = new ArrayList<>();

        mTopOtherServiceListItems.add(new TopOtherServiceListItem(1, "Prepaid", R.drawable.ic_prepaid));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(2, "Postpaid", R.drawable.ic_postpaid));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(3, "DTH", R.drawable.ic_dth_icon));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(4, "Electricity", R.drawable.ic_electricity_icon));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(5, "Broadband", R.drawable.ic_broadband));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(6, "Water", R.drawable.ic_water_icon));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(7, "Landline", R.drawable.ic_landline_icon));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(8, "Gas", R.drawable.ic_gas_icon));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(9, "Piped Gas", R.drawable.ic_piped_gas));
        mTopOtherServiceListItems.add(new TopOtherServiceListItem(10, "Life Insurance", R.drawable.ic_life_insurance));

        return mTopOtherServiceListItems;
    }*/

}
