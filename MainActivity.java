package cn.truthvision.stopsignproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup recording;
    private RadioButton recordAll;
    private RadioButton recordViolations;
    private RadioButton recordNonStopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recording = (RadioGroup) findViewById(R.id.group1);
        recordAll = (RadioButton) findViewById(R.id.recordall);
        recordViolations = (RadioButton) findViewById(R.id.recordviolations);
        recordNonStopping = (RadioButton) findViewById(R.id.recordnonstopping);

        //Menu menu = (Menu) findViewById()
        recordAll.setOnClickListener(this);
        recordViolations.setOnClickListener(this);
        recordNonStopping.setOnClickListener(this);


    }
    @Override
    public void onClick(View v){
        /*int checkedRadioButtonId = recording.getCheckedRadioButtonId();

        switch(checkedRadioButtonId){
            case R.id.recordall:
                if(recordAll.isChecked()){
                    Toast toast = Toast.makeText(this, "Record All Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.recordviolations:
                if(recordViolations.isChecked()){
                    Toast toast = Toast.makeText(this, "Record Violations Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }*/
    }



}
