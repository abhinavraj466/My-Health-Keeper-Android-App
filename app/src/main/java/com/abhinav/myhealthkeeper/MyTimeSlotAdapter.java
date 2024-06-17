package com.abhinav.myhealthkeeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {

    Context context;
    List<TimeSlotModel> timeSlotList;


    public MyTimeSlotAdapter(Context context, List<TimeSlotModel> timeSlotList) {
        this.context = context;
        this.timeSlotList = timeSlotList;
    }



    @NonNull
    @Override
    public MyTimeSlotAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).
                inflate(R.layout.time_slot, parent, false);
        return new MyViewHolder(itemview);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_time_slot.setText(new StringBuilder(Common.convertTimeSlotToString(position).toString()));
        if (timeSlotList.size() == 0)  // if all position is available, show list
        {
            holder.txt_time_slot_description.setText("Available");
            holder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.black));

        }

        else  // if position is booked
        {
            for (TimeSlotModel slotValue : timeSlotList)
            {
                int slot;
                slot = Integer.parseInt(slotValue.getSlot().toString());

                if (slot == position)
                {
                    holder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                    holder.txt_time_slot_description.setText("Full");
                    holder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.white));
                    holder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.white));
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return Common.TIME_SLOT_TOTAL;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_time_slot, txt_time_slot_description;
        CardView card_time_slot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_time_slot = itemView.findViewById(R.id.card_time_slot);
            txt_time_slot = itemView.findViewById(R.id.txt_time_slot);
            txt_time_slot_description = itemView.findViewById(R.id.txt_time_slot_description);
        }
    }
}
