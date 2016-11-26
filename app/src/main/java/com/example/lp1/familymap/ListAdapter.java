package com.example.lp1.familymap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.lp1.familymap.model.Login;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lp1 on 4/11/16.
 */
public class ListAdapter extends BaseExpandableListAdapter{




    /**
     * Created by Scott on 3/23/2016.
     */
        private Context context;

        public String[] headers = {"Life Events", "Family"};
        private static String[] group1 = {"event 1", "event 2"};
        private static String[] group2;
        private String[][] groups = {group1, group2};
        private Login loginModel;
        private String firstName;
        private String lastName;
        private String gender;
        private String motherId;
        private String fatherId;
        private ArrayList<String> kids = new ArrayList<>();
        private ArrayList<String> family = new ArrayList<>();
        private ArrayList<String> allTheirEvents = new ArrayList<>();

        public ListAdapter(Context context) {
            this.context = context;
            loginModel = Login.getInstance();
            setGroups();
        }

        public void setGroups(){
            Map<String,ArrayList<String>> events = loginModel.getAllEvents();
            Map<String,ArrayList<String>> people = loginModel.getAllPeople();


            String personId = loginModel.getCurrentPersonId();

            loginModel.setFirstname(people.get(personId).get(1));
            loginModel.setLastname(people.get(personId).get(2));
            loginModel.setGender(people.get(personId).get(3));
            gender = people.get(personId).get(3);

            String spouse = people.get(personId).get(4);

            if (people.get(personId).size() > 6){ // has parents include all family apparently
                motherId = people.get(personId).get(6);
                fatherId = people.get(personId).get(5);
                family.add(loginModel.getName(fatherId) + " - Father");
                family.add(loginModel.getName(motherId) + " - Mother");
            }
            family.add(loginModel.getName(spouse) + " - Spouse");
            // get kids
            Iterator it = people.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                ArrayList<String> personInfo = ((ArrayList<String>)pair.getValue());
                if (personInfo.size() > 6){ // kid has parents
                    if (gender.equals("f")){
                        if (personInfo.get(5).equals(personId)) {
                            if (personInfo.get(3).equals("m")){
                                kids.add(personInfo.get(1) + " "
                                        + personInfo.get(2) + " - Son");
                            } else {
                                kids.add(personInfo.get(1) + " "
                                        + personInfo.get(2) + " - Daughter");
                            }
                        }
                    } else {
                        if (personInfo.get(6).equals(personId)){
                            if (personInfo.get(3).equals("m")){
                                kids.add(personInfo.get(1) + " "
                                        + personInfo.get(2) + " - Son");
                            } else {
                                kids.add(personInfo.get(1) + " "
                                        + personInfo.get(2) + " - Daughter");
                            }
                        }
                    }
                }

            }
            if (kids.size() > 0){
                for (int i = 0 ; i < kids.size();i++){
                    family.add(kids.get(i));
                }
            }

