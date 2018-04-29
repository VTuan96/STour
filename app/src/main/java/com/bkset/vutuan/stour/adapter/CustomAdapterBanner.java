package com.bkset.vutuan.stour.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bkset.vutuan.stour.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by vutuan on 27/04/2018.
 */

public class CustomAdapterBanner extends RecyclerView.Adapter<CustomAdapterBanner.Hoder>{
    private Context context;
    private ArrayList<String> list;
    private ImageView imageView;

    public CustomAdapterBanner(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    public CustomAdapterBanner(Context context, ArrayList<String> list, ImageView imageView) {
        this.context = context;
        this.list = list;
        this.imageView = imageView;
    }

    @NonNull
    @Override
    public Hoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_item_banner,parent,false);
        Hoder hoder=new Hoder(view);
        return hoder;
    }

    @Override
    public void onBindViewHolder(@NonNull Hoder holder, int position) {
        final String url=list.get(position);
        Glide.with(context)
                .load(url)
                .into(holder.imgItemBanner);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Hoder extends RecyclerView.ViewHolder{
        private ImageView imgItemBanner;

        public Hoder(View itemView) {
            super(itemView);
            imgItemBanner=itemView.findViewById(R.id.imgItemBanner);
        }
    }
}
