package com.example.lp1.familymap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;
import com.example.lp1.familymap.model.Login;

public class AmapActivity extends AppCompatActivity {


    private amazonMap mapFragment;
    LayoutInflater inflater;
    ViewGroup container;
    Login loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginModel = Login.getInstance();
        FragmentManager fm = this.getSupportFragmentManager();
        mapFragment = new amazonMap();

        //mapFragment = new amazonMap();
        fm.beginTransaction()
                .add(R.id.mapFragmentcontainer, mapFragment)
                .commit();

        //mapFragment.onCreateView(getLayoutInflater(),,savedInstanceState);
    }


    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                loginModel.setUseMap(true);
                Intent intent = new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_go_to_top, menu);
        return true;
    }


}
