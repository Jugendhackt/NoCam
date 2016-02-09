package com.example.marius.nocam1;

/**
 * Created by Marius on 17.10.2015.
 */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Vibrator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonImport {
    List<double[]>list = new ArrayList<double[]>();
    MapsActivity ma;
    public void loadAndInsertMarkers(MapsActivity ama){
        ma=ama;
        JSONParser parser = new JSONParser();
        try
        {
            InputStream ins = ma.getResources().openRawResource(R.raw.cctvs);
            Object obj = parser.parse(new InputStreamReader(ins));
            JSONObject jsonObject = (JSONObject) obj;

            jsonObject.get("cctvs");
            JSONArray cctvlist = (JSONArray) jsonObject.get("cctvs");

            for (int i = 0;i < cctvlist.size();i++) {
                JSONArray tuple = (JSONArray) cctvlist.get(i);
                double latitude = (double) tuple.get(0);
                double longitude = (double) tuple.get(1);
                ma.setMarker(latitude, longitude);
                list.add(new double[]{latitude, longitude});

            }
            ma.setMarker(52.530617, 13.413602);
            list.add(new double[]{52.530617, 13.413602});


        } catch(Exception e) {
            System.out.println(e);
        } finally {

        } // end of try
    }
    public static float distFrom (double lat1, double lng1, double lat2, double lng2 )
    {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return new Float(dist * meterConversion).floatValue();
    }
    public void calculateAllDistances(double lat, double lon){
        for(int i = 0; i < list.size(); i = i+1){
           double distance = distFrom(lat, lon, list.get(i)[0], list.get(i)[1]);
            if (distance <= 5){
                if (ma != null) {
                    Vibrator vib = (Vibrator) ma.getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(2000);
                }

                break;
            }
        }
    }
}