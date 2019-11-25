package com.highway.ownermodule.owner.owneradapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.highway.ownermodule.owner.ownerfragment.VehicleCancelFragment;
import com.highway.ownermodule.owner.ownerfragment.VehicleCompletedFragment;
import com.highway.ownermodule.owner.ownerfragment.VehicleOnGoingFragment;
import com.highway.ownermodule.owner.ownerfragment.VehiclePendingFragment;
import com.highway.ownermodule.owner.ownerfragment.VehicleUpComingFragment;

import java.util.List;

public class VehicleOwnerAdapter extends FragmentPagerAdapter {
    List<Fragment> vehicleOwnerfragment;

    public VehicleOwnerAdapter(@NonNull FragmentManager fm,  List<Fragment> fragments) {
        super(fm);
        this.vehicleOwnerfragment =fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return vehicleOwnerfragment.get(position);
    }

    @Override
    public int getCount() {
        return vehicleOwnerfragment.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (vehicleOwnerfragment.get(position)instanceof VehicleUpComingFragment){
            return "UPCOMING";
        }else if (vehicleOwnerfragment.get(position)instanceof VehicleOnGoingFragment){
            return "ONGOING";
        }else if (vehicleOwnerfragment.get(position)instanceof VehiclePendingFragment){
            return "PENDING";
        }else if (vehicleOwnerfragment.get(position)instanceof VehicleCompletedFragment){
            return "COMPLETED";
        }if (vehicleOwnerfragment.get(position)instanceof VehicleCancelFragment){
            return "CANCEL";
        }
        return " ";
        //return super.getPageTitle(position);
    }
}
