package com.highway.customer.customerActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.highway.R;
import com.highway.customer.customerModelClass.bookingVehicleList.BookingVehicleListResponse;
import com.highway.customer.customerModelClass.bookingVehicleList.VehicleList;

import java.util.ArrayList;
import java.util.List;

import static com.highway.utils.Constants.CAR_TRANSPORTER;
import static com.highway.utils.Constants.PICKUP;
import static com.highway.utils.Constants.SEMI_TRAILER;
import static com.highway.utils.Constants.TATA_ACE;
import static com.highway.utils.Constants.TATA_MAGIC;
import static com.highway.utils.Constants.TRUCK;

public class BookingVehicleAdapter extends RecyclerView.Adapter<BookingVehicleAdapter.ViewHolder> {
    private Context context;

    OnClickEvents onClickEvents;
    BookingVehicleListResponse bookingVehicleListResponse;
    List<ImageView> imageViewList = new ArrayList<>();
    List<View> itemViewList = new ArrayList<>();

    public int previousPosition = -1;
    public SingleViewItemBinding previousView;
    private int row_index;

    public BookingVehicleAdapter(BookingVehicleListResponse bookingVehicleListResponse1, Context context1, OnClickEvents onClickEvents1) {
        this.context = context1;
        this.bookingVehicleListResponse = bookingVehicleListResponse1;
        this.onClickEvents = onClickEvents1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_vehicle_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // holder.tv1.setText(android_versionnames[position]);
        VehicleList vehicleList = bookingVehicleListResponse.getVehicleData().getVehicleList().get(position);

        holder.tataAceTv1.setText("" + vehicleList.getVehicleName());
        holder.faireChargeTv3.setText("\u20B9" + vehicleList.getVehicleFare());
        holder.timeDurationTv2.setText(vehicleList.getTimeDuration());



        Log.d("Data" + vehicleList.getVehicleName(), "" + vehicleList.isSelected());

        holder.infoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickEvents != null) {
                    onClickEvents.onCLickInfo(holder.getAdapterPosition());
                }
            }
        });


        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index = position;

                updateItems(holder);
                bookingVehicleListResponse.getVehicleData().getVehicleList().get(row_index).setSelected(true);

                notifyDataSetChanged();


                if (onClickEvents != null) {
                    onClickEvents.onCLickTruck(position,
                            vehicleList.getVehicleFare());

                }
            }
        });



        switch (bookingVehicleListResponse.getVehicleData().getVehicleList().get(position).getVehicleType()){
            case PICKUP :

                if (bookingVehicleListResponse.getVehicleData().getVehicleList().get(position).isSelected()) {

                    holder.vehicleIcons.setImageResource(R.drawable.v_pickup_selected);
                } else {
                    holder.vehicleIcons.setImageResource(R.drawable.v_pickup_un_selected);
                }
                break;

            case CAR_TRANSPORTER :

                if (bookingVehicleListResponse.getVehicleData().getVehicleList().get(position).isSelected()) {

                    holder.vehicleIcons.setImageResource(R.drawable.v_car_transport_selected);
                } else {
                    holder.vehicleIcons.setImageResource(R.drawable.v_car_transport_un_selected);
                }
                break;

            case TATA_MAGIC :

                if (bookingVehicleListResponse.getVehicleData().getVehicleList().get(position).isSelected()) {

                    holder.vehicleIcons.setImageResource(R.drawable.v_tata_magic_selected);
                } else {
                    holder.vehicleIcons.setImageResource(R.drawable.v_tata_magic_un_selected);
                }
                break;

            case SEMI_TRAILER :

                if (bookingVehicleListResponse.getVehicleData().getVehicleList().get(position).isSelected()) {

                    holder.vehicleIcons.setImageResource(R.drawable.v_semi_trailer_selected);
                } else {
                    holder.vehicleIcons.setImageResource(R.drawable.v_semi_trailer_un_selected);
                }
                break;

            case TRUCK :

                if (bookingVehicleListResponse.getVehicleData().getVehicleList().get(position).isSelected()) {

                    holder.vehicleIcons.setImageResource(R.drawable.v_truck_selected);
                } else {
                    holder.vehicleIcons.setImageResource(R.drawable.v_truck_un_selected);
                }
                break;


            case TATA_ACE :
                if (bookingVehicleListResponse.getVehicleData().getVehicleList().get(position).isSelected()) {

                    holder.vehicleIcons.setImageResource(R.drawable.v_tata_ace_selected);
                } else {
                    holder.vehicleIcons.setImageResource(R.drawable.v_tata_ace_un_selected);
                }
                break;
        }
      //  holder.vehicleIcons.setImageResource(R.drawable.v_tata_ace_un_selected);



    }

    private void updateItems(ViewHolder holder) {
        for (int i = 0; i < bookingVehicleListResponse.getVehicleData().getVehicleList().size(); i++) {
            bookingVehicleListResponse.getVehicleData().getVehicleList().get(i).setSelected(false);

        }
    }

    @Override
    public int getItemCount() {
        if (bookingVehicleListResponse != null
                && bookingVehicleListResponse.getVehicleData().getVehicleList() != null
                && bookingVehicleListResponse.getVehicleData().getVehicleList().size() > 0) {
            return bookingVehicleListResponse.getVehicleData().getVehicleList().size();
        } else {
            return 0;
        }
    }

    public void setOnClickEvents(OnClickEvents onClickEvents) {
        this.onClickEvents = onClickEvents;
    }

    public void setData(BookingVehicleListResponse bookingVehicleListResponse) {
        this.bookingVehicleListResponse = bookingVehicleListResponse;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tataAceTv1, timeTv2, faireChargeTv3,timeDurationTv2;
        ImageView vehicleIcons, infoImg;
        LinearLayout row_linearlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tataAceTv1 = itemView.findViewById(R.id.tataAceTv1);
            timeTv2 = itemView.findViewById(R.id.timeDurationTv2);
            faireChargeTv3 = itemView.findViewById(R.id.faireChargeTv3);
            vehicleIcons = itemView.findViewById(R.id.vehicleImg1);
            infoImg = itemView.findViewById(R.id.infoImg);
            row_linearlayout = itemView.findViewById(R.id.Llayout1);
            timeDurationTv2 = itemView.findViewById(R.id.timeDurationTv2);

        }
    }

    public interface OnClickEvents {
        void onCLickInfo(int position);

        void onCLickTruck(int position, String vehicleFare);

    }
}
