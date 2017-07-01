package com.example.admin.sosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by Admin on 3/21/2017.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Drawer drawer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = setUpToolbar();
        initController();
    }

    private Toolbar setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_list);
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
        layoutContact = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutContact);
        layoutContact.setOnClickListener(this);
        layoutFamily = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutFamily);
        layoutFamily.setOnClickListener(this);
        layoutLocation = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutLocation);
        layoutLocation.setOnClickListener(this);
        layoutSetting = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutSetting);
        layoutSetting.setOnClickListener(this);
        layoutHelp = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutHelp);
        layoutHelp.setOnClickListener(this);

        tvContact = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContact);
        tvFamily = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContact);
        tvLocation = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContact);
        tvSetting = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContact);
        tvHelp = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContact);
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
            case R.id.layoutContact:
                resetSlideWhenClick();
                tvContact.setTextColor(getResources().getColor(R.color.accent));
                break;
            case R.id.layoutFamily:
                resetSlideWhenClick();
                tvFamily.setTextColor(getResources().getColor(R.color.accent));
                break;
            case R.id.layoutLocation:
                resetSlideWhenClick();
                tvLocation.setTextColor(getResources().getColor(R.color.accent));
                break;
            case R.id.layoutSetting:
                resetSlideWhenClick();
                tvSetting.setTextColor(getResources().getColor(R.color.accent));
                Intent intent = new Intent(com.example.admin.sosapp.SettingActivity.this, com.example.admin.sosapp.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutHelp:
                resetSlideWhenClick();
                tvHelp.setTextColor(getResources().getColor(R.color.accent));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            return;
        }
        finishAffinity();
    }

}
