package cn.truthvision.stopsignlib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by TV_Laptop_01 on 7/12/2016.
 */
public class TestView extends LinearLayout {
    public TestView(Context context) {
        super(context);
        initialize(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context){
        inflate(context, R.layout.test_view, this);
    }

}
