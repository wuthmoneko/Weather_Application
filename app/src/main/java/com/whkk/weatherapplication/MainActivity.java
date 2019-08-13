package com.whkk.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.whkk.weatherapplication.remote.dto.ResponseWeather;
import com.whkk.weatherapplication.remote.retrofit.APIService;
import com.whkk.weatherapplication.remote.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private TextView lblWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblWeatherData = findViewById(R.id.lblWeatherData);


        APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<ResponseWeather> call = service.getCurrentWeatherData("7879229444msh7b39aa2be769d56p1c366ejsne6b1fe6e456a",
                "\"metric\"or\"imperial\"",
                "xml,html",
                "Dawei,MM");


        call.enqueue(new Callback<ResponseWeather>() {

            @Override
            public void onResponse(@NonNull Call<ResponseWeather> call,@NonNull Response<ResponseWeather> response) {
                if (response.isSuccessful()) {
                    ResponseWeather responseWeather = response.body();
                    assert responseWeather != null;

                    Log.d("MainActivity", "weather main" + responseWeather.weatherList.get(0).main);
                    Log.d("MainActivity", "weather description" + responseWeather.weatherList.get(0).description);
                    Log.d("MainActivity", "weather temp" + responseWeather.main.temp);
                    Log.d("MainActivity", "weather temp min" + responseWeather.main.tempMin);
                    Log.d("MainActivity", "weather temp max" + responseWeather.main.tempMax);


                    lblWeatherData.setText("weather main :" + responseWeather.weatherList.get(0).main + "\n"
                            + "weather description :" + responseWeather.weatherList.get(0).description + "\n"
                            + "Weather Temp :" + responseWeather.main.temp + "\n"
                            + "Weather temp min :" + responseWeather.main.tempMin + "\n"
                            + "weather temp max :" + responseWeather.main.tempMax + "\n");

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseWeather> call, @NonNull Throwable t) {
                Log.d("MainActivity", String.format("Error: %s", t.getMessage()));
            }
        });
    }
}