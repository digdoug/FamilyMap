package com.example.lp1.familymap;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView;


import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.example.lp1.familymap.model.Login;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link amazonMap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link amazonMap#newInstance} factory method to
 * create an instance of this fragment.
 */


public class amazonMap extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Login loginModel;
    private TreeMap<String,Float> eventTypewColor = new TreeMap<>();
    private Map<String,ArrayList<String>> events = new HashMap<>();
    private Map<String,ArrayList<String>> people = new HashMap<>();
    private Marker currentMarker;

    //private OnFragmentInteractionListener mListener;

    public amazonMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment amazonMap.
     */
    // TODO: Rename and change types and number of parameters
    public static amazonMap newInstance(String param1, String param2) {
        amazonMap fragment = new amazonMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);




        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        loginModel = Login.getInstance();
        people = loginModel.getAllPeople();
        events = loginModel.getAllEvents();

        ViewGroup viewGroup = (ViewGroup) getView();


    }




    // fragement.getmapasync
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View aView = inflater.inflate(R.layout.fragment_map, container, false);
        final ImageView myView = (ImageView) aView.findViewById(R.id.image);
        final TextView textView1 = (TextView) aView.findViewById(R.id.text);
        final TextView textView2 = (TextView) aView.findViewById(R.id.text2);
        // support fragment manager
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment smp =  (SupportMapFragment) fm.findFragmentById(R.id.map);

        // Linear layout listener
        LinearLayout personDetails = (LinearLayout) aView.findViewById(R.id.personData);
        personDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("just clicked on person detail");
                // transition to person activity
                if (currentMarker != null){
                    loginModel.setCurrentPersonId(events.get(currentMarker.getTitle()).get(0));
                    Intent intent = new Intent(getActivity(), PersonExpandableListActivity.class);
                    startActivity(intent);
                }

            }
        });

        smp.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final AmazonMap amazonMap) {

                if (loginModel.getCurrentLocation() != null){
                    amazonMap.animateCamera(CameraUpdateFactory.newLatLng(loginModel.getCurrentLocation()));

                    Drawable genderIcon;
                    if (loginModel.getCurrentGender().equals("m")) {
                        genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male).
                                colorRes(R.color.male_icon).sizeDp(40);
                    } else {
                        genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female).
                                colorRes(R.color.female_icon).sizeDp(40);
                    }

                    myView.setImageDrawable(genderIcon);
                    textView1.setText(loginModel.getCurrentFirstName() +
                           " " + loginModel.getCurrentLastName());
                    textView2.setText(loginModel.getCurrentNameOfLocation());
                }
                amazonMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //final Dialog d = new Dialog(getContext());
                        //aView.findViewById(R.id.displayatBottom);
                        currentMarker = marker;



                        String personId = events.get(marker.getTitle()).get(0);

                        String gender = people.get(personId).get(3);
                        String name = people.get(personId).get(1) + " " + people.get(personId).get(2);
                        String eventAndLocation = marker.getSnippet() + ": " +
                                events.get(marker.getTitle()).get(4) + ", " + events.get(marker.getTitle()).get(3)
                                + " (" + events.get(marker.getTitle()).get(6) + ")";

                        Drawable genderIcon;
                        if (gender.equals("m")) {
                            genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male).
                                    colorRes(R.color.male_icon).sizeDp(40);
                        } else {
                            genderIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female).
                                    colorRes(R.color.female_icon).sizeDp(40);
                        }

                        myView.setImageDrawable(genderIcon);
                        textView1.setText(name);
                        textView2.setText(eventAndLocation);


                        return false;
                    }
                });


                // relative layout
                // image view
                Iterator it = events.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    double lat = Double.parseDouble(((ArrayList<String>) pair.getValue()).get(1));
                    double lon = Double.parseDouble(((ArrayList<String>) pair.getValue()).get(2));


                    String eventType = ((ArrayList<String>) pair.getValue()).get(5);

                    if (!eventTypewColor.containsKey(eventType)) {
                        float hue = (float) Math.random() * 360; // theoretically this could generate the same color
                        eventTypewColor.put(eventType, hue);
                    }

                    LatLng point = new LatLng(lat, lon);
                    MarkerOptions opt = new MarkerOptions()
                            .position(point)
                            .title(pair.getKey().toString())
                            .snippet(eventType)
                            .icon(BitmapDescriptorFactory.defaultMarker(eventTypewColor.get(eventType)));

                    amazonMap.addMarker(opt);
                    //it.remove(); // avoids a ConcurrentModificationException
                }


            }
        });
        super.onCreateView(inflater, container, savedInstanceState);
        loginModel.setAllFilterTypes(eventTypewColor);
        return aView;
    }





}


// person Activity calls Map Activity