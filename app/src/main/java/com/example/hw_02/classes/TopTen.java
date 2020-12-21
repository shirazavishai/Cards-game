package com.example.hw_02.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopTen {
    private ArrayList<Record> top10List = new ArrayList<Record>();

    public TopTen() {
    }

    public boolean add(Record record) {
        if (top10List.size() == 10) {
            if (top10List.get(0).getScore() > record.getScore()) {
                return false;
            } else
                top10List.remove(top10List.get(0));
        }
        top10List.add(record);
        Collections.sort(top10List, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return ((Record) o1).getScore() - ((Record) o2).getScore();
            }
        });
        return true;
    }

    public ArrayList<Record> getTop10List() {
        return top10List;
    }

}
