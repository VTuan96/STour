package com.bkset.vutuan.stour.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.bkset.vutuan.stour.HomeActivity;
import com.bkset.vutuan.stour.R;
import com.bkset.vutuan.stour.adapter.CustomAdapterBanner;
import com.bkset.vutuan.stour.adapter.CustomAdapterLocation;
import com.bkset.vutuan.stour.model.LocationCategory;
import com.bkset.vutuan.stour.ultilities.Contants;
import com.bkset.vutuan.stour.ultilities.DownloadJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CardView.OnClickListener{
    LinearLayout layout_list_category;

    private CardView cvHotel, cvRestaurant, cvHistoryRelics, cvFestivalCulture, cvCultureFood;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment fragment=null;

    private RecyclerView rvBanner;
    private ArrayList<String> urlBanners=new ArrayList<>();
    private CustomAdapterBanner adapterBanner;

    private int counter=0; // counter of banner image


    android.support.v7.app.ActionBar actionBar;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        initWidget(view);
        manager=getActivity().getSupportFragmentManager();

        urlBanners=new ArrayList<>();
        adapterBanner=new CustomAdapterBanner(getContext(),urlBanners);
        rvBanner.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvBanner.setAdapter(adapterBanner);

        onCardViewClicked();

        getCategoryLocation();

        getBanner();



        return view;
    }

    private void getBanner() {
        final RequestQueue requestQueue= DownloadJSON.getInstance(getContext()).getRequestQue();

        StringRequest requestLocationCategory=new StringRequest(Request.Method.GET, Contants.urlGetBanner, new Response.Listener<String>() {
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
                                String urlBanner=lc.getString("Image");
                                urlBanners.add(urlBanner);
                            }

                            adapterBanner.notifyDataSetChanged();

                            Timer timer=new Timer();
                            TimerTask timerTask=new TimerTask() {
                                @Override
                                public void run() {
                                    rvBanner.smoothScrollToPosition(counter++);
                                    if (counter==3) counter=0;
                                }
                            };
                            timer.schedule(timerTask,5000,4000);

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

        requestQueue.add(requestLocationCategory);
    }

    private void getCategoryLocation(){
        final RequestQueue requestQueue= DownloadJSON.getInstance(getContext()).getRequestQue();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Contants.urlGetAllCategoryLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray data=jsonObject.getJSONArray("Data");
                    for (int i=0;i<data.length();i++){
                        JSONObject object=data.getJSONObject(i);
                        String name=object.getString("Name");
                        int id=object.getInt("Id");

                        final LinearLayout itemLayout=new LinearLayout(getContext());
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,0,0,40);
                        itemLayout.setLayoutParams(params);
                        itemLayout.setOrientation(LinearLayout.VERTICAL);

                        TextView itemTxt=new TextView(getContext());
                        itemTxt.setText(name);
                        itemTxt.setTypeface(Typeface.DEFAULT_BOLD);

                        RecyclerView itemRv=new RecyclerView(getContext());
                        itemRv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        itemRv.setLayoutManager(layoutManager);
                        itemRv.setBackgroundColor(getResources().getColor(R.color.colorGrey));

                        itemLayout.addView(itemTxt);
                        itemLayout.addView(itemRv);

                        layout_list_category.addView(itemLayout);

                        final ArrayList<LocationCategory> listLocationCategory=new ArrayList<>();
                        final CustomAdapterLocation adapterLocation=new CustomAdapterLocation(getContext(),listLocationCategory);
                        itemRv.setHasFixedSize(true);
                        itemRv.setAdapter(adapterLocation);

                        final String finalUrlLocationByCategoryId = Contants.urlLocationByCategoryId+id;
                        StringRequest requestLocationCategory=new StringRequest(Request.Method.GET, finalUrlLocationByCategoryId, new Response.Listener<String>() {
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
                                            layout_list_category.removeView(itemLayout);
                                        } else {
                                            for (int i=0;i<length;i++){
                                                JSONObject lc=rootArrayLC.getJSONObject(i);
                                                String name=lc.getString("Name");
                                                int id=lc.getInt("Id");
                                                int locationCategoryId=lc.getInt("LocationCategoryId");
                                                String address=lc.getString("Address");
                                                String shortDes=lc.getString("ShortDes");
                                                String longDes=lc.getString("LongDes");
                                                double longitude=lc.getDouble("Longitude");
                                                double latitude=lc.getDouble("Latitude");
                                                String avatar=lc.getString("Avatar");
                                                LocationCategory locationCategory=new LocationCategory(locationCategoryId,id,name,address,shortDes,longDes,longitude,latitude,avatar);
                                                listLocationCategory.add(locationCategory);
                                            }

                                            adapterLocation.notifyDataSetChanged();

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

                        requestQueue.add(requestLocationCategory);

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

    private void onCardViewClicked() {
        cvHotel.setOnClickListener(this);
        cvRestaurant.setOnClickListener(this);
        cvFestivalCulture.setOnClickListener(this);
        cvCultureFood.setOnClickListener(this);
        cvHistoryRelics.setOnClickListener(this);
    }

    private void initWidget(View view) {
        layout_list_category=view.findViewById(R.id.layout_list_category);
        cvCultureFood= view.findViewById(R.id.cvCultureFood);
        cvFestivalCulture=view.findViewById(R.id.cvFestivalCulture);
        cvHistoryRelics=view.findViewById(R.id.cvHistoryRelics);
        cvHotel=view.findViewById(R.id.cvHotel);
        cvRestaurant=view.findViewById(R.id.cvRestaurant);
        cvHistoryRelics=view.findViewById(R.id.cvHistoryRelics);
        cvCultureFood=view.findViewById(R.id.cvCultureFood);
        cvFestivalCulture=view.findViewById(R.id.cvFestivalCulture);
        rvBanner=view.findViewById(R.id.rvBanner);

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.cvHotel:
                fragment=new HotelFragment();
                Bundle bundle=new Bundle();
                bundle.putString(Contants.FLAG,Contants.HOTEL);
                fragment.setArguments(bundle);
                transaction=manager.beginTransaction();
                transaction.addToBackStack("HotelFragment");
                transaction.add(R.id.container,fragment);
                transaction.commit();
                HomeActivity.nvMenuBottom.setVisibility(View.GONE);
                break;

            case R.id.cvRestaurant:
                fragment=new HotelFragment();
                Bundle bundleRestaurent=new Bundle();
                bundleRestaurent.putString(Contants.FLAG,Contants.RESTAURANT);
                fragment.setArguments(bundleRestaurent);
                transaction=manager.beginTransaction();
                transaction.addToBackStack("HotelFragment");
                transaction.add(R.id.container,fragment);
                transaction.commit();
                HomeActivity.nvMenuBottom.setVisibility(View.GONE);
                break;

            case R.id.cvHistoryRelics:
                fragment=new HotelFragment();
                Bundle bundleHistoryRelics=new Bundle();
                bundleHistoryRelics.putString(Contants.FLAG,Contants.LOCATION);
                fragment.setArguments(bundleHistoryRelics);
                transaction=manager.beginTransaction();
                transaction.addToBackStack("HotelFragment");
                transaction.add(R.id.container,fragment);
                transaction.commit();
                HomeActivity.nvMenuBottom.setVisibility(View.GONE);
                break;

            case R.id.cvCultureFood:
                fragment=new HotelFragment();
                Bundle bundleCultureFood=new Bundle();
                bundleCultureFood.putString(Contants.FLAG,Contants.FOOD);
                fragment.setArguments(bundleCultureFood);
                transaction=manager.beginTransaction();
                transaction.addToBackStack("HotelFragment");
                transaction.add(R.id.container,fragment);
                transaction.commit();
                HomeActivity.nvMenuBottom.setVisibility(View.GONE);
                break;

            case R.id.cvFestivalCulture:
                fragment=new HotelFragment();
                Bundle bundleFestivalCulture=new Bundle();
                bundleFestivalCulture.putString(Contants.FLAG,Contants.CULTURAL);
                fragment.setArguments(bundleFestivalCulture);
                transaction=manager.beginTransaction();
                transaction.addToBackStack("HotelFragment");
                transaction.add(R.id.container,fragment);
                transaction.commit();
                HomeActivity.nvMenuBottom.setVisibility(View.GONE);
                break;
        }
    }



}
