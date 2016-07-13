package cn.truthvision.stopsignproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

    private void openSettings(View v) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
