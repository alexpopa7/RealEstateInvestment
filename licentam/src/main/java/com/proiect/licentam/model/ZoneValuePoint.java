package com.proiect.licentam.model;

public class ZoneValuePoint {

    private String zoneName;
    private Integer nrOfTotalValuePoints;

    public ZoneValuePoint(String zoneName, Integer nrOfTotalValuePoints) {
        this.zoneName = zoneName;
        this.nrOfTotalValuePoints = nrOfTotalValuePoints;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getNrOfTotalValuePoints() {
        return nrOfTotalValuePoints;
    }

    public void setNrOfTotalValuePoints(Integer nrOfTotalValuePoints) {
        this.nrOfTotalValuePoints = nrOfTotalValuePoints;
    }
}
