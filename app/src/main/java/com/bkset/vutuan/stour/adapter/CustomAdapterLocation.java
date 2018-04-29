package com.bkset.vutuan.stour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkset.vutuan.stour.R;
import com.bkset.vutuan.stour.model.LocationCategory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;

/**
 * Created by vutuan on 14/04/2018.
 */

public class CustomAdapterLocation extends RecyclerView.Adapter<CustomAdapterLocation.Holder>{
    public Context mContext;
    public ArrayList<LocationCategory> mList;

    public CustomAdapterLocation(Context mContext, ArrayList<LocationCategory> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.layout_item_location,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        LocationCategory locationCategory=mList.get(position);
        Glide.with(mContext)
                .load(locationCategory.getAvatar().toString())
                .into(holder.imgItemLocation);
        holder.txtItemLocation.setText(locationCategory.getName());
        System.out.println(locationCategory.getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ImageView imgItemLocation;
        private TextView txtItemLocation;

        public Holder(View itemView) {
            super(itemView);
            imgItemLocation=itemView.findViewById(R.id.imgItemLocation);
            txtItemLocation=itemView.findViewById(R.id.txtItemLocation);
        }
    }
}
