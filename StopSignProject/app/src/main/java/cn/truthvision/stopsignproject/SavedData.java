package cn.truthvision.stopsignproject;
;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SavedData extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSettings(v);
            }
        });

        Button vid = (Button) findViewById(R.id.savevids);
        vid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSavedVid(v);
            }
        });

        Button data = (Button) findViewById(R.id.datapage);
        data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openDataBrowser(v);
            }
        });


    }

    private void openDataBrowser(View v) {
        Intent i = new Intent(this, DataBrowser.class);
        startActivity(i);
    }


    private void openSavedVid(View v) {
        Intent i = new Intent(this, SavedVideo.class);
        startActivity(i);
    }

    private void openSettings(View v) {
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }

}
