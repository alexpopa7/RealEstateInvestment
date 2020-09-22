package com.proiect.licentam.model;

import javax.persistence.*;


@Entity
@Table(name = "home")
public class HomeDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "typeid")
    private Type type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "constructedarea")
    private Integer constructedArea;

    @Column(name = "buildyear")
    private Integer buildYear;

    @ManyToOne
    @JoinColumn(name = "statusid")
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    //@ManyToOne
    @JoinColumn(name = "addressid")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "zoneid")
    private Zone zone;

    @Column(name = "valuepoints")
    private Integer valuePoints;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
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
}
