package com.highway.customer.customerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.highway.customer.customerFragment.CancelFragmentForCustomer;
import com.highway.customer.customerFragment.CompletedFragmentForCustomer;
import com.highway.customer.customerFragment.UpCommingFragmentForCustomer;

import java.util.List;

public class FragmentTabModeAdapterForCustomer extends FragmentPagerAdapter {
    List<Fragment>customerfragments;

    public FragmentTabModeAdapterForCustomer(FragmentManager fm, List<Fragment>fragments) {
        super(fm);
        this.customerfragments =fragments;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return customerfragments.get(position);
    }

    @Override
    public int getCount() {
        return customerfragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (customerfragments.get(position)instanceof UpCommingFragmentForCustomer){
            return "UPCOMING";
        }else if (customerfragments.get(position)instanceof CompletedFragmentForCustomer){
            return "COMPLETED";
        }if (customerfragments.get(position)instanceof CancelFragmentForCustomer){
            return "CANCEL";
        }
            return " ";
        //return super.getPageTitle(position);
    }
}
