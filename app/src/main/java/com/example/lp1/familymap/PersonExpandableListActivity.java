package com.example.lp1.familymap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lp1.familymap.model.Login;
import com.example.lp1.familymap.ListAdapter;

public class PersonExpandableListActivity extends AppCompatActivity {

    private Login loginModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_expandable_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ExpandableListAdapter listAdapter = new com.example.lp1.familymap.ListAdapter(getBaseContext());
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.personExpandableListView);
        listView.setAdapter(listAdapter);

        loginModel = Login.getInstance();
        TextView textView = (TextView) findViewById(R.id.firstName);
        textView.setText(loginModel.getFirstname());
        TextView textView2 = (TextView) findViewById(R.id.lastName);
        textView2.setText(loginModel.getLastname());
        TextView textView3 = (TextView) findViewById(R.id.gender);
        String gender;
        if (loginModel.getGender().equals("m")){
            gender = "male";
        } else {
            gender = "female";
        }
        textView3.setText(gender);





        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            /**
             * Callback method to be invoked when a child in this expandable list has
             * been clicked.
             *
             * @param parent        The ExpandableListView where the click happened
             * @param v             The view within the expandable list/ListView that was clicked
             * @param groupPosition The group position that contains the child that
             *                      was clicked
             * @param childPosition The child position within the group
             * @param id            The row id of the child that was clicked
             * @return True if the click was handled
             */
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String headerName = (String) parent.getExpandableListAdapter().getGroup(groupPosition);
                String childString = (String) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                int index = childPosition + 1;

                if (groupPosition == 0){
                    loginModel.setCurrentLocation(loginModel.getCurrentLifeEvents().get(
                            childPosition
                    ));
                    switchToMapActivity(childString);
                } else {
                    loginModel.setCurrentPersonId(loginModel.findPersonId(childString));
                    switchToPersonActivity(childString);
                }
                return true;
            }
        });

    }

    public void switchToMapActivity(String childString){
        loginModel.setMapActivityUsetoTrue();
        // get current location
        Intent intent = new Intent(this,AmapActivity.class);
        startActivity(intent);
    }

    public void switchToPersonActivity(String childString){
        Intent intent = new Intent(this,PersonExpandableListActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        if (item.toString().equals("up")){
            finish();
        }
        switch (item.getItemId()){
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            default:
                loginModel.setUseMap(true);
                Intent intent = new Intent(PersonExpandableListActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PersonExpandableListActivity.this.startActivity(intent);
                return true;

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_go_to_top, menu);
        return true;
    }

}
