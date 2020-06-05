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
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CompletedTrip;
import com.highway.customer.customerActivity.CompletedTripDetailsForCustomersActivity;
import com.highway.customer.customerAdapter.UpComingTripAdapterForCustomer;
import com.highway.drivermodule.driverActivity.CompletedTripDetailsForDriverActivity;
import com.highway.utils.Utils;

import java.util.List;

public class OnCompletedTripAdapterForDriver extends RecyclerView.Adapter<OnCompletedTripAdapterForDriver.MyViewHolder> {

    private List<CompletedTrip> completedTrips;
    private Context context;
    OnCompletedTripAdapterForDriver.TripDetailListInterface tripDetailListInterface;


    public OnCompletedTripAdapterForDriver(List<CompletedTrip> completedTrips, Context context) {
        this.completedTrips = completedTrips;
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

        CompletedTrip completedTrip = completedTrips.get(position);

        LatLng sourceAddLatLng = new LatLng(Double.parseDouble(completedTrip.getSourceLat()), Double.parseDouble(completedTrip.getSourceLong()));
        LatLng destAddLatLng = new LatLng(Double.parseDouble(completedTrip.getDestinationLat()), Double.parseDouble(completedTrip.getDestinationLong()));
        holder.tv1CompleteDate.setText(" " + completedTrip.getEndDate());
        holder.tv2SourceAddress.setText(" " + Utils.getAddress(context, sourceAddLatLng));
        holder.tv4DestAddress.setText(" " + Utils.getAddress(context, destAddLatLng));
        holder.tv3SourceTime.setText("" + completedTrip.getPickupTime());
        holder.tv5DestTime.setText("" + completedTrip.getDropTime());
        holder.tv6VehicleName.setText("" + completedTrip.getVehicleName());
        holder.tv7FairCharge.setText("" + completedTrip.getFare());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CompletedTripDetailsForDriverActivity.class);

                intent.putExtra("sourceLat",completedTrips.get(holder.getAdapterPosition()).getSourceLat());
                intent.putExtra("sourceLong",completedTrips.get(holder.getAdapterPosition()).getSourceLong());
                intent.putExtra("destinationLat",completedTrips.get(holder.getAdapterPosition()).getDestinationLat());
                intent.putExtra("destinationLong",completedTrips.get(holder.getAdapterPosition()).getDestinationLong());
                intent.putExtra("name",completedTrips.get(holder.getAdapterPosition()).getName());
                intent.putExtra("role",completedTrips.get(holder.getAdapterPosition()).getRole());
                intent.putExtra("vehicleName",completedTrips.get(holder.getAdapterPosition()).getVehicleName());
                intent.putExtra("vehicleNumber",completedTrips.get(holder.getAdapterPosition()).getVehicleNumber());
                intent.putExtra("fare",completedTrips.get(holder.getAdapterPosition()).getFare());
                intent.putExtra("status",completedTrips.get(holder.getAdapterPosition()).getStatus());
                intent.putExtra("tripType",completedTrips.get(holder.getAdapterPosition()).getTripType());
                intent.putExtra("startDate",completedTrips.get(holder.getAdapterPosition()).getStartDate());
                intent.putExtra("endDate",completedTrips.get(holder.getAdapterPosition()).getEndDate());
                intent.putExtra("pickupTime",completedTrips.get(holder.getAdapterPosition()).getPickupTime());
                intent.putExtra("dropTime",completedTrips.get(holder.getAdapterPosition()).getDropTime());

                context.startActivity(intent);

                /*if (tripDetailListInterface!=null){
                    tripDetailListInterface.TripDetailListClick(
                            completedTrips.get(holder.getAdapterPosition()).getSourceLat(),
                            completedTrips.get(holder.getAdapterPosition()).getSourceLong(),
                            completedTrips.get(holder.getAdapterPosition()).getDestinationLat(),
                            completedTrips.get(holder.getAdapterPosition()).getDestinationLong(),
                            completedTrips.get(holder.getAdapterPosition()).getName(),
                            completedTrips.get(holder.getAdapterPosition()).getRole(),
                            completedTrips.get(holder.getAdapterPosition()).getVehicleName(),
                            completedTrips.get(holder.getAdapterPosition()).getVehicleNumber(),
                            completedTrips.get(holder.getAdapterPosition()).getFare(),
                            completedTrips.get(holder.getAdapterPosition()).getStatus(),
                            completedTrips.get(holder.getAdapterPosition()).getTripType(),
                            completedTrips.get(holder.getAdapterPosition()).getStartDate(),
                            completedTrips.get(holder.getAdapterPosition()).getEndDate(),
                            completedTrips.get(holder.getAdapterPosition()).getPickupTime(),
                            completedTrips.get(holder.getAdapterPosition()).getDropTime()
                    );
                }*/

            }
        });

    }

    public void setTripDetailListInterface(TripDetailListInterface tripDetailListInterface) {
        this.tripDetailListInterface = tripDetailListInterface;
    }

    public interface TripDetailListInterface {
         void TripDetailListClick(String sourceLat, String sourceLong, String destinationLat, String destinationLong,
                                   String name, String role, String vehicleName, String vehicleNumber, String fare,
                                   String status, String tripType, String startDate, String endDate, String pickupTime,
                                   String dropTime);


    }

    @Override
    public int getItemCount() {
        return completedTrips.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1CompleteDate, tv2SourceAddress, tv3SourceTime, tv4DestAddress, tv5DestTime, tv6VehicleName, tv7FairCharge;
        private ImageView img1SourceIndicator, img2DestIndicator, img3_gmap_locator, img4VehicleImg;
        public CardView cardView;
        public LinearLayout linearLayout;

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
