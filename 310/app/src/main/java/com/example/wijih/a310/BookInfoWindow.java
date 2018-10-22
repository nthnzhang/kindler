package com.example.wijih.a310;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.wijih.a310.model.Book;

public class BookInfoWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_info_window);

        // getting current user form login activity
        Book book= getIntent().getParcelableExtra("book_info");

        String bookTitle = book.getTitle();
        TextView bookTitleView = findViewById(R.id.bookTitleView);
        bookTitleView.setText(bookTitle);


        String bookDescription = book.getDescription();
        TextView bookDescriptionView = findViewById(R.id.bookDescriptionView);
        bookDescriptionView.setText(bookDescription);



        Boolean isForSale = book.isForSale();
        TextView bookForSaleView = findViewById(R.id.bookForSaleView);
        bookForSaleView.setText(String.valueOf(isForSale));

        // adjusting size of popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

    }

}