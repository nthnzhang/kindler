package com.example.wijih.a310;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class BookInfoWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_info_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.6));





    }

}
