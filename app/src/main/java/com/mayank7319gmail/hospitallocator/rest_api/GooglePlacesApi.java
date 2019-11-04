package com.mayank7319gmail.hospitallocator.rest_api;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.maps.model.LatLng;
import com.mayank7319gmail.hospitallocator.R;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mayank Gupta on 11-12-2017.
 */

public class GooglePlacesApi {

    public static String WEB_KEY;
    public final static int SEARCH_RADIUS = 5000;

    public static final int TYPE_RESTAURANT = 0;
    public static final int TYPE_CAFE = 1;
    public static final int TYPE_ELECTRONICS = 2;

    public static final int RANKBY_PROMINENCE = 0;
    public static final int RANKBY_DISTANCE = 1;

    Context ctx;

    public GooglePlacesApi(Context ctx) {
        this.ctx = ctx;
        WEB_KEY = ctx.getString(R.string.google_maps_key);
    }

    public HospitalListClient getHospitalListClient(){
        String BASE_URL = "https://maps.googleapis.com/maps/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(HospitalListClient.class);
    }

    /*public String getTypeString(int type){
        switch (type){
            case TYPE_HOSPITAL:
                return "Hospital";
            case TYPE_DOCTOR:
                return "Doctor/Clinic";
            case TYPE_DENTIST:
                return "Dentist";
        }
        return "";
    }*/

    public int getType(String s){
        switch (s){
            case "Restaurant":
                return TYPE_RESTAURANT;
            case "Cafe":
                return TYPE_CAFE;
            case "Electronics":
                return TYPE_ELECTRONICS;
        }

        return TYPE_RESTAURANT;
    }

    public int getRank(String s){
        switch (s){
            case "Prominence":
                return RANKBY_PROMINENCE;
            case "Distance":
                return RANKBY_DISTANCE;
        }

        return RANKBY_PROMINENCE;
    }

   /* public String getRankString(int rank){
        switch (rank){
            case RANKBY_PROMINENCE:
                return "Prominence";
            case RANKBY_DISTANCE:
                return "Distance";
        }
        return "";
    }*/

    public HashMap<String,String> getQueryParams(LatLng loc, int type, int rankby){
        HashMap<String,String> params = new HashMap<>();
        params.put("key",WEB_KEY);

        String latlng = loc.latitude+","+loc.longitude;
        params.put("location",latlng);

        switch (type){
            case TYPE_RESTAURANT:
                params.put("type","restaurant");
                break;
            case TYPE_CAFE:
                params.put("type","cafe");
                break;
            case TYPE_ELECTRONICS:
                params.put("type","electronics_store");
                break;
        }

        switch (rankby){
            case RANKBY_DISTANCE:
                params.put("rankby","distance");
                break;
            case RANKBY_PROMINENCE:
                params.put("radius",String.valueOf(SEARCH_RADIUS));
                break;
        }

        return params;
    }
}