            ArrayList<String> currLife = new ArrayList<>();
            // get Events
            Iterator it2 = events.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pair = (Map.Entry) it2.next();
                if (((ArrayList<String>)pair.getValue()).get(0).equals(personId)){
                    allTheirEvents.add(
                            loginModel.getEventDescription(pair.getKey().toString()
                            ));
                    currLife.add(pair.getKey().toString());
                }
            }


            loginModel.setCurrentLifeEvents(currLife);
            loginModel.setCurrentFamily(family);
            // set current life events

            String[] familyArray = family.toArray(new String[0]);
            String[] allthePersonsEvents = allTheirEvents.toArray(new String[0]);
            System.out.println("family Array " + Arrays.toString(familyArray));
            System.out.println("life event Array " + Arrays.toString(group1));
            group2 = familyArray;
            group1 = allthePersonsEvents;
            groups = new String[][]{group1, group2};

        }

        /**
         * Gets the number of groups.
         *
         * @return the number of groups
         */
        @Override
        public int getGroupCount() {
            return 2;
        }

        /**
         * Gets the number of children in a specified group.
         *
         * @param groupPosition the position of the group for which the children
         *                      count should be returned
         * @return the children count in the specified group
         */
        @Override
        public int getChildrenCount(int groupPosition) {
            return groups[groupPosition].length;
        }

        /**
         * Gets the data associated with the given group.
         *
         * @param groupPosition the position of the group
         * @return the data child for the specified group
         */
        @Override
        public Object getGroup(int groupPosition) {
            return headers[groupPosition];
        }

        /**
         * Gets the data associated with the given child within the given group.
         *
         * @param groupPosition the position of the group that the child resides in
         * @param childPosition the position of the child with respect to other
         *                      children in the group
         * @return the data of the child
         */
        @Override
        public Object getChild(int groupPosition, int childPosition) {

            return groups[groupPosition][childPosition];
        }

        /**
         * Gets the ID for the group at the given position. This group ID must be
         * unique across groups. The combined ID (see
         * {@link #getCombinedGroupId(long)}) must be unique across ALL items
         * (groups and all children).
         *
         * @param groupPosition the position of the group for which the ID is wanted
         * @return the ID associated with the group
         */
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        /**
         * Gets the ID for the given child within the given group. This ID must be
         * unique across all children within the group. The combined ID (see
         * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
         * (groups and all children).
         *
         * @param groupPosition the position of the group that contains the child
         * @param childPosition the position of the child within the group for which
         *                      the ID is wanted
         * @return the ID associated with the child
         */
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        /**
         * Indicates whether the child and group IDs are stable across changes to the
         * underlying data.
         *
         * @return whether or not the same ID always refers to the same object
         */
        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         * Gets a View that displays the given group. This View is only for the
         * group--the Views for the group's children will be fetched using
         * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
         *
         * @param groupPosition the position of the group for which the View is
         *                      returned
         * @param isExpanded    whether the group is expanded or collapsed
         * @param convertView   the old view to reuse, if possible. You should check
         *                      that this view is non-null and of an appropriate type before
         *                      using. If it is not possible to convert this view to display
         *                      the correct data, this method can create a new view. It is not
         *                      guaranteed that the convertView will have been previously
         *                      created by
         *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
         * @param parent        the parent that this view will eventually be attached to
         * @return the View corresponding to the group at the specified position
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if(convertView == null) {
                LayoutInflater inflaterInflater = (LayoutInflater)this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflaterInflater.inflate(R.layout.big_bold_text_view, null);
            }

            TextView labelListHeader = (TextView) convertView.findViewById(R.id.bigBoldLine);
            labelListHeader.setText(headerTitle);

            return convertView;
        }

        /**
         * Gets a View that displays the data for the given child within the given
         * group.
         *
         * @param groupPosition the position of the group that contains the child
         * @param childPosition the position of the child (for which the View is
         *                      returned) within the group
         * @param isLastChild   Whether the child is the last child within the group
         * @param convertView   the old view to reuse, if possible. You should check
         *                      that this view is non-null and of an appropriate type before
         *                      using. If it is not possible to convert this view to display
         *                      the correct data, this method can create a new view. It is not
         *                      guaranteed that the convertView will have been previously
         *                      created by
         *                      {@link #getChildView(int, int, boolean, View, ViewGroup)}.
         * @param parent        the parent that this view will eventually be attached to
         * @return the View corresponding to the child at the specified position
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childString = (String) getChild(groupPosition, childPosition);
            if(convertView == null) {
                LayoutInflater inflaterInflater = (LayoutInflater)this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflaterInflater.inflate(R.layout.my_text_view, null);
            }

            TextView labelListHeader = (TextView) convertView.findViewById(R.id.personExpandableListViewTextLine);

            labelListHeader.setText(childString);

            return convertView;
        }

        /**
         * Whether the child at the specified position is selectable.
         *
         * @param groupPosition the position of the group that contains the child
         * @param childPosition the position of the child within the group
         * @return whether the child is selectable.
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }



