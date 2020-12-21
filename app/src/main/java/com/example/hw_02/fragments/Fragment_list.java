package com.example.hw_02.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.hw_02.CallBack_map;
import com.example.hw_02.R;
import com.example.hw_02.classes.Record;
import com.example.hw_02.classes.TopTen;
import com.example.hw_02.utils.MySP;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Fragment_list extends Fragment {

    private ListView listView;
    private HashMap<String, Record> records;
    private CallBack_map callBack_map;

    public void setCallBack_map(CallBack_map callBack_map) {
        this.callBack_map = callBack_map;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("hw_02 app list", "On createView-fragment list");

        View view = inflater.inflate(R.layout.fragment_lay_list, container, false);
        findViews(view);

        init();
        initViews();

        return view;
    }

    private void init() {
        records = new HashMap<String, Record>();
        ArrayList<String> strings = new ArrayList<>();
        String gets = MySP.getInstance().getString("top10", "");
        if (!gets.equals("")) {
            TopTen topTenList = new Gson().fromJson(gets, TopTen.class);
            for (Record r : topTenList.getTop10List()) {
                gets = r.getScore() + " " + r.getName() + " \n" + r.getDate();
                records.put(gets, r);
                strings.add(gets);
            }
        }
        //Collections.sort(strings);
        Collections.reverse(strings);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                android.R.id.text1, strings);
        listView.setAdapter(arrayAdapter);
    }

    private void initViews() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = (String) listView.getItemAtPosition(position);
                Record r = records.get(temp);
                if (r != null) {
                    callBack_map.getLocation(r.getLng(), r.getLat(), r.getName() + " " + r.getScore());
                }
            }
        });
    }

    private void findViews(View view) {
        listView = view.findViewById(R.id.f_l_LST_list);
    }
}
