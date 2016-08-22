package cn.truthvision.stopsignproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Testing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        Button b = (Button) findViewById(R.id.testb);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                test(v);
            }
        });
    }

    private void test(View v) {

    }
}
