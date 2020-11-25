package com.example.bhavik;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<CountryInformation> {
    private Context context;
    private List<CountryInformation> countryInformationsList;
    private List<CountryInformation> countryInformationListFiltered;
    private List<MainData> dataList;
    private RoomDB database;

    public MyCustomAdapter(Context context, List<CountryInformation> countryInformationsList) {
        super(context, R.layout.list_custom_item,countryInformationsList);
        this.context=context;
        this.countryInformationsList=countryInformationsList;
        this.countryInformationListFiltered=countryInformationsList;
    }


    @Nullable
    @Override
    public CountryInformation getItem(int position) {
        return countryInformationListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    @Override
    public int getCount() {
        return countryInformationListFiltered.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView tvCountryName=view.findViewById(R.id.tvCountryName);
        ImageView imageView=view.findViewById(R.id.imageFlag);
        tvCountryName.setText(countryInformationsList.get(position).getName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(context).load(countryInformationsList.get(position).getFlag()).apply(options).into(imageView);
        return view;
    }
}
