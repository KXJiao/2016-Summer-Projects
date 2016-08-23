package cn.truthvision.stopsignproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.support.v7.app.ActionBar.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.truthvision.stopsignlib.DBHandlerVideo;
import cn.truthvision.stopsignlib.DBHandlerViolation;
import cn.truthvision.stopsignlib.VideoInfo;
import cn.truthvision.stopsignlib.Violation;

public class DataBrowser extends Activity implements OnMapReadyCallback {

    TabHost tabHost;
    //ArrayList<String> list = new ArrayList<String>();

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

        //Retrieves Video data as stored on database

        String query = "SELECT * FROM videos";
        DBHandlerVideo dbh = new DBHandlerVideo(this,null,null,1);
        ArrayList<VideoInfo> vidarr = new ArrayList<>();
        if(dbh.count()>0) {
            SQLiteDatabase sql = dbh.getWritableDatabase();
            Cursor cursor = sql.rawQuery(query, null);
            cursor.moveToFirst();
            try {
                while (cursor.moveToNext()) {
                    vidarr.add(new VideoInfo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4))));
                }
            } finally {
                cursor.close();
            }
        }

        String vquery = "SELECT * FROM violations";
        DBHandlerViolation dbv = new DBHandlerViolation(this,null,null,1);
        if(dbv.count()>0){
            SQLiteDatabase sql = dbv.getWritableDatabase();
            Cursor cursor = sql.rawQuery(vquery, null);
            cursor.moveToFirst();
            try {
                while(cursor.moveToNext()) {
                    //@TODO: traverse violation database and adds Violations to videos in the list
                    int found = -1;
                    ArrayList<Violation> temp = new ArrayList<>();
                    for(int x = 0; x<vidarr.size(); x++){
                        if(vidarr.get(x).getFileName().equals(cursor.getString(1))){
                            temp = vidarr.get(x).getViolations();
                            temp.add(new Violation(new Time(Long.parseLong(cursor.getString(3))),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6)),Integer.parseInt(cursor.getString(7))));
                            found = x;
                            break;
                        }
                    }
                    if(found>=0) {
                        VideoInfo set = vidarr.get(found);
                        set.setViolations(temp);
                        vidarr.set(found, set);
                    }

                }
            } finally {
                cursor.close();
            }
        }


        //adds the videos to the linear layout

        LinearLayout ll = (LinearLayout) findViewById(R.id.linlay);
        for(int x = 0; x<vidarr.size(); x++){
            TextView txt = new TextView(this);
            txt.setText(vidarr.get(x).toString());
            ll.addView(txt);
        }


/*      @TODO: converting this code to work for the info pulled from the Videos and Violations database
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "StopSignVidStore");
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout23);
        LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);




        File[] temp = mediaStorageDir.listFiles();
        if(mediaStorageDir.listFiles()!= null && mediaStorageDir.listFiles().length>0) {
            for (int x = 0; x < mediaStorageDir.listFiles().length; x++) {
                String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss").format(new Date());
                Button timeButton = new Button(this);
                timeButton.setText("" + timeStamp);
                Button myButton = new Button(this);
                myButton.setText(temp[x].getName() + "");
                final String path = temp[x].getAbsolutePath() + "";
                myButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        //
                    }
                });
                ll.addView(myButton, lp);
            }
        }


*/
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


        DBHandlerVideo dbh = new DBHandlerVideo(this,null,null,1);

        if(dbh.count()>0) {
            for (int x = 1; x <= dbh.count(); x++) {
                VideoInfo vid = dbh.findID(x);
                map.addMarker(new MarkerOptions().position(new LatLng(vid.getLat(), vid.getLng())).title(vid.getFileName() + ", " + vid.getLatLng()));
            }
        }


        //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    private void dataHandler(){

    }

}


