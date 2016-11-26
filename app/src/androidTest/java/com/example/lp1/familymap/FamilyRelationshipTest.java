package com.example.lp1.familymap;

import android.os.Bundle;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.example.lp1.familymap.model.Login;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by lp1 on 4/16/16.
 */
public class FamilyRelationshipTest {

    private ListAdapter listAdapter;
    private PersonExpandableListActivity personActivity;
    private Login loginModel;


    void testFamilyRelationship(Bundle savedInstance){
        loginModel = Login.getInstance();

        // can make person map of 2 people


        // has family
        Map<String,ArrayList<String>> myMap = new HashMap<>();
        ArrayList<String> personInfo = new ArrayList<>();
        personInfo.add("person");
        personInfo.add("lastName");
        personInfo.add("m");
        personInfo.add("spouse1");
        personInfo.add("parent1");
        personInfo.add("parent2");
        myMap.put("person1", personInfo);

        personInfo.clear();
        personInfo.add("personb");
        personInfo.add("lastName2");
        personInfo.add("f");
        personInfo.add("spouse2");
        myMap.put("parent1", personInfo);

        // no family
        Map<String,ArrayList<String>> myMap2 = new HashMap<>();
        ArrayList<String> personInfo2 = new ArrayList<>();
        personInfo2.add("person");
        personInfo2.add("lastName");
        personInfo2.add("m");
        personInfo2.add("spouse1");
        myMap2.put("person1", personInfo2);

        personInfo2.clear();
        personInfo2.add("personb");
        personInfo2.add("lastName2");
        personInfo2.add("f");
        personInfo2.add("spouse2");
        myMap2.put("parent1", personInfo2);


        ArrayList<String> familyTest = new ArrayList<>();
        familyTest.add(personInfo.get(1) + " " + personInfo.get(2) + " - father");

        loginModel.setAllPeople(myMap);
        personActivity = new PersonExpandableListActivity();
        personActivity.onCreate(savedInstance);


        // test 1 -- has family and is correct
        assertEquals(loginModel.getCurrentFamily(), familyTest);


        // test 2 -- has no family
        assertEquals(loginModel.getCurrentFamily(), null);





    }

}
