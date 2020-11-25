package com.example.bhavik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private int positionCountry;
    TextView countryName,capital,population,borders,region,subregion,languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent=getIntent();
        positionCountry=intent.getIntExtra("position",0);
        countryName=findViewById(R.id.countryName);
        capital=findViewById(R.id.capital);
        population=findViewById(R.id.population);
        region=findViewById(R.id.region);
        subregion=findViewById(R.id.subregion);
        languages=findViewById(R.id.languages);
        borders=findViewById(R.id.borders);

        countryName.setText(MainActivity.countryInformationList.get(positionCountry).getName());
        capital.setText(MainActivity.countryInformationList.get(positionCountry).getCapital());
        population.setText(MainActivity.countryInformationList.get(positionCountry).getPopulation());
        region.setText(MainActivity.countryInformationList.get(positionCountry).getRegion());
        subregion.setText(MainActivity.countryInformationList.get(positionCountry).getSubregion());
        languages.setText(MainActivity.countryInformationList.get(positionCountry).getLanguages());
        borders.setText(MainActivity.countryInformationList.get(positionCountry).getBorders());

    }



    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
