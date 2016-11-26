package com.example.lp1.familymap;

import android.widget.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lp1.familymap.model.Login;

import java.util.ArrayList;

/**
 * Created by lp1 on 4/12/16.
 */
public class CustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    Login loginModel;
    private static LayoutInflater inflater=null;
    public CustomAdapter(FilterActivity mainActivity, String[] prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loginModel = Login.getInstance();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        Switch switcher;
        View rowView;
        rowView = inflater.inflate(R.layout.filter_list_view_row, null);
        holder.tv=(TextView) rowView.findViewById(R.id.listViewtext1);
        switcher = (Switch) rowView.findViewById(R.id.switch1);
        holder.tv.setText(result[position]);
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<Boolean> tempfilter = new ArrayList<Boolean>();
                tempfilter = loginModel.getCurrentFilter();
                if (isChecked){
                    tempfilter.set(position,true);
                    loginModel.setCurrentFilter(tempfilter);
                } else {
                    tempfilter.set(position,false);
                    loginModel.setCurrentFilter(tempfilter);
                }
            }
        });

        return rowView;
    }



}

