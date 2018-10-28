package com.simon.smarttourguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simon.smarttourguide.R;
import com.simon.smarttourguide.activities.MapActivity;
import com.simon.smarttourguide.model.PlacesLoc;
import com.tomtom.online.sdk.map.TomtomMap;

import java.util.ArrayList;



public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.Holder> {





    Context context;
    PlacesLoc placesLoc;
    public PlacesAdapter(PlacesLoc placesLoc, Context applicationContext) {
        this.placesLoc = placesLoc;
        context = applicationContext;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_loc, parent, false);
        return new Holder(v);
    }



    @Override
    public void onBindViewHolder(final Holder holder, final int pos) {
        //prod = prods.menu.get(0).products;

        final int position = pos;

        holder.relativeParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, MapActivity.class));
            }
        });
//        final History.OHistory oHistory = orderHistory.Orders.get(position);
//        holder.textViewOrder_placed_on.setText("Date: " + oHistory.Order_placed_on);




    }

    @Override
    public int getItemCount() {
        return placesLoc.places.size();
    }


    public static class Holder extends RecyclerView.ViewHolder {
        public TextView textViewPlace;
        RelativeLayout relativeParent;

        public Holder(View itemView) {
            super(itemView);
            relativeParent =itemView.findViewById(R.id.relativeParent);

//            textViewPlace = (TextView) itemView.findViewById(R.id.textViewProduct_Price);




        }
    }

}
