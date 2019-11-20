package com.example.stormy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey ="8c798ae8a862d546577abf4f0df5557f";
        double latitude =37.8267;
        double longitude =-122.4233;

        String forecastURl = "https://api.darksky.net/forecast/"
                + apiKey +"/"+latitude +","+longitude;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(forecastURl).build();
        Call call = client.newCall(request);

        try {
            Response response = call.execute();

            if(response.isSuccessful()){
                Log.v(TAG, response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "IO Exception caight", e);
        }

    }
}
