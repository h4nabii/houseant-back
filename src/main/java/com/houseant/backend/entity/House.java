package com.houseant.backend.entity;

public class House {
    private int house_id;
    private String name;
    private String address;
    private String direction;
    private String area;
    private String layout;
    private float price;
    private String picture;
    private String developer;
    private int floor;
    private boolean has_elevator;
    private String build_time;

    public House(int house_id, String name, String address, String direction, String area, String layout, float price, String picture, String developer, int floor, boolean has_elevator, String build_time) {
        this.house_id = house_id;
        this.name = name;
        this.address = address;
        this.direction = direction;
        this.area = area;
        this.layout = layout;
        this.price = price;
        this.picture = picture;
        this.developer = developer;
        this.floor = floor;
        this.has_elevator = has_elevator;
        this.build_time = build_time;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isHas_elevator() {
        return has_elevator;
    }

    public void setHas_elevator(boolean has_elevator) {
        this.has_elevator = has_elevator;
    }

    public String getBuild_time() {
        return build_time;
    }

    public void setBuild_time(String build_time) {
        this.build_time = build_time;
    }

    @Override
    public String toString() {
        return "House{" +
                "house_id=" + house_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", direction='" + direction + '\'' +
                ", area='" + area + '\'' +
                ", layout='" + layout + '\'' +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", developer='" + developer + '\'' +
                ", floor=" + floor +
                ", has_elevator=" + has_elevator +
                ", build_time='" + build_time + '\'' +
                '}';
    }
}
