package com.androidtutorialpoint.mynavigationdrawer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidtutorialpoint.mynavigationdrawer.common.AppContanst;
import com.androidtutorialpoint.mynavigationdrawer.fragment.ContactFragment;
import com.androidtutorialpoint.mynavigationdrawer.fragment.DialogEnterContactInfo;
import com.androidtutorialpoint.mynavigationdrawer.fragment.HomeFragment;
import com.androidtutorialpoint.mynavigationdrawer.fragment.MyLocationFragment;
import com.androidtutorialpoint.mynavigationdrawer.fragment.SettingFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.libre.mylibs.MyUtils;

import java.io.File;
import java.io.IOException;

import common.LocationGPS;

public class MainActivity extends AppCompatActivity implements DialogEnterContactInfo.updateData, SettingFragment.UpdateImage, ContactFragment.updateData123 {
    public static int navItemIndex = 0;
    DrawerLayout drawer;
    NavigationView navigationView;
    private AlertDialog alertDialog;
    private ImageView img;
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_HELP = "help";
    private static final String TAG_SETTING = "setting";
    public static String CURRENT_TAG = TAG_HOME;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    private static final int SPEECH_REQUEST_CODE = 0;


    private FragmentManager manager;
    private Double latitude;
    private Double longtitude;
    private FragmentTransaction fragmentTransaction;
    private String token;
    private ImageView profile_image;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;

    private static int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 999;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.ac_main, new HomeFragment()).addToBackStack(null).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        IntentFilter filter = new IntentFilter
                (Intent.ACTION_MEDIA_BUTTON);
        CallBroadcastReceiver callBroadcastReceiver = new CallBroadcastReceiver();
        registerReceiver(callBroadcastReceiver, filter);


        LocationGPS locationGPS = new LocationGPS();
        latitude = locationGPS.checkAndGetLocation(this)[0];
        longtitude = locationGPS.checkAndGetLocation(this)[1];
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setTitle("Setting");
                SettingFragment settingFragment = new SettingFragment();
                manager = getSupportFragmentManager();
                fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.ac_main, settingFragment).addToBackStack(null).commit();
                drawer.closeDrawers();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        setToolbarTitle();
        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
        }
        token = MyUtils.getStringData(getApplicationContext(), AppContanst.TOKEN);
        storageRef = storage.getReferenceFromUrl("gs://saveyoufelffinal.appspot.com").child(token + "ava.png");
        getAva();

    }

    private void sendSms(String phonenumber, String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phonenumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            sendSms("01644546355", "This is my location " + "http://maps.google.com/maps?saddr=" + latitude + "," + longtitude);
        }
        return true;
    }

    public void getAva() {
        try {
            final File localFile = File.createTempFile("images", "jpg");


            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    profile_image.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
        }

    }


    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            showAlertDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    public void resetSlide() {

    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportActionBar().setTitle("Home");
                        HomeFragment homeFragment = new HomeFragment();
                        manager = getSupportFragmentManager();
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.ac_main, homeFragment).addToBackStack(null).commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_contacts:
                        getSupportActionBar().setTitle("Contact");
                        ContactFragment contactFragment = new ContactFragment();
                        manager = getSupportFragmentManager();
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.ac_main, contactFragment).addToBackStack(null).commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_locatonOfMine:
                        getSupportActionBar().setTitle("My Location");
                        MyLocationFragment myLocationFragment = new MyLocationFragment();
                        manager = getSupportFragmentManager();
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.ac_main, myLocationFragment).addToBackStack(null).commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_help:
                        HomeFragment homeFragment1 = new HomeFragment();
                        manager = getSupportFragmentManager();
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.ac_main, homeFragment1).addToBackStack(null).commit();
                        MyUtils.insertIntData(getApplicationContext(), String.valueOf(AppContanst.HELP), 2);
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_aboutUs:
                        HomeFragment homeFragment2 = new HomeFragment();
                        manager = getSupportFragmentManager();
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.ac_main, homeFragment2).addToBackStack(null).commit();
                        MyUtils.insertIntData(getApplicationContext(), String.valueOf(AppContanst.ABOUTUS), 3);
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        HomeFragment homeFragment3 = new HomeFragment();
                        manager = getSupportFragmentManager();
                        fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.ac_main, homeFragment3).addToBackStack(null).commit();
                        MyUtils.insertIntData(getApplicationContext(), String.valueOf(AppContanst.POLICY), 4);
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);
                return true;
            }
        });

    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // contacts
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;
            case 2:
                // location of mine
                MyLocationFragment myLocationFragment = new MyLocationFragment();
                return myLocationFragment;
            default:
                return new HomeFragment();
        }
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Your Save");
        builder.setMessage("Hành động của bạn có thể khiến bạn gặp khó khăn. Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Thôi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Không thoát được", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyUtils.insertStringData(getApplicationContext(), AppContanst.TOKEN, "");
                MyUtils.insertIntData(getApplicationContext(), String.valueOf(AppContanst.COUNT), 1);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @Override
    public void updateData(int action) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ac_main);
        ((ContactFragment) fragment).receiverAction(action);
    }

    private void displayImageFromGallery(Intent data, ImageView imageView) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bitmap = MyUtils.bitmapRotate(imgDecodableString);
        imageView.setImageBitmap(bitmap);
        File file = MyUtils.saveBitmapToFile(MyUtils.resize(bitmap, 800, 800), "picture" + ".jpg");
        //image_edit = new TypedFile("multipart/form-data", file);
    }


    @Override
    public void onDataPass(int data) {
        getAva();
    }

    public class MediaButtonIntentReceiver extends BroadcastReceiver {

        public MediaButtonIntentReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("davapday", "eqweqw");
            String intentAction = intent.getAction();
            if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
                return;
            }
            KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event == null) {
                return;
            }
            int action = event.getAction();
            if (action == KeyEvent.KEYCODE_VOLUME_UP) {
                // do something
                Toast.makeText(context, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
            }
            abortBroadcast();
        }
    }

    private void launchApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


}
