package com.highway.ownerModule.vehicleOwnerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.highway.BuildConfig;
import com.highway.R;
import com.highway.ownerModule.vehicleOwnerActivities.GetAllVehicleDetailsActivity;
import com.highway.ownerModule.vehicleOwnerfragment.GetAllVehicleFragmentForVehicleOwner;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.VehicleDetail;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GetAllVehicleAdapterForVehicleOwner extends RecyclerView.Adapter<GetAllVehicleAdapterForVehicleOwner.ViewHolder> {

    ArrayList<VehicleDetail> vehicleDetails;
    Context context;
    GetAllVehicleFragmentForVehicleOwner getAllVehicleFragmentForVehicleOwner;

    public GetAllVehicleAdapterForVehicleOwner(ArrayList<VehicleDetail> vehicleDetails1, Context context1, GetAllVehicleFragmentForVehicleOwner getAllVehicleFragmentForVehicleOwner) {
        this.context = context1;
        this.vehicleDetails = vehicleDetails1;
        this.getAllVehicleFragmentForVehicleOwner = getAllVehicleFragmentForVehicleOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_get_all_vehicle__item_fragment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        VehicleDetail vehicleDetail = vehicleDetails.get(position);
        holder.tv1DriverName.setText("" + vehicleDetail.getDriverName());
        holder.tv2VehicleName.setText("" + vehicleDetail.getVehicleName());
        holder.tv3VehicleNumber.setText("" + vehicleDetail.getVehicleNumber());
        holder.tv4VehicleModelNo.setText("" + vehicleDetail.getVehicleModelNo());
        holder.tv5VehicleLoadCapicity.setText("" + vehicleDetail.getVehicleCapacity());
        holder.tv6VehicleSize.setText("" + vehicleDetail.getVehicleSize());
        holder.tv7VehicleDescription.setText("" + vehicleDetail.getVehicleDescription());


        Picasso.with(context).load(BuildConfig.API_SERVER_IP + vehicleDetail.getVehicleImage())
                .error(R.mipmap.ic_launcher)
                .into(holder.image);


        if (vehicleDetail.getVehicleOnOff().equalsIgnoreCase("ON")) {
            holder.toggle.setChecked(true);
        } else {
            holder.toggle.setChecked(false);
        }

      /*  holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    getAllVehicleFragmentForVehicleOwner.getonlineoffline(HighwayPrefs.getString(context, Constants.ID), vehicleDetail.getVehicleId(), "ON");


                } else {
                    getAllVehicleFragmentForVehicleOwner.getonlineoffline(HighwayPrefs.getString(context, Constants.ID), vehicleDetail.getVehicleId(), "OFF");


                }
            }
        });*/
        holder.toggle.setOnClickListener(new View.OnClickListener() {

            //please note that objPlace, position and holder must be declared
            //as final inside the getView() function scope.
            @Override
            public void onClick(View arg0) {
                final boolean isChecked = holder.toggle.isChecked();
                if (isChecked) {

                    getAllVehicleFragmentForVehicleOwner.getonlineoffline(HighwayPrefs.getString(context, Constants.ID), vehicleDetail.getVehicleId(), "ON");


                } else {
                    getAllVehicleFragmentForVehicleOwner.getonlineoffline(HighwayPrefs.getString(context, Constants.ID), vehicleDetail.getVehicleId(), "OFF");


                }

            }
        });

        holder.llCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), GetAllVehicleDetailsActivity.class);

                i.putExtra("Latitude", vehicleDetail.getLatitude());
                i.putExtra("Longitude", vehicleDetail.getLongitude());
                i.putExtra("VehicleName", vehicleDetail.getVehicleName());

                i.putExtra("VehicleNumber", vehicleDetail.getVehicleNumber());
                i.putExtra("VehicleModelNo", vehicleDetail.getVehicleModelNo());
                i.putExtra("VehicleDescription", vehicleDetail.getVehicleDescription());

                i.putExtra("VehicleCapacity", vehicleDetail.getVehicleCapacity());
                i.putExtra("VehicleSize", vehicleDetail.getVehicleSize());
                i.putExtra("VehicleImage", vehicleDetail.getVehicleImage());

                i.putExtra("DriverName", vehicleDetail.getDriverName());
                i.putExtra("Mobile", vehicleDetail.getMobile());
                i.putExtra("Email", vehicleDetail.getEmail());
                i.putExtra("pos", "" + position);
                view.getContext().startActivity(i);



               /* if(vehicleDetail.getLatitude() != null && !vehicleDetail.getLatitude().isEmpty()) {
                    Intent i = new Intent(view.getContext(), GetAllVehicleDetailsActivity.class);
                    i.putExtra("lat", vehicleDetail.getLatitude());
                    i.putExtra("long", vehicleDetail.getLongitude());
                    i.putExtra("vehicalName", vehicleDetail.getVehicleName());
                    view.getContext().startActivity(i);
                }else {

                    Toast.makeText(view.getContext(),"latitude and longitude not available", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
        // load vihicle img
    }

    @Override
    public int getItemCount() {
        return vehicleDetails.size();
    }

    public void setData(ArrayList<VehicleDetail> vehicleDetails) {

        this.vehicleDetails = vehicleDetails;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1DriverName, tv2VehicleName, tv3VehicleNumber, tv4VehicleModelNo, tv5VehicleLoadCapicity, tv6VehicleSize, tv7VehicleDescription;
        private ImageView img1VehicleImg, image;
        LinearLayout llCard;
        Switch toggle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1DriverName = itemView.findViewById(R.id.Tv1DriverName);
            tv2VehicleName = itemView.findViewById(R.id.Tv2VehicleName);
            tv3VehicleNumber = itemView.findViewById(R.id.Tv3VehicleNo);
            tv4VehicleModelNo = itemView.findViewById(R.id.Tv4VehicleModelNo);
            tv5VehicleLoadCapicity = itemView.findViewById(R.id.Tv5VehicleLoadCapicity);
            tv6VehicleSize = itemView.findViewById(R.id.Tv6VehicleSize);
            tv7VehicleDescription = itemView.findViewById(R.id.Tv7VehicleDescription);
            llCard = itemView.findViewById(R.id.llCard);
            image = itemView.findViewById(R.id.image);
            toggle = itemView.findViewById(R.id.toggle);
        }
    }
}
