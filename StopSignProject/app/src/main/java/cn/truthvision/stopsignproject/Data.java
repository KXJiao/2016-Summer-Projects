package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent i = getIntent();
        
        Button settings = (Button) findViewById(R.id.settingsbutton3);
        settings.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               openSettings(view);
           }
        });
    }
    
    public void openSettings(View view){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        //finish();
    }
}
