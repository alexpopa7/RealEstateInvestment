package com.proiect.licentam.sorters;

import com.proiect.licentam.model.Home;

import java.util.Comparator;

public class SortByValuePoints implements Comparator<Home> {


    @Override
    public int compare(Home o1, Home o2) {
        return o2.getValuePoints() - o1.getValuePoints();
    }
}
