package com.example.lp1.familymap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import com.example.lp1.familymap.model.Login;

public class FilterActivity extends AppCompatActivity {

    ArrayList<String> listItems = new ArrayList<String>();
    ListView lv;
    Context context;
    ArrayAdapter<String> adapter;
    Login loginModel;
    ArrayList<Boolean> currentFiler = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginModel=Login.getInstance();

        // dynamic data
        Set<String> allKeys = loginModel.getAllFilterTypes().keySet();
        for (String s: allKeys){
            listItems.add(s);
        }
        // by side
        listItems.add("Father's Side");
        listItems.add("Mother's Side");
        // by gender
        listItems.add("Male Events");
        listItems.add("Female Events");


        for (int i = 0;i < listItems.size();i ++){
            currentFiler.add(true);
        }
        loginModel.setCurrentFilter(currentFiler);
        context = this;
        lv = (ListView) findViewById(R.id.filterListView);
        String[] listOfItems = listItems.toArray(new String[0]);

        lv.setAdapter(new CustomAdapter(this,listOfItems));

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.up:
                Intent intent = new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
