package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    TextView confirmedIndian;
    TextView ConfirmedForeign;
    TextView deaths;
    TextView discharged;
    TextView confirmedIndianResult;
    TextView confirmedForeignResult;
    TextView dischargedResult;
    TextView deathsResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        confirmedIndian=(TextView)findViewById(R.id.confirmedIndian);
        ConfirmedForeign=(TextView)findViewById(R.id.ConfirmedForeign);
        deaths=(TextView)findViewById(R.id.deaths);
        discharged=(TextView)findViewById(R.id.discharged);

        confirmedIndianResult=(TextView)findViewById(R.id.confirmedIndianResult);
        confirmedForeignResult=(TextView)findViewById(R.id.confirmedForeignResult);
        dischargedResult=(TextView)findViewById(R.id.dischargedResult);
        deathsResult=(TextView)findViewById(R.id.deathsResult);

        Intent in = getIntent();

        confirmedIndianResult.setText(in.getStringExtra("confirmedCasesIndian"));
        confirmedForeignResult.setText(in.getStringExtra("confirmedCasesForeign"));
        dischargedResult.setText(in.getStringExtra("discharged"));
        deathsResult.setText(in.getStringExtra("deaths"));

   }
}
