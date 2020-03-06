package com.example.stormy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stormy.R;
import com.example.stormy.weather.Current;
import com.example.stormy.weather.Forecast;

import com.example.stormy.databinding.ActivityMainBinding;

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
    private Forecast forecast;
    private ImageView iconImageView;
    final double latitude =37.8267;
    final double longitude =-122.4233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getForecast(latitude, longitude);
        Log.d(TAG, "Main Code is Running");
    }

    private void getForecast(double latitude, double longitude ) {
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        TextView darkSky = findViewById(R.id.darkSkyAttribution);
        darkSky.setMovementMethod(LinkMovementMethod.getInstance());

        iconImageView = findViewById(R.id.iconImageView);

        String apiKey ="8c798ae8a862d546577abf4f0df5557f";


        String forecastURl = "https://api.darksky.net/forecast/"
                + apiKey +"/"+latitude +","+longitude;

        if(isNetworkAvailable()){

        OkHttpClient client = new OkHttpClient();

        Request request = new Builder().url(forecastURl).build();
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
                        forecast = parseForecastData(jsonData);

                        Current currentWeather = forecast.getCurrentWeather();

                        final Current displayWeather = new Current(
                                currentWeather.getLocation(),
                                currentWeather.getIcon(),
                                currentWeather.getTime(),
                                currentWeather.getTemperature(),
                                currentWeather.getHumidity(),
                                currentWeather.getPrecipChance(),
                                currentWeather.getSummary(),
                                currentWeather.getTimeZone()
                        );

                        binding.setWeather(displayWeather);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Drawable drawable = getResources().getDrawable(displayWeather.getIconId());
                                iconImageView.setImageDrawable(drawable);
                            }
                        });


                    }else{
                        alertUserAboutError();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "IO Exception caught", e);
                }catch (JSONException e){
                    Log.e(TAG, "JSON Exception", e);
                }
            }
        });
        }
    }

    private Forecast parseForecastData(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setCurrentWeather(getCurrentDetails(jsonData));
        return forecast;
    }


    private Current getCurrentDetails(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);

        String timezone = forecast.getString("timezone");
        Log.i(TAG, "from Json " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");
        Current currentWeather = new Current();

        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setLocation("Alcatraz Island CA");
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimeZone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
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

    public void refreshOnClick(View view){
        getForecast(latitude, longitude);
        Toast.makeText(this, "Data Refreshed", Toast.LENGTH_LONG).show();

    }
}
