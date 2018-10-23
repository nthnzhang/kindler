package com.example.wijih.a310;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    ListView verticalBooks;
    //List<String> books = user.getBooks();
    List<String> books = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        books.add("harry potter");
        books.add("Hunger games");
        books.add("friends");
        books.add("the office");
        books.add("hello");


        verticalBooks = (ListView) findViewById(R.id.verticalBooksView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_books_view, R.id.booksDisplay, books);
        verticalBooks.setAdapter(adapter);
    }

}
