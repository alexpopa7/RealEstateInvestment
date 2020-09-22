package com.proiect.licentam.sorters;

import com.proiect.licentam.model.ZoneValuePoint;

import java.util.Comparator;

public class SortZoneByValuePoints implements Comparator<ZoneValuePoint> {
    @Override
    public int compare(ZoneValuePoint o1, ZoneValuePoint o2) {
        return o2.getNrOfTotalValuePoints() - o1.getNrOfTotalValuePoints();
    }
}
