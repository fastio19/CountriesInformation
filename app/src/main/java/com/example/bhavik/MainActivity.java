package com.example.bhavik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;
    public static List<CountryInformation> countryInformationList=new ArrayList<>();

    CountryInformation countryInformation;

    MyCustomAdapter myCustomAdapter;
    List<MainData> dataList=new ArrayList<>();
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        simpleArcLoader=findViewById(R.id.loader);
        getSupportActionBar().setTitle("Asian Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fetchData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position",position));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if(item.getItemId()==android.R.id.home)
                finish();
         return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url="https://restcountries.eu/rest/v2/all";
        simpleArcLoader.start();
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String countryName=jsonObject.getString("name");
                                String capital=jsonObject.getString("capital");
                                String flagUrl=jsonObject.getString("flag");
                                Log.e("Bhavik",flagUrl);
                                String region=jsonObject.getString("region");
                                String subregion=jsonObject.getString("subregion");
                                String population=jsonObject.getString("population");
                                String borders=jsonObject.getString("borders");
                                JSONArray jsonArray1=(JSONArray) jsonObject.get("languages");
                                Log.i("my info :",flagUrl);
                                String languages="";
                                for(int j=0;j<jsonArray1.length();j++){
                                    JSONObject jsonObject1=jsonArray1.getJSONObject(j);
                                    String temp=jsonObject1.getString("name");
                                    if(j>0 && j<jsonArray1.length())
                                        languages+=" , ";
                                    languages=languages+" "+temp;
                                }
                                countryInformation=new CountryInformation(countryName,capital,
                                        flagUrl, region,subregion,population,
                                        borders,languages);
                                countryInformationList.add(countryInformation);

                            }
                            myCustomAdapter=new MyCustomAdapter(MainActivity.this,countryInformationList);
                            listView.setAdapter(myCustomAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
