package com.bkset.vutuan.stour.model;

import java.util.ArrayList;

/**
 * Created by vutuan on 15/04/2018.
 */

public class LocationCategory {
    private  int LocationCategoryId;
    private int Id;
    private String Name;
    private String Address;
    private String ShortDes;
    private String LongDes;
    private double Longitude;
    private double Latitude;
    private String Avatar;
    private  int Star;
    private String Hotline;
    private int ViewCount;
    private int Price;
    private ArrayList<String> FileAttachs;

    public LocationCategory(int id, String name, String address, String longDes, double longitude, double latitude, String avatar, int star, String hotline, int viewCount, int price, ArrayList<String> fileAttachs) {
        Id = id;
        Name = name;
        Address = address;
        LongDes = longDes;
        Longitude = longitude;
        Latitude = latitude;
        Avatar = avatar;
        Star = star;
        Hotline = hotline;
        ViewCount = viewCount;
        Price = price;
        FileAttachs = fileAttachs;
    }

    public LocationCategory(int id, String name, String address, String shortDes, String longDes, double longitude, double latitude, String avatar, int star, int viewCount) {
        Id = id;
        Name = name;
        Address = address;
        ShortDes = shortDes;
        LongDes = longDes;
        Longitude = longitude;
        Latitude = latitude;
        Avatar = avatar;
        Star = star;
        ViewCount = viewCount;
    }

    public LocationCategory(int id, String name, String address, String shortDes, String longDes, double longitude, double latitude, String avatar, int star, int viewCount, int price) {
        Id = id;
        Name = name;
        Address = address;
        ShortDes = shortDes;
        LongDes = longDes;
        Longitude = longitude;
        Latitude = latitude;
        Avatar = avatar;
        Star = star;
        ViewCount = viewCount;
        Price = price;
    }

    public LocationCategory(int locationCategoryId, int id, String name, String address, String shortDes, String longDes, double longitude, double latitude, String avatar) {
        LocationCategoryId = locationCategoryId;
        Id = id;
        Name = name;
        Address = address;
        ShortDes = shortDes;
        LongDes = longDes;
        Longitude = longitude;
        Latitude = latitude;
        Avatar = avatar;
    }

    public int getViewCount() {
        return ViewCount;
    }

    public LocationCategory(int locationCategoryId, int id, String name, String address, String shortDes, String longDes, double longitude, double latitude, String avatar, int star, int viewCount) {
        LocationCategoryId = locationCategoryId;
        Id = id;
        Name = name;
        Address = address;
        ShortDes = shortDes;
        LongDes = longDes;
        Longitude = longitude;
        Latitude = latitude;
        Avatar = avatar;
        Star = star;
        ViewCount = viewCount;
    }

    public ArrayList<String> getFileAttachs() {
        return FileAttachs;
    }

    public void setFileAttachs(ArrayList<String> fileAttachs) {
        FileAttachs = fileAttachs;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setViewCount(int viewCount) {
        ViewCount = viewCount;
    }

    public LocationCategory(int locationCategoryId, int id, String name, String address, String shortDes, String longDes, double longitude, double latitude, String avatar, int star, String hotline, int viewCount) {

        LocationCategoryId = locationCategoryId;
        Id = id;
        Name = name;
        Address = address;
        ShortDes = shortDes;
        LongDes = longDes;
        Longitude = longitude;
        Latitude = latitude;
        Avatar = avatar;
        Star = star;
        Hotline = hotline;
        ViewCount = viewCount;
    }

    public LocationCategory(int locationCategoryId, int id, String name, String address, String shortDes, String longDes, double longitude, double latitude, String avatar, int star, String hotline) {
        LocationCategoryId = locationCategoryId;
        Id = id;
        Name = name;
        Address = address;
        ShortDes = shortDes;
        LongDes = longDes;
        Longitude = longitude;
        Latitude = latitude;
        Avatar = avatar;
        Star = star;
        Hotline = hotline;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public String getHotline() {
        return Hotline;
    }

    public void setHotline(String hotline) {
        Hotline = hotline;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLocationCategoryId() {
        return LocationCategoryId;
    }

    public void setLocationCategoryId(int locationCategoryId) {
        LocationCategoryId = locationCategoryId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getShortDes() {
        return ShortDes;
    }

    public void setShortDes(String shortDes) {
        ShortDes = shortDes;
    }

    public String getLongDes() {
        return LongDes;
    }

    public void setLongDes(String longDes) {
        LongDes = longDes;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
