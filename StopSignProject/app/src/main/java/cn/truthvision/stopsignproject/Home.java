package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ImageView stopsign = (ImageView) findViewById(R.id.stopsignimage);
        Intent i = getIntent();

        Button s = (Button) findViewById(R.id.button);
        s.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                openSettings(v);
            }
        });
        
        Button start = (Button) findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                start(v);
            }
        });
    }

    private void openSettings(View v) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
    
    private void start(View v){
        Intent intent = new Intent(this, Recording.class); /* think that the Video class is not necessary because if the user presses "START" it directs to the Recording page and then
        after the video is saved the app transitions to the "Data" page. For now it would be fine to keep the "Video" page but ultimately we may not need it. On basis of this, a "Video" button
        in the "Settings" page is unnecessary as well.*/

        /*Option 2: Keep the Video page but allow that page to serve the same functionality as the current data page (i.e. storing videos). The Data page can then be used for statistics, graphs, etc. */
        startActivity(intent);
    }
}
