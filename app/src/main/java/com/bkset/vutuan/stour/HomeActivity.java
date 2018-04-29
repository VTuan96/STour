package com.bkset.vutuan.stour;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.bkset.vutuan.stour.fragment.HomeFragment;
import com.bkset.vutuan.stour.fragment.MapFragment;
import com.bkset.vutuan.stour.fragment.MoreFragment;
import com.bkset.vutuan.stour.fragment.NotifyFragment;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    public static BottomNavigationView nvMenuBottom;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment fragment=null;
    public static android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initWidget();
        manager=getSupportFragmentManager();
        actionBar=getSupportActionBar();

        setUpNavigationBottomView();

        transaction=manager.beginTransaction();
//        transaction.addToBackStack("fragment");
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();

    }

    private void setUpNavigationBottomView() {
        nvMenuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id)
                {
                    case R.id.mnuHome:
                        fragment=new HomeFragment();
                        break;
                    case R.id.mnuMap:
                        fragment=new MapFragment();
                        break;
                    case R.id.mnuNotify:
                        fragment=new NotifyFragment();
                        break;
                    case R.id.mnuMore:
                        fragment=new MoreFragment();
                        break;

                }

                if (fragment!=null){
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.container,fragment);
                    transaction.commit();
                } else {
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.container,new HomeFragment());
                    transaction.commit();
                }
                return true;
            }
        });
    }

    private void initWidget() {
        nvMenuBottom=findViewById(R.id.nvMenuBottom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            //setClickListener on Back arrow button on ActionBar
            case android.R.id.home: {
               int countBackStack=manager.getBackStackEntryCount();
               if (countBackStack>0){
                   String name= manager.getBackStackEntryAt(countBackStack-1).getName();
                   if (name.equals("HotelFragment")){
                       actionBar.setTitle("sTour");
                       //hide action bar
//                       actionBar.hide();
                       //set actionbar transparent
//                       actionBar.setElevation(0);
//                       actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                       actionBar.setHomeButtonEnabled(false);
                       actionBar.setDisplayHomeAsUpEnabled(false);
                   } else if (name.equals("DetailHotelFragment")){
                       actionBar.setTitle("Khách sạn");
                   }
                   manager.popBackStack();
               }

                break;
            }


        }
        return true;
    }

}
