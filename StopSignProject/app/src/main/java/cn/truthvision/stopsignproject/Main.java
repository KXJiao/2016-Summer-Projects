package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Main extends AppCompatActivity {

    RadioButton a1;
    RadioButton a2;
    RadioButton a3;
    RadioButton b1;
    RadioButton b2;

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


        //Convert rad button to id, somehow radgroup has no id
        a1 = (RadioButton) findViewById(R.id.radioButton);
        a2 = (RadioButton) findViewById(R.id.radioButton2);
        a3 = (RadioButton) findViewById(R.id.radioButton3);


        b1 = (RadioButton) findViewById(R.id.adddatabase);
        b2 = (RadioButton) findViewById(R.id.realtime);



    }
    
    /*public void openHome(View v){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        //finish();
    }*/

    public void openVideo(View v){
        Intent intent = new Intent(this, Video.class);

        int RecOptions = 1;
        int SaveOptions = 1;

        if(a1.isChecked())
            RecOptions = 1;
        else if(a2.isChecked())
            RecOptions = 2;
        else
            RecOptions = 3;

        if(b1.isChecked())
            SaveOptions = 1;
        else
            SaveOptions = 2;


        intent.putExtra("Record", RecOptions);
        intent.putExtra("Save", SaveOptions);
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
