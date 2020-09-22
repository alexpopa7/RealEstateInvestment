package com.proiect.licentam.model;


import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;


public class Home {


    private Integer id;

    private String type;

    private Integer price;

    private Integer constructedArea;

    private Integer buildYear;

    private String zoneType;

    private String address;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String status;

    private Integer valuePoints;

    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getConstructedArea() {
        return constructedArea;
    }

    public void setConstructedArea(Integer constructedArea) {
        this.constructedArea = constructedArea;
    }

    public Integer getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Integer buildYear) {
        this.buildYear = buildYear;
    }

    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getValuePoints() {
        return valuePoints;
    }

    public void setValuePoints(Integer valuePoints) {
        this.valuePoints = valuePoints;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;
        return Objects.equals(id, home.id) &&
                Objects.equals(type, home.type) &&
                Objects.equals(price, home.price) &&
                Objects.equals(constructedArea, home.constructedArea) &&
                Objects.equals(buildYear, home.buildYear) &&
                Objects.equals(zoneType, home.zoneType) &&
                Objects.equals(address, home.address) &&
                Objects.equals(status, home.status) &&
                Objects.equals(valuePoints, home.valuePoints) &&
                Objects.equals(user, home.user);
    }

}
