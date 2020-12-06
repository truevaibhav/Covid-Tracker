package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String indianCases;
    String foreignCases;
    String dischargePatients;
    String peopleDied;
    Intent i;


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,  "Network Connection error :(", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String cityName=editText.getText().toString();
            super.onPostExecute(s);
            try{
                if(cityName=="") {

                    Toast.makeText(MainActivity.this, "Enter State Name!.", Toast.LENGTH_SHORT).show();
                }else {
                    JSONObject reader = new JSONObject(s);
                    JSONObject Data = reader.getJSONObject("data");
                    JSONArray array = Data.getJSONArray("regional");


                    ArrayList<String> loc = new ArrayList<>();
                    ArrayList<Integer> confirmedCasesIndian = new ArrayList<>();
                    ArrayList<Integer> confirmedCasesForeign = new ArrayList<>();
                    ArrayList<Integer> discharged = new ArrayList<>();
                    ArrayList<Integer> deaths = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject childObject = array.getJSONObject(i);

                        loc.add(childObject.getString("loc"));
                        confirmedCasesIndian.add(childObject.getInt("confirmedCasesIndian"));
                        confirmedCasesForeign.add(childObject.getInt("confirmedCasesForeign"));
                        discharged.add(childObject.getInt("discharged"));
                        deaths.add(childObject.getInt("deaths"));
                    }

                    String store = "";
                    for (int j = 0; j < array.length(); j++) {

                        store += loc.get(j);
                        if (loc.get(j).equals(cityName)) {
                            indianCases = confirmedCasesIndian.get(j).toString();
                            foreignCases = confirmedCasesForeign.get(j).toString();
                            dischargePatients = discharged.get(j).toString();
                            peopleDied = deaths.get(j).toString();

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData(v);
                                    i = new Intent(getApplicationContext(), Result.class);
                                    i.putExtra("confirmedCasesIndian", indianCases);
                                    i.putExtra("confirmedCasesForeign",foreignCases);
                                    i.putExtra("discharged",dischargePatients);
                                    i.putExtra("deaths",peopleDied);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error Occurred :(", Toast.LENGTH_SHORT).show();
            }

        }

    }

    EditText editText;
    ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=(EditText)findViewById(R.id.editText);
        button=(ImageView) findViewById(R.id.button);
    }

    public void getData(View view) {

        DownloadTask task = new DownloadTask();
        try {
            task.execute("https://api.rootnet.in/covid19-in/stats/latest");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Occurred :(", Toast.LENGTH_SHORT).show();
        }
    }
}

