
package com.example.lovishverma.transitnow;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.lovishverma.APIConfiguration.ApiConfiguration;
import com.example.lovishverma.HttpRequestProcessor.HttpRequestProcessor;
import com.example.lovishverma.HttpRequestProcessor.Response;
import com.example.lovishverma.fragments.BusLocFragment;
import com.example.lovishverma.fragments.ListedMembersFragment;
import com.example.lovishverma.fragments.NearestStopFragment;
import com.example.lovishverma.fragments.SetReminderFragment;
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

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    SupportMapFragment sMapFragment, mapFragment;
   private Button btnSaveLoc;
    private GoogleApiClient googleApiClient;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private int responseData;
    private boolean success;
    private String MemberId, Title, Description, DDate, Longitude, Latitude, Place;
    private String message;
    private double latitude, longitude;
    private LatLng latLng;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMapFragment = SupportMapFragment.newInstance();
        setContentView(R.layout.activity_dashboard);

        btnSaveLoc = (Button) findViewById(R.id.btnSaveLoc);
//        btnShowLoc = (Button) findViewById(R.id.btnShowLoc);
//        btnSignOut = (Button) findViewById(R.id.btnSignOut);


        // Obtain the SupportMapFragment and get notified when the map
        //is ready to be used.
//        sMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
//       sMapFragment.getMapAsync(this);
        //sMapFragment.getMapAsync((OnMapReadyCallback) this);

        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        sMapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "TrackMyPosition/SaveMyCurrentPosition";
        Log.e("url", urlRegister);

        btnSaveLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
                new SaveTask().execute(MemberId, Title, Description, DDate, Longitude, Latitude, Place);

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "CURRENT LOCATION", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                getCurrentLocation();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction().replace(R.id.content_frame, new MapFragment()).commit();


    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    public void getCurrentLocation()
    {
        mMap.clear();

        //Creating a location object
        if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashboardActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           // return;
        }
        Location location =
                LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        //Moving the map to location
        moveMap();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Add locations and markers here
        mMap = googleMap;

        // Add a marker in Chandigarh and move the camera
        LatLng chd = new LatLng(30, 76);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new
                        MarkerOptions().position(latLng).title("hello"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return true;
            }
        });
        mMap.addMarker(new MarkerOptions().position(chd).title("hello"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chd));
    }

    public void moveMap() {

        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Current Location").draggable(true));

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(30));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class SaveTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            MemberId = params[0];
            Title = params[1];
            Description = params[2];
            DDate = params[3];
            Longitude = params[4];
            Latitude = params[5];
            Place = params[6];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("MemberId", MemberId);
                jsonObject.put("Title", Title);
                jsonObject.put("Description", Description);
                jsonObject.put("DDate", DDate);
                jsonObject.put("Longitude", Longitude);
                jsonObject.put("Latitude", Latitude);
                jsonObject.put("Place", Place);


                jsonPostString = jsonObject.toString();
                Log.e("jsonPostString", jsonPostString);
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
                jsonResponseString = response.getJsonResponseString();
                Log.e("jsonResponseString", jsonResponseString);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));

                responseData = jsonObject.getInt("responseData");
                // Log.d("message", message);
                message = jsonObject.getString("message");
                Log.d("message", message);


                if (responseData == 1) {
                    Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getActivity(), FragmentOne.class);
//                    startActivity(intent);
                } else {
                    Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.my_profile) {
            Intent intent = new Intent(DashboardActivity.this,MyProfileActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.sign_out) {
            Intent intent = new Intent(DashboardActivity.this,TabActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();


        if (id == R.id.navNormalMap) {
            // fm.beginTransaction().replace(R.id.content_frame, new MapFragment()).commit();

            if (!sMapFragment.isAdded())
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFm.beginTransaction().show(sMapFragment).commit();

        } else if (id == R.id.navListedMembers) {

            fm.beginTransaction().replace(R.id.content_frame, new ListedMembersFragment()).commit();

//        } else if (id == R.id.navLoc) {
//            fm.beginTransaction().replace(R.id.content_frame, new MyLocFragment()).commit();
        }else if (id == R.id.navBusLoc) {

            fm.beginTransaction().replace(R.id.content_frame, new BusLocFragment()).commit();

        } else if (id == R.id.navBusStop) {
            fm.beginTransaction().replace(R.id.content_frame, new NearestStopFragment()).commit();
        } else if (id == R.id.navReminder) {
            fm.beginTransaction().replace(R.id.content_frame, new SetReminderFragment()).commit();
        }
        else if (id == R.id.navDistanceCalculator) {
            //fm.beginTransaction().replace(R.id.content_frame, new SetReminderFragment()).commit();
            openApp(DashboardActivity.this,"com.googlemap.googlemapdistance");
            if (!sMapFragment.isAdded())
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFm.beginTransaction().show(sMapFragment).commit();
        }else if (id == R.id.navAboutUs) {
           // fm.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
            Intent intent = new Intent(DashboardActivity.this,AboutActivity.class);
            startActivity(intent);
            if (!sMapFragment.isAdded())
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFm.beginTransaction().show(sMapFragment).commit();

        } else if (id == R.id.navRateUs) {
           // fm.beginTransaction().replace(R.id.content_frame, new RateUsFragment()).commit();
            Intent intent = new Intent(DashboardActivity.this,RateActivity.class);
            startActivity(intent);
            if (!sMapFragment.isAdded())
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFm.beginTransaction().show(sMapFragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static boolean openApp(Context context, String packageName)
    {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if(i == null){
            return false;}

        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);

        return true;
    }


}
