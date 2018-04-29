package com.bkset.vutuan.stour.fragment;


import android.app.ActionBar;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bkset.vutuan.stour.R;
import com.bkset.vutuan.stour.adapter.CustomAdapterHotel;
import com.bkset.vutuan.stour.adapter.CustomAdapterLocation;
import com.bkset.vutuan.stour.model.LocationCategory;
import com.bkset.vutuan.stour.ultilities.Contants;
import com.bkset.vutuan.stour.ultilities.DownloadJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelFragment extends Fragment {
    private RecyclerView rvHotel;
    private CustomAdapterHotel adapter;
    private ArrayList<LocationCategory> list;

    static String urlGetAllHotel="http://stour.sanslab.vn/api/hotel/GetByAll";

    android.support.v7.app.ActionBar actionBar;
    public static android.support.v4.app.FragmentManager manager;

    public HotelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=null;
        Bundle bundle=getArguments();
        String flag=bundle.getString(Contants.FLAG);
        view=inflater.inflate(R.layout.fragment_hotel, container, false);
        setHasOptionsMenu(true);

        actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        manager=getActivity().getSupportFragmentManager();

        //init widget
        rvHotel=view.findViewById(R.id.rvHotel);


        if (flag.equals(Contants.HOTEL)){
            setupAdapter(Contants.HOTEL);
            getListHotel(Contants.urlGetAllHotel);
            actionBar.setTitle("Khách sạn");
        } else if (flag.equals(Contants.RESTAURANT)){
            setupAdapter(Contants.RESTAURANT);
            getListHotel(Contants.urlGetAtllRestaurant);
            actionBar.setTitle("Nhà hàng");
        } else if (flag.equals(Contants.CULTURAL)){
            setupAdapter(Contants.CULTURAL);
            getListHotel(Contants.urlGetAllCultural);
            actionBar.setTitle("Văn hóa lễ hội");
        } else if(flag.equals(Contants.FOOD)){
            setupAdapter(Contants.FOOD);
            getListHotel(Contants.urlGetAllFood);
            actionBar.setTitle("Văn hóa ẩm thực");
        } else if(flag.equals(Contants.LOCATION)){
            setupAdapter(Contants.LOCATION);
            getListHotel(Contants.urlGetAllLocation);
            actionBar.setTitle("Di tích lịch sử");
        }

        return view;
    }

    private void setupAdapter(String flag){
        //setup recyclerview
        list=new ArrayList<>();
        adapter=new CustomAdapterHotel(getContext(),list,flag);
        rvHotel.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHotel.setHasFixedSize(true);
        rvHotel.setAdapter(adapter);
    }

    private void getListHotel(String url){
        final RequestQueue requestQueue= DownloadJSON.getInstance(getContext()).getRequestQue();
        final String flag=getArguments().getString(Contants.FLAG);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject rootLC=new JSONObject(response);
                    String status=rootLC.getString("Status");
                    String message=rootLC.getString("Message");
                    if (status.equals("1") && message.equals("Success")){
                        JSONArray rootArrayLC=rootLC.getJSONArray("Data");
                        int length=rootArrayLC.length();
                        if (length==0){

                        } else {
                            for (int i=0;i<length;i++){
                                JSONObject lc=rootArrayLC.getJSONObject(i);
                                String name=lc.getString("Name");
                                int id=lc.getInt("Id");
                                String address=lc.getString("Address");
                                String shortDes=lc.getString("ShortDes");
                                String longDes=lc.getString("LongDes");
                                double longitude=lc.getDouble("Longitude");
                                double latitude=lc.getDouble("Latitude");
                                String avatar=lc.getString("Avatar");
                                int star=lc.getInt("Star");
                                int viewCount=lc.getInt("ViewCount");
                                int price=0;
                                if (flag.equals(Contants.HOTEL) || flag.equals(Contants.RESTAURANT))
                                    price=lc.getInt("Price");
                                LocationCategory locationCategory=new LocationCategory(id,name,address,shortDes,longDes,longitude,latitude,avatar,star,viewCount,price);
                                list.add(locationCategory);
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


}
