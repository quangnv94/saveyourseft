package com.example.admin.sosapp;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Drawer drawer;
    Toolbar toolbar;
    private LinearLayout layoutContact;
    private LinearLayout layoutFamily;
    private LinearLayout layoutLocation;
    private LinearLayout layoutSetting;
    private LinearLayout layoutHelp;

    private TextView tvContact;
    private TextView tvFamily;
    private TextView tvLocation;
    private TextView tvSetting;
    private TextView tvHelp;
    private TextView tvDateTime;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private Button btnPush;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = setUpToolbar();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        btnPush = (Button) findViewById(R.id.push_button);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        tvDateTime.setText("" + formattedDate);
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar);
        initController();





    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            return;
        }
        finishAffinity();
    }

    private Toolbar setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.menu2);
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen()) {
                    drawer.closeDrawer();
                    return;
                }
            }
        });
        return toolbar;
    }

    public void initController() {
        setupDrawerLayout();
        setUpSlide();
    }

    public void setUpSlide() {
//        layoutContact = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutContact);
//        layoutContact.setOnClickListener(this);
        layoutFamily = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutFamily);
        layoutFamily.setOnClickListener(this);
        layoutSetting = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutSetting);
        layoutSetting.setOnClickListener(this);
        layoutHelp = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutHelp);
        layoutHelp.setOnClickListener(this);

//        tvContact = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContact);
        tvFamily = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvFamily);
        tvSetting = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvSetting);
        tvHelp = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvHelp);
    }

    public void resetSlideWhenClick() {
        tvContact.setTextColor(getResources().getColor(R.color.black));
        tvFamily.setTextColor(getResources().getColor(R.color.black));
        tvLocation.setTextColor(getResources().getColor(R.color.black));
        tvSetting.setTextColor(getResources().getColor(R.color.black));
        tvHelp.setTextColor(getResources().getColor(R.color.black));
    }

    private void setupDrawerLayout() {
        LayoutInflater inflater = getLayoutInflater();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withCustomView(inflater.inflate(R.layout.slide_menu, null))
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .build();
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.layoutContact:
//                resetSlideWhenClick();
//                tvContact.setTextColor(getResources().getColor(R.color.accent));
//                break;
            case R.id.layoutFamily:
                resetSlideWhenClick();
                tvFamily.setTextColor(getResources().getColor(R.color.accent));
                break;
//            case R.id.layoutLocation:
//                resetSlideWhenClick();
//                tvLocation.setTextColor(getResources().getColor(R.color.accent));
//                break;
            case R.id.layoutSetting:
                resetSlideWhenClick();
                tvSetting.setTextColor(getResources().getColor(R.color.accent));
                Intent intent = new Intent(com.example.admin.sosapp.MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutHelp:
                resetSlideWhenClick();
                tvHelp.setTextColor(getResources().getColor(R.color.accent));
                break;
        }
    }
}

