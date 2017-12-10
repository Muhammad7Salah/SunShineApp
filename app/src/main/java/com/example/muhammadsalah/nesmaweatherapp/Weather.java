package com.example.muhammadsalah.nesmaweatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Weather extends AppCompatActivity {

    SharedPreferences preferences;SharedPreferences.Editor editor;

    //DateFormat dateFormat = new SimpleDateFormat("MMM");
    Date date = new Date();
    Calendar cal= Calendar.getInstance();
    String monthday = new SimpleDateFormat("MMM").format(cal.getTime())+" "+new SimpleDateFormat("d").format(cal.getTime());
    String day = new SimpleDateFormat("EEEE").format(cal.getTime());

    //private String appid = "90ae95cfcdea5af9243709e38534b2a3"; //mykey
    private String appid = "c0c4a4b4047b97ebc5948ac9c48c0559"; //example key


    //ArrayList<HashMap<String, String>> dataList;
    ArrayList<Item> array = new ArrayList<Item>();
    WeatherDataSQLite db = new WeatherDataSQLite(this);
    int iconID=R.drawable.solar_icon_1;


    //private String url = "http://api.openweathermap.org/data/2.5/forecast/?q=Cairo,EG&units=metric&cnt=7&appid="+appid;
    private String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Cairo%2CEG&units=metric&cnt=7&appid="+appid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        preferences = getApplicationContext().getSharedPreferences("editing",MODE_PRIVATE);
        editor = preferences.edit();

        new Download7dayWeather().execute();

        // Construct the data source
/*
        // Create the adapter to convert the array to views

        array.add(new Item("TODAY, "+ monthday,"CLEAR",R.drawable.solar_icon_1,"21°","8°"));
        array.add(new Item("TOMORROW","CLOUDY",R.drawable.cloudy,"22°","17°"));
        cal.add(Calendar.DATE,2);
        String day = new SimpleDateFormat("EEEE").format(cal.getTime());
        array.add(new Item(day.toUpperCase(),"CLEAR",R.drawable.solar_icon_1,"21°","8°"));
        cal.add(Calendar.DATE,1);
        day = new SimpleDateFormat("EEEE").format(cal.getTime());
        array.add(new Item(day.toUpperCase(),"CLEAR",R.drawable.solar_icon_1,"21°","8°"));
        cal.add(Calendar.DATE,1);
        day = new SimpleDateFormat("EEEE").format(cal.getTime());
        array.add(new Item(day.toUpperCase(),"CLEAR",R.drawable.solar_icon_1,"21°","8°"));
        cal.add(Calendar.DATE,1);
        day = new SimpleDateFormat("EEEE").format(cal.getTime());
        array.add(new Item(day.toUpperCase(),"CLEAR",R.drawable.solar_icon_1,"21°","8°"));
        cal.add(Calendar.DATE,1);
        day = new SimpleDateFormat("EEEE").format(cal.getTime());
        array.add(new Item(day.toUpperCase(),"CLEAR",R.drawable.solar_icon_1,"21°","8°"));

        Adapter adapter = new Adapter(this, array);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(adapter);*/
    }


    private class Download7dayWeather extends AsyncTask<Void,Void,Void>{

        private final String TAG = Weather.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... voids) {

            HTTPHandler httpHandler = new HTTPHandler();

            String jsonStr = httpHandler.requestData(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if(jsonStr != null){
                try {
                    //DELETE ALL RECORDS
                    Log.d("deleteAll: ", "Deleting all items ..");
                    db.deleteAllItems();

                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray list = jsonObject.getJSONArray("list");

                    for(int i=0;i<list.length();i++){

                        Item item = null;
                        JSONObject index = list.getJSONObject(i);

                        //JSONObject main = index.getJSONObject("main"); //mykey
                        //String temp_min = main.getString("temp_min");
                        //String temp_max = main.getString("temp_max");
                        JSONObject temp = index.getJSONObject("temp");
                        String temp_min = temp.getString("min");
                        String temp_max = temp.getString("max");

                        JSONArray weather = index.getJSONArray("weather");
                        JSONObject weatherInfo = weather.getJSONObject(0);
                        String mainWeatherStatus = weatherInfo.getString("main");

                        if(mainWeatherStatus.equals("Clear"))
                            iconID=R.drawable.solar_icon_1;
                        else if(mainWeatherStatus.equals("Clouds")) {
                            iconID = R.drawable.cloudy;
                            mainWeatherStatus = "Cloudy";
                        }

                        if(i!=0 && i!=1){
                            day = new SimpleDateFormat("EEEE").format(cal.getTime());
                            item = new Item(day.toUpperCase(),mainWeatherStatus.toUpperCase(),iconID,temp_max+"°",temp_min+"°");
                            array.add(item);
                            cal.add(Calendar.DATE,1);
                        }
                        else if(i==1) {
                            item = new Item("TOMORROW",mainWeatherStatus.toUpperCase(),iconID,temp_max+"°",temp_min+"°");
                            array.add(item);
                            cal.add(Calendar.DATE, 2);
                            String day = new SimpleDateFormat("EEEE").format(cal.getTime());
                        }
                        else if(i==0) {
                            item = new Item("TODAY, " + monthday, mainWeatherStatus.toUpperCase(), iconID, temp_max + "°", temp_min + "°");
                            array.add(item);
                        }
                        // Inserting Contacts
                        Log.d("Insert: ", "Inserting ..");
                        db.addItem(item,i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
            else {
                // Reading all contacts
                Log.d("Reading: ", "Reading all items..");
                array = db.getAllItems();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Adapter adapter = new Adapter(Weather.this, array);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lv);
            listView.setAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {
            editor.putBoolean("loggedIn",false);
            editor.commit();
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}