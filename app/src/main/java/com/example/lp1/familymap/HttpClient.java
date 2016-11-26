package com.example.lp1.familymap;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.lp1.familymap.model.*;

/**
 * Created by lp1 on 3/18/16.
 */
public class HttpClient {

    public HttpClient(){
        login = Login.getInstance();
    }
    private String postType;
    private Login login;
    private String requestBody;

    public String getURL(URL url){


        post(url);
        try {
            url = new URL("http://" +login.getServerHost()+":"+
                    login.getServerPort()+"/person/");
            post2(url);
            url = new URL("http://" +login.getServerHost()+":"+
                    login.getServerPort()+"/event/");
            getEvents(url);
            System.out.println("here");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void parseObjectforToken(String string){
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("message")) {

            } else{
                login.setPersonId(jsonObject.getString("personId"));
                login.setAuthenticationToken(jsonObject.getString("Authorization"));
                login.setUserName(jsonObject.getString("userName"));
                // set new post Data
                login.setPostData("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void parseObjectforNames(String string){

        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("message")) {
            } else {
                //login.setFirstname(jsonObject.getString("firstName"));
                //login.setLastname(jsonObject.getString("lastName"));
                for (int i = 0; i < jsonObject.getJSONArray("data").length();i++) {
                    JSONObject event = jsonObject.getJSONArray("data").getJSONObject(i);
                    String personId = event.getString("personID");
                    ArrayList<String> everythingelse = new ArrayList<>();

                    // perhaps I just need to remember this order
                    String descendant = event.getString("descendant");
                    everythingelse.add(descendant);
                    String firstName = event.getString("firstName");
                    everythingelse.add(firstName);
                    String lastName = event.getString("lastName");
                    everythingelse.add(lastName);
                    String gender = event.getString("gender");
                    everythingelse.add(gender);
                    if (event.has("spouse")){
                        String spouse = event.getString("spouse");
                        everythingelse.add(spouse);
                    }
                    if (event.has("mother")){
                        String mother = event.getString("mother");
                        everythingelse.add(mother);
                    }
                    if (event.has("father")){
                        String father = event.getString("father");
                        everythingelse.add(father);
                    }



                    login.addPeople(personId, everythingelse);
                    System.out.println("something");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void parseObjectForEvents(String string){
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("message")) {
            } else {
                for (int i = 0; i < jsonObject.getJSONArray("data").length();i++){
                    JSONObject event =  jsonObject.getJSONArray("data").getJSONObject(i);
                    String eventId = event.getString("eventID");
                    ArrayList<String> everythingelse = new ArrayList<>();

                    // perhaps I just need to remember this order
                    String personId = event.getString("personID");
                    everythingelse.add(personId);
                    String latitude = event.getString("latitude");
                    everythingelse.add(latitude);
                    String longitude = event.getString("longitude");
                    everythingelse.add(longitude);
                    String country = event.getString("country");
                    everythingelse.add(country);
                    String city = event.getString("city");
                    everythingelse.add(city);
                    String description = event.getString("description");
                    everythingelse.add(description);
                    String year = event.getString("year");
                    everythingelse.add(year);
                    String descendant = event.getString("descendant");
                    everythingelse.add(descendant);

                    login.addEvent(eventId, everythingelse);
                    System.out.println("something");

                }
                System.out.println("something");

                //login.setFirstname(jsonObject.getString("firstName"));
                //login.setLastname(jsonObject.getString("lastName"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void post(URL url){

        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set HTTP request headers, if necessary
            if (login.getAuthenticationToken() != null){
                connection.addRequestProperty("Authorization", login.getAuthenticationToken());
            }

            connection.connect();

            // Write post data to request body

            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(login.getPostData().getBytes());
            requestBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                parseObjectforToken(responseBodyData);

            }
            else {
                System.out.println("Server error");
            }
        }
        catch (IOException e) {

        }

    }

    public void post2(URL url){

        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");

            // Set HTTP request headers, if necessary
            if (login.getAuthenticationToken() != null){
                connection.addRequestProperty("Authorization", login.getAuthenticationToken());
            }

            connection.connect();

            // Write post data to request body

            /*OutputStream requestBody = connection.getOutputStream();
            requestBody.write(login.getPostData().getBytes());
            requestBody.close();*/

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                parseObjectforNames(responseBodyData);

            }
            else {
                System.out.println("Server error");
            }
        }
        catch (IOException e) {
            // IO ERROR
        }

    }

    public void getEvents(URL url){
        try {

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");

            // Set HTTP request headers, if necessary
            if (login.getAuthenticationToken() != null){
                connection.addRequestProperty("Authorization", login.getAuthenticationToken());
            }

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                parseObjectForEvents(responseBodyData);

            }
            else {
                System.out.println("Server error");
            }
        }
        catch (IOException e) {
            // IO ERROR
        }
    }



}
