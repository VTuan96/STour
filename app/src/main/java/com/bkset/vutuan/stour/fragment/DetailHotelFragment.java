package com.bkset.vutuan.stour.fragment;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bkset.vutuan.stour.HomeActivity;
import com.bkset.vutuan.stour.R;
import com.bkset.vutuan.stour.adapter.CustomAdapterImage;
import com.bkset.vutuan.stour.model.LocationCategory;
import com.bkset.vutuan.stour.ultilities.Contants;
import com.bkset.vutuan.stour.ultilities.DownloadJSON;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHotelFragment extends Fragment {
    android.support.v7.app.ActionBar actionBar;
    private ImageView imgDetailHotel;
    private RecyclerView rvImageDetailHotel;
    private TextView txtAddressDetailHotel, txtPhoneDetailHotel, txtDescriptionDetailHotel, txtNameDetailHotel;
    private ImageButton btnPhoneDetailHotel;
    private RatingBar rbDetailHotel;
    private GoogleMap map;

    private int hotelId;
    private static View view;

    private ArrayList<String> fileAttachs=new ArrayList<>();
    private CustomAdapterImage adapterImage;
    private String phoneNumber;

    public DetailHotelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view!=null){
            if (container != null)
                container.removeView(view);
        } else{
            try{
                view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail_hotel,container,false);
            }
            catch (InflateException ex)
            {
                ex.printStackTrace();
            }
        }


        initWidgets(view);

        actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Chi tiết khách sạn");
        setHasOptionsMenu(true);
        final FragmentManager manager=getActivity().getSupportFragmentManager();

        Bundle bundle=getArguments();
        hotelId=bundle.getInt(Contants.HOTEL_ID);
        String flag=bundle.getString(Contants.FLAG);

        adapterImage=new CustomAdapterImage(getContext(),fileAttachs,imgDetailHotel);
        rvImageDetailHotel.setAdapter(adapterImage);
        rvImageDetailHotel.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        if (flag.equals(Contants.HOTEL)){
//            setupAdapter(Contants.HOTEL);
            getDetailHotel(Contants.urlGetDetaiHotelById+hotelId);
            actionBar.setTitle("Chi tiết khách s");
        } else if (flag.equals(Contants.RESTAURANT)){
//            setupAdapter(Contants.RESTAURANT);
            getDetailHotel(Contants.urlGetDetaiRestaurantById+hotelId);
            actionBar.setTitle("Chi tiết nhà hàng");
        } else if (flag.equals(Contants.CULTURAL)){
//            setupAdapter(Contants.CULTURAL);
            getDetailHotel(Contants.urlGetDetailCulturalById+hotelId);
            actionBar.setTitle("Chi tiết văn hóa lễ hội");
        } else if(flag.equals(Contants.FOOD)){
//            setupAdapter(Contants.FOOD);
            getDetailHotel(Contants.urlGetDetaiFoodById+hotelId);
            actionBar.setTitle("Chi tiết văn hóa ẩm thực");
        } else if(flag.equals(Contants.LOCATION)){
//            setupAdapter(Contants.LOCATION);
            getDetailHotel(Contants.urlGetDetaiLocationById+hotelId);
            actionBar.setTitle("Di tích lịch sử");
        }

//        getDetailHotel(Contants.urlGetDetaiHotelById+hotelId);

        MapFragment mapFragment = ((MapFragment) getActivity().getFragmentManager().findFragmentById(
                R.id.mapDetailHotel));
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map=googleMap;
            }
        });

        btnPhoneDetailHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber!=null){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }

            }
        });


        return view;
    }

    private void initWidgets(View view) {
        imgDetailHotel=view.findViewById(R.id.imgDetailHotel);
        rvImageDetailHotel=view.findViewById(R.id.rvImageDetailHotel);
        txtAddressDetailHotel=view.findViewById(R.id.txtAddressDetailHotel);
        txtDescriptionDetailHotel=view.findViewById(R.id.txtDescriptionDetailHotel);
        txtPhoneDetailHotel=view.findViewById(R.id.txtPhoneDetailHotel);
        txtNameDetailHotel=view.findViewById(R.id.txtNameDetailHotel);
        rbDetailHotel=view.findViewById(R.id.rbDetailHotel);
        btnPhoneDetailHotel=view.findViewById(R.id.btnPhoneDetailHotel);
    }

    private void getDetailHotel(String url){
        final String flag=getArguments().getString(Contants.FLAG);
        final RequestQueue requestQueue= DownloadJSON.getInstance(getContext()).getRequestQue();
        System.out.println("url "+url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject rootLC=new JSONObject(response);
                    String status=rootLC.getString("Status");
                    String message=rootLC.getString("Message");
                    if (status.equals("1") && message.equals("Success")){
                        JSONObject rootArrayLC=rootLC.getJSONObject("Data");
                        String name=rootArrayLC.getString("Name");
                        int id=rootArrayLC.getInt("Id");
                        String address=rootArrayLC.getString("Address");
                        String shortDes=rootArrayLC.getString("ShortDes");
                        String longDes=rootArrayLC.getString("LongDes");
                        double longitude=rootArrayLC.getDouble("Longitude");
                        double latitude=rootArrayLC.getDouble("Latitude");
                        String avatar=rootArrayLC.getString("Avatar");
                        String hotline="";
                        if (flag.equals(Contants.RESTAURANT) || flag.equals(Contants.HOTEL))
                            hotline=rootArrayLC.getString("HotLine");
                        int star=rootArrayLC.getInt("Star");
                        int viewCount=rootArrayLC.getInt("ViewCount");
                        int price=0;
                        if (flag.equals(Contants.HOTEL) || flag.equals(Contants.RESTAURANT))
                            price=rootArrayLC.getInt("Price");
                        
                        JSONArray attachs=rootArrayLC.getJSONArray("FileAttachs");
                        int len=attachs.length();
                        phoneNumber=hotline;

                        if (len>0){
                            for (int i=0;i<len;i++){
                                JSONObject itemFile=attachs.getJSONObject(i);
                                String file=itemFile.getString("FileUrl");
                                fileAttachs.add(file);
                            }
                            adapterImage.notifyDataSetChanged();
                        }
                        LocationCategory locationCategory=new LocationCategory(id,name,address,longDes,longitude,latitude,avatar,star,hotline,viewCount,price,fileAttachs);

                        txtAddressDetailHotel.setText(locationCategory.getAddress());
                        txtPhoneDetailHotel.setText(locationCategory.getHotline());
                        txtNameDetailHotel.setText(locationCategory.getName());
                        rbDetailHotel.setRating(locationCategory.getStar());
                        String longDesHotel= null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            longDesHotel = Html.fromHtml(longDes,Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString();
                        }

                        if(longDes.contains("null") || longDes.equals("") || longDes.equals(null)){
                            txtDescriptionDetailHotel.setText("Thông tin đang cập nhật!");
                        } else {
                            txtDescriptionDetailHotel.setText(longDesHotel);
                            System.out.println(longDesHotel);
                        }

                        Glide.with(getContext())
                                .load(locationCategory.getAvatar())
                                .into(imgDetailHotel);

                        LatLng position = new LatLng(latitude, longitude);
                        map.moveCamera(CameraUpdateFactory.newLatLng(position));
                        map.animateCamera(CameraUpdateFactory.zoomTo(20),200,null);

                        Marker markerPosition = map.addMarker(new MarkerOptions().position(position)
                                .title(locationCategory.getName())
                        );
                        markerPosition.showInfoWindow();

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
