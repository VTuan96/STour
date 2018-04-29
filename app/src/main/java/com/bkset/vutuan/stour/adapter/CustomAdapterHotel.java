package com.bkset.vutuan.stour.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bkset.vutuan.stour.R;
import com.bkset.vutuan.stour.fragment.DetailHotelFragment;
import com.bkset.vutuan.stour.fragment.HotelFragment;
import com.bkset.vutuan.stour.model.LocationCategory;
import com.bkset.vutuan.stour.ultilities.Contants;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by vutuan on 16/04/2018.
 */

public class CustomAdapterHotel extends RecyclerView.Adapter<CustomAdapterHotel.Holder> {
    private Context mContext;
    private ArrayList<LocationCategory> mList;
    private String flag;

    public CustomAdapterHotel(Context mContext, ArrayList<LocationCategory> mList, String flag) {
        this.mContext = mContext;
        this.mList = mList;
        this.flag=flag;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.layout_item_hotel,parent,false);
        Holder holder=new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final LocationCategory category=mList.get(position);
        holder.txtViewCount.setText(String.valueOf(category.getViewCount()));
        holder.txtNameHotel.setText(category.getName());
        holder.txtAddressHotel.setText(category.getAddress());
        if (category.getPrice()>0)
            holder.txtItemPrice.setText(category.getPrice()+"VNĐ");
        else holder.txtItemPrice.setText("Đang cập nhật!");
        Glide.with(mContext)
                .load(category.getAvatar())
                .into(holder.imgAvatarHotel);
        holder.rbStar.setRating(category.getStar());

        holder.cvItemHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= HotelFragment.manager.beginTransaction();
                DetailHotelFragment detailHotelFragment=new DetailHotelFragment();
                Bundle bundle=new Bundle();
                bundle.putInt(Contants.HOTEL_ID,category.getId());
                bundle.putString(Contants.FLAG,flag);
                detailHotelFragment.setArguments(bundle);
                transaction.addToBackStack("DetailHotelFragment");
                transaction.add(R.id.container,detailHotelFragment);
                transaction.commit();
            }
        });

        if (flag.equals(Contants.HOTEL)){
            holder.layout_services.setVisibility(View.VISIBLE);
        } else {
            holder.layout_services.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class Holder extends RecyclerView.ViewHolder{
        private ImageView imgAvatarHotel;
        private TextView txtNameHotel;
        private TextView txtAddressHotel;
        private TextView txtViewCount;
        private RatingBar rbStar;
        private CardView cvItemHotel;
        private TextView txtItemPrice;
        private FrameLayout layout_services;

        public Holder(View itemView) {
            super(itemView);
            imgAvatarHotel=itemView.findViewById(R.id.imgAvatarHotel);
            txtNameHotel=itemView.findViewById(R.id.txtNameHotel);
            txtAddressHotel=itemView.findViewById(R.id.txtAddressHotel);
            txtViewCount=itemView.findViewById(R.id.txtViewCount);
            rbStar=itemView.findViewById(R.id.rbStar);
            cvItemHotel=itemView.findViewById(R.id.cvItemHotel);
            txtItemPrice=itemView.findViewById(R.id.txtItemPrice);
            layout_services=itemView.findViewById(R.id.layout_services);
        }
    }
}
