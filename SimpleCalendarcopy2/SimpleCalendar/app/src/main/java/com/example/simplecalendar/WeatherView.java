package com.example.simplecalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.content.Intent;


public class WeatherView extends AppCompatActivity {
    TextView dateView;
    TextView cityView;
    TextView weatherView;
    TextView tempView;

    ImageButton imagebtn_weather;

    static RequestQueue requestQueue;

    public void setDateView(TextView dateView) {
        this.dateView = dateView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        imagebtn_weather = findViewById(R.id.imagebutton_weather);

        dateView = findViewById(R.id.dateView);
        cityView = findViewById(R.id.cityView);
        weatherView = findViewById(R.id.weatherView);
        tempView = findViewById(R.id.tempView);


        imagebtn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        //button

        ImageButton button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Maincativity2.class);
                startActivity(intent);
            }
        });


        ImageButton button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
                String getDay = simpleDateFormatDay.format(date);
                String getTime = simpleDateFormatTime.format(date);

                String getDate = getDay + "\n" + getTime;
                dateView.setText(getDate);


                CurrentweatherCall();


            }
        });
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }
    private void CurrentweatherCall(){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Busan&appid=e35eb9388257bb1edde5fb4a6f373d4a";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {

                    //System??? ?????? ??????(???,???,???,???,???,?????????)???????????? Date??? ????????????
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);

                    //???, ???, ??? ????????????. ???,???,??? ???????????? ??????????????? String??? ???????????? ??????
                    SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
                    String getDay = simpleDateFormatDay.format(date);
                    String getTime = simpleDateFormatTime.format(date);

                    //getDate??? ????????? ????????? ???????????? ?????? ??? dateView??? text??????
                    String getDate = getDay + "\n" + getTime;
                    dateView.setText(getDate);

                    //api??? ?????? ?????? jsonobject??? ????????? ?????? ??????
                    JSONObject jsonObject = new JSONObject(response);


                    //?????? ?????? ??????
                    String city = jsonObject.getString("name");

                    cityView.setText(city);


                    //?????? ?????? ??????
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);

                    String weather = weatherObj.getString("description");

                    weatherView.setText(weather);



                    //?????? ?????? ??????
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));

                    //?????? ?????? ?????? ????????? ?????? ????????? ??????
                    double tempDo = (Math.round((tempK.getDouble("temp")-273.15)*100)/100.0);
                    tempView.setText(tempDo +  "??C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

}