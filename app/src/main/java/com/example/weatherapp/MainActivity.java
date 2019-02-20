package com.example.weatherapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText city;
    private Button button;
    private ImageView imageView;
    private TextView temp, beschreibung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!istNetzwerkVerfuegbar()){
            Toast.makeText(this, getString(R.string.kein_Netzwerk), Toast.LENGTH_SHORT).show();
            finish();
        }
        city = findViewById(R.id.city);
        imageView = findViewById(R.id.image);
        temp = findViewById(R.id.temperatur);
        beschreibung = findViewById(R.id.beschreibung);
        button = findViewById(R.id.button);
        button.setOnClickListener((v) -> new Thread(()->{

            final WeatherData weather;
            try {
                weather = WeatherUtils.getWeather(city.getText().toString());

                final Bitmap bitmapWeather = WeatherUtils.getImage(weather);
                runOnUiThread(()->{
                    imageView.setImageBitmap(bitmapWeather);
                    beschreibung.setText(weather.getDescription());
                    Double tempD = weather.getTemp() -273.15;
                    temp.setText(getString(R.string.temp_template,tempD.intValue()));
                });

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start());
        city.setOnEditorActionListener((textView,i,keyEvent)->{
            button.performClick();
            return true;
        });
    }

    private boolean istNetzwerkVerfuegbar() {
        ConnectivityManager mgr = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // aktuelles Vorgehen
            mgr = getSystemService(ConnectivityManager.class);
        }else {
            // so wurde es frueher gemacht
            mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo info = mgr.getActiveNetworkInfo();

        return info!=null && info.isConnected();
    }


}
