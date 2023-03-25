package com.example.myapplication;

import static org.testng.AssertJUnit.assertEquals;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, CustomList.OnDeleteClickListener {

    private ListView listView;
    private Button sortButton;
    private ArrayList<City> cities;
    private CustomList customList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        sortButton = findViewById(R.id.sort_button);

        cities = new ArrayList<>();
        cities.add(new City("Vancouver", "British Columbia"));
        cities.add(new City("Calgary", "Alberta"));
        cities.add(new City("Toronto", "Ontario"));
        cities.add(new City("Montreal", "Quebec"));

        customList = new CustomList(this, cities);
        listView.setAdapter(customList);
        listView.setOnItemClickListener(this);

        sortButton.setOnClickListener(this);

        customList.setOnDeleteClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        City city = cities.get(position);
        String message = "You clicked on " + city.getCityName() + " in " + city.getProvinceName();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == sortButton) {
            Collections.sort(cities, new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o1.getCityName().compareToIgnoreCase(o2.getCityName());
                }
            });
            customList.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        cities.remove(position);
        customList.notifyDataSetChanged();
    }


    @Test
    public void testSort() {
        // Initialize the cities list
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Vancouver", "British Columbia"));
        cities.add(new City("Calgary", "Alberta"));
        cities.add(new City("Toronto", "Ontario"));
        cities.add(new City("Montreal", "Quebec"));

        // Sort the list
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.getCityName().compareToIgnoreCase(o2.getCityName());
            }
        });

        // Check if the list is sorted correctly
        assertEquals("Calgary", cities.get(0).getCityName());
        assertEquals("Montreal", cities.get(1).getCityName());
        assertEquals("Toronto", cities.get(2).getCityName());
        assertEquals("Vancouver", cities.get(3).getCityName());
    }

    @Test
    public void testDelete() {
        // Initialize the cities list
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Vancouver", "British Columbia"));
        cities.add(new City("Calgary", "Alberta"));
        cities.add(new City("Toronto", "Ontario"));
        cities.add(new City("Montreal", "Quebec"));
        cities.remove(0);
        assertEquals(3, cities.size());
        assertEquals("Calgary", cities.get(0).getCityName());
        assertEquals("Toronto", cities.get(1).getCityName());
        assertEquals("Montreal", cities.get(2).getCityName());

    }
}


