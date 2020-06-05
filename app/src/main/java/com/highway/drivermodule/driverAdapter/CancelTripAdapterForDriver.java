package com.highway.drivermodule.driverAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.highway.R;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CancelTrip;
import com.highway.customer.customerActivity.CancelTripDetailsForCustomerActivity;
import com.highway.drivermodule.driverActivity.CancelTripDetailsByDriverActivity;
import com.highway.utils.Utils;

import java.util.List;

public class CancelTripAdapterForDriver extends RecyclerView.Adapter<CancelTripAdapterForDriver.MyViewHolder> {


    private List<CancelTrip> cancelTrips;
    private Context context;
    CancleTripDetailInterface cancleTripDetailInterface;

    public CancelTripAdapterForDriver(List<CancelTrip> cancelTrips, Context context) {
        this.cancelTrips = cancelTrips;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CancelTrip cancelTrip = cancelTrips.get(position);

        LatLng sourceAddLatLng = new LatLng(Double.parseDouble(cancelTrip.getSourceLat()), Double.parseDouble(cancelTrip.getSourceLong()));
        LatLng destAddLatLng = new LatLng(Double.parseDouble(cancelTrip.getDestinationLat()), Double.parseDouble(cancelTrip.getDestinationLong()));

        holder.tv1CompleteDate.setText(" " + cancelTrip.getEndDate());
        holder.tv2SourceAddress.setText(" " + Utils.getAddress(context, sourceAddLatLng));
        holder.tv4DestAddress.setText(" " + Utils.getAddress(context, destAddLatLng));
        holder.tv3SourceTime.setText("" + cancelTrip.getPickupTime());
        holder.tv5DestTime.setText("" + cancelTrip.getDropTime());
        holder.tv6VehicleName.setText("" + cancelTrip.getVehicleName());
        holder.tv7FairCharge.setText("" + cancelTrip.getFare());
        // img


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click success", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, CancelTripDetailsByDriverActivity.class);
                intent.putExtra("sourceLat",cancelTrips.get(holder.getAdapterPosition()).getSourceLat());
                intent.putExtra("sourceLong",cancelTrips.get(holder.getAdapterPosition()).getSourceLong());
                intent.putExtra("destinationLat",cancelTrips.get(holder.getAdapterPosition()).getDestinationLat());
                intent.putExtra("destinationLong",cancelTrips.get(holder.getAdapterPosition()).getDestinationLong());
                intent.putExtra("name",cancelTrips.get(holder.getAdapterPosition()).getName());
                intent.putExtra("role",cancelTrips.get(holder.getAdapterPosition()).getRole());
                intent.putExtra("vehicleName",cancelTrips.get(holder.getAdapterPosition()).getVehicleName());
                intent.putExtra("vehicleNumber",cancelTrips.get(holder.getAdapterPosition()).getVehicleNumber());
                intent.putExtra("fare",cancelTrips.get(holder.getAdapterPosition()).getFare());
                intent.putExtra("status",cancelTrips.get(holder.getAdapterPosition()).getStatus());
                intent.putExtra("tripType",cancelTrips.get(holder.getAdapterPosition()).getTripType());
                intent.putExtra("startDate",cancelTrips.get(holder.getAdapterPosition()).getStartDate());
                intent.putExtra("endDate",cancelTrips.get(holder.getAdapterPosition()).getEndDate());
                intent.putExtra("pickupTime",cancelTrips.get(holder.getAdapterPosition()).getPickupTime());
                intent.putExtra("dropTime",cancelTrips.get(holder.getAdapterPosition()).getDropTime());
                context.startActivity(intent);

               /* if (cancleTripDetailInterface != null) {
                    cancleTripDetailInterface.TripDetailListClick(
                            cancelTrips.get(holder.getAdapterPosition()).getSourceLat(),
                            cancelTrips.get(holder.getAdapterPosition()).getSourceLong(),
                            cancelTrips.get(holder.getAdapterPosition()).getDestinationLat(),
                            cancelTrips.get(holder.getAdapterPosition()).getDestinationLong(),
                            cancelTrips.get(holder.getAdapterPosition()).getName(),
                            cancelTrips.get(holder.getAdapterPosition()).getRole(),
                            cancelTrips.get(holder.getAdapterPosition()).getVehicleName(),
                            cancelTrips.get(holder.getAdapterPosition()).getVehicleNumber(),
                            cancelTrips.get(holder.getAdapterPosition()).getFare(),
                            cancelTrips.get(holder.getAdapterPosition()).getStatus(),
                            cancelTrips.get(holder.getAdapterPosition()).getTripType(),
                            cancelTrips.get(holder.getAdapterPosition()).getStartDate(),
                            cancelTrips.get(holder.getAdapterPosition()).getEndDate(),
                            cancelTrips.get(holder.getAdapterPosition()).getPickupTime(),
                            cancelTrips.get(holder.getAdapterPosition()).getDropTime()
                    );
                }*/

            }
        });

    }

    public void setTripDetailListInterface (CancelTripAdapterForDriver.CancleTripDetailInterface cancleTripDetailInterface ){
        this.cancleTripDetailInterface = cancleTripDetailInterface;
    }

    public interface CancleTripDetailInterface {
        void TripDetailListClick(String sourceLat, String sourceLong, String destinationLat, String destinationLong,
                                 String name, String role, String vehicleName, String vehicleNumber, String fare,
                                 String status, String tripType, String startDate, String endDate, String pickupTime,
                                 String dropTime
        );


    }

    @Override
    public int getItemCount() {
        return cancelTrips.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1CompleteDate, tv2SourceAddress, tv3SourceTime, tv4DestAddress, tv5DestTime, tv6VehicleName, tv7FairCharge;
        private ImageView img1SourceIndicator, img2DestIndicator, img3_gmap_locator, img4VehicleImg;
        private  LinearLayout linearLayout;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1CompleteDate = itemView.findViewById(R.id.Tv1CompleteDate);
            tv2SourceAddress = itemView.findViewById(R.id.Tv2SourceAddress);
            tv3SourceTime = itemView.findViewById(R.id.Tv3SourceTime);
            tv4DestAddress = itemView.findViewById(R.id.Tv4DestAddress);
            tv5DestTime = itemView.findViewById(R.id.Tv5DestTime);
            tv6VehicleName = itemView.findViewById(R.id.Tv6VehicleName);
            tv7FairCharge = itemView.findViewById(R.id.Tv7FairCharge);
            img1SourceIndicator = itemView.findViewById(R.id.Img1SourceIndicator);
            img2DestIndicator = itemView.findViewById(R.id.Img2DestIndicator);
            img4VehicleImg = itemView.findViewById(R.id.Img4VehicleImg);
             cardView = itemView.findViewById(R.id.CardView);
            linearLayout=itemView.findViewById(R.id.LLout1);
        }
    }
}
