package com.example.lp1.familymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lp1.familymap.model.Login;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener{



    private LoginFragment loginFragment;
    private amazonMap mapFragment;
    private Login login;

    public void setLoginFragment(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fm = this.getSupportFragmentManager();
        // inflate
        loginFragment = (LoginFragment)fm.findFragmentById(R.id.fragmentLayout);
        //mapFragment = (amazonMap) fm.findFragmentById(R.id.fragmentLayout);
        login = Login.getInstance();
     if (loginFragment == null && !login.getCanUseMap()){
         loginFragment = LoginFragment.newInstance("string","string2");
         fm.beginTransaction()
                 .add(R.id.fragmentContainer, loginFragment)
                 .commit();
        }
        if (login.isUseMap()){
            changetoMap();
        }
        //System.out.println("something");
        // do some replacement stuff with a map Fragment


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (login.getCanUseMap()){
            getMenuInflater().inflate(R.menu.icon_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        System.out.println(item.toString());

        switch (item.toString()){
            case "search":
                System.out.println("in search");
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case "filter":
                System.out.println("in filter");
                Intent intent2 = new Intent(this, FilterActivity.class);
                startActivity(intent2);
                break;
            case "settings":
                System.out.println("in settings");
                Intent intent3 = new Intent(this, SettingsActivity.class);
                startActivity(intent3);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*public void onPrepareOptionsMenu(){

    }*/




    public void changetoMap(){
        mapFragment = new amazonMap();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragmentContainer,mapFragment,"string")
                .commit();
    }

    public void backToLogin(){
        FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = LoginFragment.newInstance("string","string2");
        fm.beginTransaction()
                .replace(R.id.fragmentContainer, loginFragment)
                .commit();
    }
    //invalidateOptionsMenu()

}
