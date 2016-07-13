package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button vidb = (Button) findViewById(R.id.vidbutton);
        vidb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openVideo(v);
            }
        });

        Button datab = (Button) findViewById(R.id.databutton);
        datab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openData(v);
            }
        });

        Button mapb = (Button) findViewById(R.id.cloudbutton);
        mapb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

            }
        });
        
        /*Button home = (Button) findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               openHome(view);
           }
        });*/


        Button data = (Button) findViewById(R.id.databutton);
        data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openData(view);
            }
        });
    }
    
    /*public void openHome(View v){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        //finish();
    }*/

    public void openVideo(View v){
        Intent intent = new Intent(this, Video.class);
        startActivity(intent);
    }

    public void openData(View v){
        Intent intent = new Intent(this, Data.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

    }


}
