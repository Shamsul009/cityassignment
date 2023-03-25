package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CustomList extends ArrayAdapter<City> {

    private ArrayList<City> cities;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public CustomList(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
        this.cities = cities;
        this.context = context;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.content, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.cityName = convertView.findViewById(R.id.city_text);
            viewHolder.provinceName = convertView.findViewById(R.id.province_text);
            viewHolder.deleteButton = convertView.findViewById(R.id.delete_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        City city = cities.get(position);

        viewHolder.cityName.setText(city.getCityName());
        viewHolder.provinceName.setText(city.getProvinceName());

        viewHolder.deleteButton.setTag(position);
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });

        return convertView;
    }

    public void sortCities() {
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                return c1.getCityName().compareToIgnoreCase(c2.getCityName());
            }
        });
        notifyDataSetChanged();
    }



    private static class ViewHolder {
        private TextView cityName;
        private TextView provinceName;
        private Button deleteButton;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}




