package com.example.lp1.familymap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.lp1.familymap.model.Login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {

    Login loginModel;
    private Map<String,ArrayList<String>> events = new HashMap<>();
    private Map<String,ArrayList<String>> people = new HashMap<>();
    private ArrayList<String> currentSearchData = new ArrayList<>();
    private RecyclerView searchRecyclerView;
    SearchActivityDataAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loginModel = Login.getInstance();
        events = loginModel.getAllEvents();
        people = loginModel.getAllPeople();

        SearchView searchView = (SearchView) findViewById(R.id.search_text_window);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }


            // just use this one
            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchData = new ArrayList<String>();
                getCurrentSearchData(newText);
                RelativeLayout searchDataRelativeView = (RelativeLayout) findViewById(R.id.search_relative_view);
                searchDataRelativeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                showCurrentData();
                System.out.println("");
                return false;
            }
        });
    System.out.println("");
    }

    public void getCurrentSearchData(String newText) {
        // first or last name in people
        // -----// insert person id
        newText = newText.toLowerCase();
        Iterator it = people.entrySet().iterator();
        String personName;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<String> personInfo = ((ArrayList<String>) pair.getValue());
            if (personInfo.get(1).toLowerCase().contains(newText)) { // first name
                personName = loginModel.getName(pair.getKey().toString());
                addIfNotInArrayList(personName);
            } else if (personInfo.get(2).toLowerCase().contains(newText)) { // last name
                personName = loginModel.getName(pair.getKey().toString());
                addIfNotInArrayList(personName);
            }
        }

            // countries
            // cities
            // descriptions
            // years
            // ----- // insert event id
            Iterator it2 = events.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pair = (Map.Entry) it2.next();
                ArrayList<String> eventInfo = ((ArrayList<String>) pair.getValue());
                String eventDescription;
                if (eventInfo.get(3).toLowerCase().contains(newText)) { // Country
                    eventDescription = loginModel.getEventDescription(
                            pair.getKey().toString());
                    addIfNotInArrayList(eventDescription);
                } else if (eventInfo.get(4).toLowerCase().contains(newText)) { // city
                    eventDescription = loginModel.getEventDescription(
                            pair.getKey().toString());
                    addIfNotInArrayList(eventDescription);
                } else if (eventInfo.get(5).toLowerCase().contains(newText)) { // description
                    eventDescription = loginModel.getEventDescription(
                            pair.getKey().toString());
                    addIfNotInArrayList(eventDescription);
                } else if (eventInfo.get(6).toLowerCase().contains(newText)) { // year
                    eventDescription = loginModel.getEventDescription(
                            pair.getKey().toString());
                    addIfNotInArrayList(eventDescription);
                }

            }
            System.out.println("");
        }


    public void addIfNotInArrayList(String info){
        Boolean isIn = false;
        for (int i = 0;i < currentSearchData.size();i++){
                if (currentSearchData.get(i).equals(info)){
                    isIn = true;
                }
            }
        if (!isIn){
            currentSearchData.add(info);
        }
    }

    public void showCurrentData(){

        searchRecyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        // set layout manager
        adapter= new SearchActivityDataAdapter(this,getCurrentSearchData(),this);

        searchRecyclerView.setAdapter(adapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public ArrayList<SearchActivityRowItems> getCurrentSearchData(){
        ArrayList<SearchActivityRowItems> data = new ArrayList<>();
        ArrayList<Integer> icons = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0; i < currentSearchData.size(); i++){
            icons.add(R.drawable.filter);
            titles.add(currentSearchData.get(i));
        }

        for (int i = 0; i < icons.size() && i< titles.size();i++){
            SearchActivityRowItems current = new SearchActivityRowItems();
            current.iconId = icons.get(i);
            current.text = titles.get(i);
            data.add(current);
        }

        return data;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.up:
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                SearchActivity.this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_go_to_top, menu);
        return true;
    }

}
