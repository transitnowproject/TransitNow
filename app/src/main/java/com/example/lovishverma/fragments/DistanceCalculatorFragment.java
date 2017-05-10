package com.example.lovishverma.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.lovishverma.HttpRequestProcessor.Response;
import com.example.lovishverma.transitnow.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 2017-05-08.
 */

public class DistanceCalculatorFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,View.OnClickListener,GoogleMap.OnMarkerDragListener,GoogleMap.OnMapLongClickListener{



    private GoogleMap mMap;
    public Marker m1,m2,mLast;

    protected ArrayList<Marker> markers;

    protected Toast distanceToast;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;

    //From -> the first coordinate from where we need to calculate the distance
    private double fromLongitude;
    private double fromLatitude;

    //To -> the second coordinate to where we need to calculate the distance
    private double toLongitude;
    private double toLatitude;

    //Buttons
    private ImageButton buttonSave;
    private ImageButton buttonCurrent;
    private ImageButton buttonView;

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    //Our buttons
    private Button btnTo,btnFrom,btnCalculateDistance;
    private FragmentManager supportFragmentManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_distance_calculator, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markers = new ArrayList<Marker>();

        //Initializing googleapi client
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        googleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .addApi(AppIndex.API)
//                .build();



        btnTo = (Button) v.findViewById(R.id.btnTo);
        btnFrom = (Button) v.findViewById(R.id.btnFrom);
        btnCalculateDistance = (Button) v.findViewById(R.id.btnCalculateDistance);

        btnTo.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnCalculateDistance.setOnClickListener(this);

        return v;
    }

//    @Override
//    protected void onStart() {
//        googleApiClient.connect();
//        super.onStart();
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, //TODO: choose an action type.
//                "Maps Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.googlemap.googlemapdistance/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(googleApiClient, viewAction);
//    }
//
//    @Override
//    protected void onStop() {
//        googleApiClient.disconnect();
//        super.onStop();
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, //TODO: choose an action type.
//                "Maps Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.googlemap.googlemapdistance/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(googleApiClient, viewAction);
//    }

    //Getting current location
    private void getcurrentLocation() {
        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null){
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //Moving the map to location
            moveMap();
        }

    }
    //Function to move the map
    private void moveMap() {
        //Creating a Latlng Object to store Corrdinates
        LatLng latlng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latlng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin="); //from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=SERVER-KEY");
        return urlString.toString();
    }
//    private void getDirection(){
//        //Getting the URL
//        String url = makeURL(fromLatitude, fromLongitude, toLatitude, toLongitude);
//
//        //Showing a dialog till we get the route
//        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Getting Route", "Please wait", false, false);
//
//        //Creating a string request
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//                        loading.dismiss();
//                        //Calling the method drawPath to draw the path
//                        drawPath(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loading.dismiss();
//                    }
//                });
//
//        //Adding the request to request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

    //The parameter is the server response
//    public void drawPath(String result) {
//        //Getting both the coordinates
//        LatLng from = new LatLng(fromLatitude, fromLongitude);
//        LatLng to = new LatLng(toLatitude, toLongitude);
//
//        //Calculating the distance in meters
//        Double distance = SphericalUtil.computeDistanceBetween(from, to);
//
//        //Displaying the distance
//        Toast.makeText(getActivity(), String.valueOf(distance+"Meters"), Toast.LENGTH_SHORT).show();
//
//        try{
//            //Parsing json
//            final JSONObject json = new JSONObject(result);
//            JSONArray routeArray = json.getJSONArray("routes");
//            JSONObject routes = routeArray.getJSONObject(0);
//            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
//            String encodedString = overviewPolylines.getString("points");
//            List<LatLng> list = decodePoly(encodedString);
//            Polyline line = mMap.addPolyline(new PolylineOptions()
//                    .addAll(list)
//                    .width(20)
//                    .color(Color.RED)
//                    .geodesic(true)
//            );
//        }
//        catch (JSONException e){
//
//        }
//    }

//    private List<LatLng> decodePoly(String encodedString) {
//        return ;
//    }
private List<LatLng> decodePoly(String encoded) {
    List<LatLng> poly = new ArrayList<LatLng>();
    int index = 0, len = encoded.length();
    int lat = 0, lng = 0;

    while (index < len){
        int b, shift = 0, result = 0;
        do{
            b = encoded.charAt(index++) - 63;
            result |= (b & 0x1f) << shift;
            shift += 5;
        } while (b >= 0x20);
        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >>1));
        lat += dlat;

        shift = 0;
        result = 0;
        do {
            b = encoded.charAt(index++) - 63;
            result |= (b & 0x1f) << shift;
            shift += 5;
        } while (b >= 0x20);
        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
        lng += dlng;

        LatLng p = new LatLng( (((double) lat / 1E5)),
                (((double) lng / 1E5)));
        poly.add(p);

    }
    return poly;
}

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getcurrentLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latlng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latlng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
//



    }

    @Override
    public void onClick(View v) {
        if(v == btnFrom){
            fromLatitude = latitude;
            fromLongitude = longitude;
            Toast.makeText(getActivity(), "From set", Toast.LENGTH_SHORT).show();
        }

        if(v == btnTo) {
            toLatitude = latitude;
            toLongitude = longitude;
            Toast.makeText(getActivity(), "To set", Toast.LENGTH_SHORT).show();
        }

        if(v == btnCalculateDistance){
            //This method will show the distance and will also draw the path
          //  getDirection();
        }


    }


    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        //Clearing all the markers
        mMap.clear();
        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));

        latitude = latLng.latitude;
        longitude = latLng.longitude;

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();

    }
}
