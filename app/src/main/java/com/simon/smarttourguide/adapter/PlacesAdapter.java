package com.simon.smarttourguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simon.smarttourguide.R;
import com.simon.smarttourguide.activities.MapActivity;
import com.simon.smarttourguide.activities.PlacesActivity;
import com.simon.smarttourguide.model.PlacesLoc;
import com.tomtom.online.sdk.map.TomtomMap;

import java.util.ArrayList;



public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.Holder> {





    Context context;
    PlacesLoc placesLoc;
    PlacesActivity PlacesActivity;
    public PlacesAdapter(PlacesLoc placesLoc, Context applicationContext, PlacesActivity placesActivity) {
        this.placesLoc = placesLoc;
        context = applicationContext;
        this.PlacesActivity =placesActivity;
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
        final PlacesLoc.Place data = placesLoc.places.get(position);


        holder.textViewPlace.setText(data.placeName);

        holder.placePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked","sds");
              context.startActivity(new Intent(PlacesActivity.getApplicationContext(), MapActivity.class));
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
        public RelativeLayout relativeParent;
        public CardView card_view;
        public ImageView placePic;

        public Holder(View itemView) {
            super(itemView);
            textViewPlace = (TextView)itemView.findViewById(R.id.textViewPlace);
            relativeParent =(RelativeLayout) itemView.findViewById(R.id.relativeParent);
            card_view = (CardView)itemView.findViewById(R.id.card_view);
            placePic =(ImageView)itemView.findViewById(R.id.placePic);

//            textViewPlace = (TextView) itemView.findViewById(R.id.textViewProduct_Price);




        }
    }

}
