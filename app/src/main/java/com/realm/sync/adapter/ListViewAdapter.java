package com.realm.sync.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.realm.sync.model.OpenWeather;
import com.realm.sync.data.RealmManager;
import com.realm.sync.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class ListViewAdapter extends BaseAdapter {


    private RealmList<OpenWeather> data = new RealmList<>();
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context) {

        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_view_item, parent, false);
        }
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        TextView tvItem2 = (TextView) convertView.findViewById(R.id.tvItem2);


        for (int i = 0; i < data.get(position).getWeather().size(); i++) {

            tvItem2.setText("\n" + "Id : " + data.get(position).getWeather().get(i).getId() + "\n\n" +
                    "Description : " + data.get(position).getWeather().get(i).getDescription() + "\n\n");

        }

        tvItem.setText("Name :" + data.get(position).getName() + "\n\n" +
                "Temp : " + data.get(position).getMain().getTemp() + "\n\n" +
                "Humidity : " + data.get(position).getMain().getHumidity() + "\n\n" +
                "Max temperature : " + data.get(position).getMain().getTemp_max() + "\n\n" +
                "Min temperature MIN : " + data.get(position).getMain().getTemp_min() + "\n\n" +
                "Pressure : " + data.get(position).getMain().getPressure() + "\n\n" +
                "Location:" + data.get(position).getCoord().getLat() + "," + data.get(position).getCoord().getLon() + "\n\n" +
                "Wind : Degree : " + data.get(position).getWind().getDeg() + " Speed : " + data.get(position).getWind().getSpeed() + "\n\n" +
                "Visibility :" + data.get(position).getVisibility());


        return convertView;
    }

    public void swapData(Collection<RealmObject> data) {

        Collection<OpenWeather> collection = new ArrayList<>();
        Realm realm = RealmManager.getRealm();

        if (data.containsAll(Collections.emptyList())) {

            collection.addAll(realm.where(OpenWeather.class).findAll());
        } else {
            for (Object aData : data) {
                OpenWeather weather = (OpenWeather) aData;
                collection.add(weather);
            }
        }
        this.data.clear();
        this.data.addAll(collection);
        notifyDataSetChanged();

    }


}
