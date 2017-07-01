package com.androidtutorialpoint.mynavigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.androidtutorialpoint.mynavigationdrawer.common.AppContanst;
import com.libre.mylibs.MyUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 6/20/2017.
 */

public class MyArrayAdapter extends BaseAdapter implements Filterable {
    private List<ContactsModel> listData;
    private LayoutInflater layoutInflater;
    private List<ContactsModel> listFiltered = new ArrayList<ContactsModel>();
    private Context context;

    public MyArrayAdapter(Context acontext, List<ContactsModel> listData) {
        this.context = acontext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(acontext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.friend_name);
            holder.tv_phoneNumber = (TextView) convertView.findViewById(R.id.friend_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactsModel contactsModel = this.listData.get(position);
        holder.tv_name.setText(contactsModel.getName());
        holder.tv_phoneNumber.setText(contactsModel.getNumberPhone());
        return convertView;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ContactsModel> resultsData = new ArrayList<ContactsModel>();
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                        //no constraint given, just return all the data. (no search)
                        results.count = listData.size();
                        results.values = listData;
                }
                else {//do the search
                    String searchStr = constraint.toString().toUpperCase();
                    for (int i = 0; i < listData.size(); i++) {
                        if (listData.get(i).getName().toUpperCase().contains(searchStr)) {
                            resultsData.add(listData.get(i));
                            results.count = resultsData.size();

                        }
                    }
                    results.values = resultsData;

                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//                    listData = (ArrayList<ContactsModel>) results.values;
//                    notifyDataSetChanged();
                if (results != null && results.count > 0) {
                    listData = (ArrayList<ContactsModel>) results.values;
                    // update the filtered data
                    notifyDataSetChanged();
                    getFilter();
                } else {
                    notifyDataSetInvalidated();
                    Log.d("KET QUA", "vôninofnosdngo");

                }
                }



        };
    }
}
