package com.solution.app.justpay4u.Shopping.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentViewPagerShoppingAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    //FragmentManager mFragmentManager;
    //ArrayList<Fragment> mListFragments;

    public FragmentViewPagerShoppingAdapter(FragmentManager manager) {
        super(manager);
        // mFragmentManager =manager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void removeFragment() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }
    /*@Override
    public int getItemPosition(Object object) {
        Fragment mFragment=(Fragment)object;
      //  mListFragments=new ArrayList<>(mFragmentManager.getFragments());
        //if(mListFragments.contains(mFragment))
        return POSITION_NONE;
        *//*if(mFragment instanceof HomeItemFragment)
            return POSITION_NONE;
        else
            return POSITION_UNCHANGED;*//*
    }*/


}
