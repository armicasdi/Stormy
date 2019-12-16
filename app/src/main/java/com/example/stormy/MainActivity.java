package com.example.stormy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private CurrentWeather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey ="8c798ae8a862d546577abf4f0df5557f";
        double latitude =37.8267;
        double longitude =-122.4233;

        String forecastURl = "https://api.darksky.net/forecast/"
                + apiKey +"/"+latitude +","+longitude;

        if(isNetworkAvailable()){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(forecastURl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {

                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);

                    if(response.isSuccessful()){
                        currentWeather = getCurrentDetails(jsonData);

                    }else{
                        alertUserAboutError();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "IO Exception caight", e);
                }catch (JSONException e){
                    Log.e(TAG, "JSON Exception", e);
                }
            }
        });
        }

        Log.d(TAG, "Main Code is Running");





    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);

        String timezone = forecast.getString("timezone");
        Log.i(TAG, "from Json " + timezone);
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }else
        {
            Toast.makeText(this, R.string.NetNot, Toast.LENGTH_LONG).show();
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");

    }
}
