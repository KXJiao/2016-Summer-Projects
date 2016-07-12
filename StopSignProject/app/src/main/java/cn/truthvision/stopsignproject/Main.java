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
        Button data = (Button) findViewById(R.id.databutton);
        data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openData(view);
            }
        });
    }

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
