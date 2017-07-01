package com.androidtutorialpoint.mynavigationdrawer.fragment;


import android.animation.ObjectAnimator;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialpoint.mynavigationdrawer.ContactsModel;
import com.androidtutorialpoint.mynavigationdrawer.MyDatabaseHelper;
import com.androidtutorialpoint.mynavigationdrawer.R;

import common.LocationGPS;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidtutorialpoint.mynavigationdrawer.MainActivity;
import com.androidtutorialpoint.mynavigationdrawer.R;
import com.androidtutorialpoint.mynavigationdrawer.common.AppContanst;
import com.libre.mylibs.MyUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "AnimationStarter";
    ObjectAnimator buttonAnimation;
    private Button btnSos;
    private int countPress = 0;
    private Double latitude;
    private Double longtitude;
    private String currentAddress;
    private ImageView imageView;
     private MediaPlayer mp;
    private  String phone = "";
    private TextView textViewTenThanhPho,textViewNgay,
            textViewNhietDoThapNhat,textViewNhietDoCaoNhat,textViewNhietDoHienTai,
            textViewTrangThai,textViewDuDoan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //get location
        LocationGPS locationGPS = new LocationGPS();
        latitude = locationGPS.checkAndGetLocation(getContext())[0];
        longtitude = locationGPS.checkAndGetLocation(getContext())[1];
        init(view);
        isHelp();
        isAboutUs();
        isPolicy();
        return view;
    }
/////// kiểm tra Help
    public void isHelp(){
        if(MyUtils.getIntData(getContext(),String.valueOf(AppContanst.HELP)) == 2){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
            alertBuilder.setTitle(getString(R.string.help));
            alertBuilder.setMessage(getString(R.string.helpContent));
            alertBuilder.setPositiveButton("I see", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            alertBuilder.show();
            MyUtils.insertIntData(getContext(),String.valueOf(AppContanst.HELP), 0);
        }

    }

    ///// kiểm tra Policy
    public void isPolicy(){
        if(MyUtils.getIntData(getContext(), String.valueOf(AppContanst.POLICY)) == 4) {
            AlertDialog.Builder alerBuilder = new AlertDialog.Builder(getContext());
            alerBuilder.setTitle(getString(R.string.privacy));
            alerBuilder.setMessage(getString(R.string.policyContent));
            alerBuilder.setPositiveButton("I see", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("ISEE", "true");
                    dialogInterface.dismiss();
                }
            });
            alerBuilder.show();
            MyUtils.insertIntData(getContext(),String.valueOf((AppContanst.POLICY)), 0);

    }}

//// kiểm tra aboutus
    public void isAboutUs(){
        if(MyUtils.getIntData(getContext(), String.valueOf(AppContanst.ABOUTUS)) == 3) {
            AlertDialog.Builder alerBuilder = new AlertDialog.Builder(getContext());
            alerBuilder.setTitle(getString(R.string.about_us));
            alerBuilder.setMessage(getString(R.string.aboutUsContent));
            alerBuilder.setPositiveButton("I see", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alerBuilder.show();
        MyUtils.insertIntData(getContext(),String.valueOf((AppContanst.ABOUTUS)), 0);
    }
    }
    public void init(View view) {
        btnSos = (Button) view.findViewById(R.id.btnSos);
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(getActivity().getApplicationContext());
        List<ContactsModel> list = myDatabaseHelper.getAllContacts();


        for(int i = 0; i< list.size(); i++){
            phone += list.get(i).getNumberPhone() + ",";
        }
        Log.d("PHONE1234567", phone);

        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
         mp = MediaPlayer.create(getActivity(), R.raw.sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.reset();
                mp.release();
            }
        });
        btnSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countPress += 1;
                if (countPress % 2 != 0) {
                    btnSos.startAnimation(animation);
                    mp.start();
                    mp.setLooping(true);
               //     mp.release();
                    try {
                        sendSms(phone.toString(), "This is my location " + "http://maps.google.com/maps?saddr=" + latitude + "," + longtitude);
                    } catch (ActivityNotFoundException exception) {
                        exception.printStackTrace();
                    }

                } else {
                    btnSos.clearAnimation();
                    mp.stop();
                    mp.prepareAsync();
                }
            }
        });
        textViewTenThanhPho=(TextView) view.findViewById(R.id.textViewTenThanhPho);

        textViewNgay=(TextView) view.findViewById(R.id.textViewNgay);

        textViewNhietDoHienTai=(TextView)view.findViewById(R.id.textViewNhietDoHienTai);
        textViewNhietDoCaoNhat=(TextView) view.findViewById(R.id.textViewNhietDoCaoNhat);
        textViewNhietDoThapNhat=(TextView) view.findViewById(R.id.textViewNhietDoThapNhat);

        textViewTrangThai=(TextView) view.findViewById(R.id.textViewTrangThai);
        textViewDuDoan=(TextView) view.findViewById(R.id.textViewDuDoan);

        imageView=(ImageView) view.findViewById(R.id.imageView);
        getCurrentWeatherDate(latitude,longtitude);

    }



    private void sendSms(String phonenumber, String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phonenumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }


    public void getCurrentWeatherDate(double lat,double lon){
        String url="http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&cnt=1&appid=1c7f23ade587536ff50410fadd9a3bde";
        Log.d("URL",url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url
//

                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                try{
                    JSONObject  jsonObject=new JSONObject(response);

                    JSONObject jsonObjectCity=jsonObject.getJSONObject("city");


                    //dt:ngay fomat
                    String name=jsonObjectCity.getString("name");// name:TenThanhPho
                    textViewTenThanhPho.setText(name);



                    JSONArray jsonArrayList=jsonObject.getJSONArray("list");
                    JSONObject jsonObjectItemList=jsonArrayList.getJSONObject(0);


                    String day=jsonObjectItemList.getString("dt");
                    long l=Long.valueOf(day);
                    Date date=new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EE,dd MM HH:mm");
                    simpleDateFormat.format(date);



                    SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("EE,dd MM");
                    Date date2=date;
                    String Day= simpleDateFormat2.format(date2);

                    textViewNgay.setText(Day);








                    JSONArray jsonArrayWeather =jsonObjectItemList.getJSONArray("weather");
                    JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);

                    String status=jsonObjectWeather.getString("main");
                    textViewTrangThai.setText(status);



                    String base=jsonObjectWeather.getString("description");//trang thai
                    textViewDuDoan.setText(base);
                    String icon="http://openweathermap.org/img/w/"+jsonObjectWeather.getString("icon")+".png";

//                   icon="http://openweathermap.org/img/w/10d.png";
                    Picasso.with(getActivity()).load(icon).into(imageView);

                    JSONObject jsonObjectMain=jsonObjectItemList.getJSONObject("temp");

                    String temp=jsonObjectMain.getString("day");//nhiet do hien tai
                    double tempInt=Double.parseDouble(temp)-273.15;
                    textViewNhietDoHienTai.setText((int) tempInt+"*C");

                    String tempMin=jsonObjectMain.getString("min");//nhiet do hien tai
                    double tempIntMin=Double.parseDouble(tempMin)-273.15;
                    textViewNhietDoThapNhat.setText((int)tempIntMin+"");

                    String tempMax=jsonObjectMain.getString("max");//nhiet do hien tai
                    double tempIntMax=Double.parseDouble(tempMax)-273.15;
                    textViewNhietDoCaoNhat.setText((int)tempIntMax+"");

                }catch (JSONException e){
                    Log.d("Errore",e.toString());

                }

//                textView.setText("Response is: " + response);
                Log.d("Location",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void playSound(){

    }
}
