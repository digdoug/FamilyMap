package com.example.lp1.familymap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.lp1.familymap.model.Login;

/**
 * Created by lp1 on 4/12/16.
 */
public class SearchActivityDataAdapter extends RecyclerView.Adapter<SearchActivityDataAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    ArrayList<SearchActivityRowItems> data = new ArrayList<>();
    private PersonExpandableListActivity act;
    private AmapActivity act2;
    Context context;
    Login loginModel;

    public SearchActivityDataAdapter(Context context,
                                     ArrayList<SearchActivityRowItems> data,
                                     Activity activity){

        inflater= LayoutInflater.from(context);
        this.data = data;

        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = inflater.inflate(R.layout.search_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SearchActivityRowItems current = data.get(position);
        loginModel = Login.getInstance();
        holder.title.setText(current.text);
        String type = (String) holder.title.getText();
        String[] ar = type.split("\\s+");
        if (ar.length > 2){
            holder.icon.setImageResource(R.drawable.ic_info_black_24dp);
        } else {
            holder.icon.setImageResource(R.drawable.ic_pregnant_woman_black_48dp);
        }


    }

    @Override
    public int getItemCount() {return data.size();}

    class MyViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        TextView title;
        ImageView icon;
        String type;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.search_row_text);
            //type = itemView.findViewById(R.id.search_row_text);

            icon = (ImageView) itemView.findViewById(R.id.search_row_image);

        }


        @Override
        public void onClick(View v) {
            System.out.println("title " + title);
            System.out.println("icon " + icon);
            type = (String) title.getText();
            String[] stringAr = type.split("\\s+");
            loginModel.setCurrentPersonId(loginModel.findPersonId(type));
            if (stringAr.length > 2){ // then it's an event
                Intent intent = new Intent(context,AmapActivity.class);
                context.startActivity(intent);
            } else {

                Intent intent = new Intent(context,PersonExpandableListActivity.class);
                context.startActivity(intent);
            }


        }
    }

}
