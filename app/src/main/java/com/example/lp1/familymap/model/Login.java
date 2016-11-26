package com.example.lp1.familymap.model;

import com.amazon.geo.mapsv2.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by lp1 on 3/18/16.
 */
public class Login {

    private String userName;
    private String password;
    private String serverHost;
    private String serverPort;
    private String authenticationToken;
    private String firstname;
    private String lastname;
    private String postData;
    private String gender;
    private String personId;
    private Boolean canUseMap = false;
    private Map<String,ArrayList<String>> allPeople = new HashMap<>();
    private Map<String,ArrayList<String>> allEvents = new HashMap<>();
    private String currentPersonId;
    private Boolean mapActivityUse = false;
    private ArrayList<Boolean> currentFilter = new ArrayList<>();
    private TreeMap<String,Float> allFilterTypes = new TreeMap<>();
    private LatLng currentLocation;
    private ArrayList<String> currentLifeEvents = new ArrayList<>();
    private String currentFirstName;
    private String currentLastName;
    private String currentNameOfLocation;
    private String currentGender;
    private boolean useMap;
    private boolean didLoginIn = false;

    public ArrayList<String> getCurrentFamily() {
        return currentFamily;
    }

    public void setCurrentFamily(ArrayList<String> currentFamily) {
        this.currentFamily = currentFamily;
    }

    private ArrayList<String> currentFamily;

    public boolean isDidLoginIn() {
        return didLoginIn;
    }

    public void setDidLoginIn(boolean didLoginIn) {
        this.didLoginIn = didLoginIn;
    }

    private static Login instance;

    private Login(){}

    public static Login getInstance(){
        if (instance == null){
            instance = new Login();
        }
        return instance;
    }

    public String getPersonId() {
        return personId;
    }

    public boolean isUseMap() {

        return useMap;
    }

    public void setUseMap(boolean useMap) {
        this.useMap = useMap;
    }

    public String getCurrentFirstName() {
        return currentFirstName;
    }

    public void setCurrentFirstName(String currentFirstName) {
        this.currentFirstName = currentFirstName;
    }

    public String getCurrentLastName() {
        return currentLastName;
    }

    public void setCurrentLastName(String currentLastName) {
        this.currentLastName = currentLastName;
    }

    public String getCurrentNameOfLocation() {
        return currentNameOfLocation;
    }

    public void setCurrentNameOfLocation(String currentNameOfLocation) {
        this.currentNameOfLocation = currentNameOfLocation;
    }

    public String getCurrentGender() {
        return currentGender;
    }

    public void setCurrentGender(String currentGender) {
        this.currentGender = currentGender;
    }

    public ArrayList<String> getCurrentLifeEvents() {
        return currentLifeEvents;
    }

    public void setCurrentLifeEvents(ArrayList<String> currentLifeEvents) {
        this.currentLifeEvents = currentLifeEvents;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public String getPostData() {
        return postData;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void addPeople(String str, ArrayList<String> al){
        allPeople.put(str,al);
    }

    public void addEvent(String str, ArrayList<String> al){

        allEvents.put(str,al);
    }

    public Map<String, ArrayList<String>> getAllPeople() {
        return allPeople;
    }

    public Map<String, ArrayList<String>> getAllEvents() {
        return allEvents;
    }

    public Boolean getCanUseMap() {
        return canUseMap;
    }

    public void setCanUseMap(Boolean canUseMap) {
        this.canUseMap = canUseMap;
    }

    public void setCurrentPersonId(String currentPersonId) {
        this.currentPersonId = currentPersonId;

        currentFirstName = allPeople.get(currentPersonId).get(1);
        currentLastName = allPeople.get(currentPersonId).get(2);
        currentGender = allPeople.get(currentPersonId).get(3);
    }

    public String getCurrentPersonId() {
        return currentPersonId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName(String personId){

        return  allPeople.get(personId).get(1) + " "
                + allPeople.get(personId).get(2);
    }

    public String findPersonId(String name){
        String[] fullName = name.split("\\s");
        String personId = "";
        Iterator it = allPeople.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String firstName = ((ArrayList<String>)pair.getValue()).get(1);
            String lastName = ((ArrayList<String>)pair.getValue()).get(2);

            if (firstName.equals(fullName[0]) && lastName.equals(fullName[1])){
                personId = pair.getKey().toString();

            }
        }

        return personId;
    }


    public void setMapActivityUsetoTrue(){
        mapActivityUse = true;
    }

    public Boolean getMapActivityUse() {
        return mapActivityUse;
    }

    public ArrayList<Boolean> getCurrentFilter() {
        return currentFilter;
    }

    public void setCurrentFilter(ArrayList<Boolean> currentFilter) {
        this.currentFilter = currentFilter;
    }

    public TreeMap<String, Float> getAllFilterTypes() {
        return allFilterTypes;
    }

    public void setAllFilterTypes(TreeMap<String, Float> allFilterTypes) {
        this.allFilterTypes = allFilterTypes;
    }


    public String getEventDescription(String eventId){
        return allEvents.get(eventId).get(5) + ": " + allEvents.get(eventId).get(4) + ", "
                + allEvents.get(eventId).get(3) + " (" +
                allEvents.get(eventId).get(6)
                +")";
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String eventId) {
        LatLng position = new LatLng(
                Double.parseDouble(allEvents.get(eventId).get(1)),
                Double.parseDouble(allEvents.get(eventId).get(2)));
        currentNameOfLocation = allEvents.get(eventId).get(5) + ": " +
                allEvents.get(eventId).get(4) + ", "
                + allEvents.get(eventId).get(3);
        currentFirstName = allPeople.get(allEvents.get(eventId).get(0)).get(1);
        currentLastName = allPeople.get(allEvents.get(eventId).get(0)).get(2);
        currentGender = allPeople.get(allEvents.get(eventId).get(0)).get(3);

        this.currentLocation = position;
    }


    public void setAllPeople(Map<String, ArrayList<String>> allPeople) {
        this.allPeople = allPeople;
    }

    public void setAllEvents(Map<String, ArrayList<String>> allEvents) {
        this.allEvents = allEvents;
    }
}
