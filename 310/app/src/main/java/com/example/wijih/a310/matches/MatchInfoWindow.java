package com.example.wijih.a310.matches;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.wijih.a310.R;
import com.example.wijih.a310.model.Book;
import com.example.wijih.a310.model.Match;

public class MatchInfoWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.matches_info_window);

        // adjusting pop up window size

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;

        getWindow().setLayout((int)(0.5*height), (int)(0.6*height));

    }
}
