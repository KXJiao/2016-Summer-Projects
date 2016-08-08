package cn.truthvision.stopsignproject;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import cn.truthvision.stopsignlib.DBHandler;
import cn.truthvision.stopsignlib.VideoInfo;

public class DataBrowser extends Activity implements OnMapReadyCallback {

    TabHost tabHost;
    //ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_browser);


        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Data
        TabHost.TabSpec spec = host.newTabSpec("Data");
        spec.setContent(R.id.Data);
        spec.setIndicator("Data");
        host.addTab(spec);

        //Graphs
        spec = host.newTabSpec("Graphs");
        spec.setContent(R.id.Graphs);
        spec.setIndicator("Graphs");
        host.addTab(spec);

        //Map
        spec = host.newTabSpec("Map");
        spec.setContent(R.id.Map);
        spec.setIndicator("Map");
        host.addTab(spec);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                search(view);
            }
        });



    }

    private void search(View view) {

    }


    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(16)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }


        DBHandler dbh = new DBHandler(this,null,null,1);

        if(dbh.count()>0) {
            for (int x = 1; x <= dbh.count(); x++) {
                VideoInfo vid = dbh.findID(x);
                map.addMarker(new MarkerOptions().position(new LatLng(vid.getLat(), vid.getLng())).title(vid.getFileName() + ", " + vid.getLatLng()));
            }
        }


        //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
