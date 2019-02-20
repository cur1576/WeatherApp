package com.example.weatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

class WeatherUtils {

    private static final String TAG = WeatherUtils.class.getSimpleName();
    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q={0}&lang=de&appid={1}";
    private static final String KEY = "8c6ab864c65279e8cd83a86dcf415737";

    private static final String NAME = "name";
    private static final String WEATHER = "weather";
    private static final String DESCRIPTION = "description";
    private static final String ICON = "icon";
    private static final String MAIN = "main";
    private static final String TEMP = "temp";

    public static String getFromServer(String url) throws IOException{
        Log.d(TAG, "getFromServer: " + url);
        StringBuilder stringBuilder = new StringBuilder();
        java.net.URL _url = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
        final int responseCode = httpURLConnection.getResponseCode();

        if(responseCode==HttpURLConnection.HTTP_OK){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
        }
        httpURLConnection.disconnect();
        Log.d(TAG, "getFromServer: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    /*
    {
      "coord": {
        "lon": 13.39,
        "lat": 52.52
      },
      "weather": [
        {
          "id": 801,
          "main": "Clouds",
          "description": "Ein paar Wolken",
          "icon": "02d"
        }
      ],
      "base": "stations",
      "main": {
        "temp": 283.15,
        "pressure": 1021,
        "humidity": 61,
        "temp_min": 283.15,
        "temp_max": 283.15
      },
      "visibility": 10000,
      "wind": {
        "speed": 4.1,
        "deg": 270
      },
      "clouds": {
        "all": 20
      },
      "dt": 1550674200,
      "sys": {
        "type": 1,
        "id": 1275,
        "message": 0.0034,
        "country": "DE",
        "sunrise": 1550643112,
        "sunset": 1550680153
      },
      "id": 2950159,
      "name": "Berlin",
      "cod": 200
    }
     */
    public static WeatherData getWeather(String city) throws JSONException,IOException {
        String name = null;
        String description = null;
        String icon = null;
        Double temp = null;
        JSONObject jsonObject = new JSONObject(getFromServer(MessageFormat.format(URL,city,KEY)));


        Log.d(TAG, "getWeather: " + jsonObject.toString(2));


        if(jsonObject.has(NAME)){
            name = jsonObject.getString(NAME);
        }
        if(jsonObject.has(WEATHER)){
            JSONArray jsonArray = jsonObject.getJSONArray(WEATHER);
            if(jsonArray.length()>0){
                JSONObject jsonWeather = jsonArray.getJSONObject(0);
                if(jsonWeather.has(DESCRIPTION)){
                    description = jsonWeather.getString(DESCRIPTION);
                }
                if(jsonWeather.has(ICON)){
                    icon = jsonWeather.getString(ICON);
                }
            }
        }
        if(jsonObject.has(MAIN)){
            JSONObject jsonMain = jsonObject.getJSONObject(MAIN);
            if(jsonMain.has((TEMP))){
                temp = jsonMain.getDouble(TEMP);
            }
        }

        return new WeatherData(description, name, icon, temp);
    }

    public static Bitmap getImage(WeatherData weather) throws IOException {
        java.net.URL req = new URL("http://openweathermap.org/img/w/" + weather.getIcon() + ".png");
        HttpURLConnection httpURLConnection = (HttpURLConnection)req.openConnection();
        Bitmap bmp = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
        httpURLConnection.disconnect();
        return bmp;
    }
}
