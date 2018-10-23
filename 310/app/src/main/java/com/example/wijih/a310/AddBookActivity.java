package com.example.wijih.a310;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddBookActivity extends AppCompatActivity {

    private EditText bookTitle, bookDescription, bookTags;
    private boolean checked, forSale = false;
    private Button add;
    private RadioButton radioChoice;
    private RadioGroup exchangeOrSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        addBook();
    }


    public void addBook () {
        bookTitle = (EditText) findViewById(R.id.bookTitle);
        bookDescription = (EditText) findViewById(R.id.bookDescription);
        bookTags = (EditText) findViewById(R.id.bookTags);
        add = (Button) findViewById(R.id.add);
        exchangeOrSale = (RadioGroup) findViewById(R.id.exchangeOrSale);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && !bookTitle.getText().toString().trim().equals("") && !bookDescription.getText().toString().trim().equals("") && !bookTags.getText().toString().trim().equals("")) {

                    //split the tags by whitespace
                    //String[] tags = bookTags.split("\\s+");

                    int selected = exchangeOrSale.getCheckedRadioButtonId();

                    radioChoice = (RadioButton) findViewById(selected);
                    if (radioChoice.getText() == "Sale") {
                        forSale = true;
                    }
                    else {
                        forSale = false;
                    }
                    user.addBook(new Book(bookTitle, bookDescription, user.getUserId(), forSale, bookTags));
                    //get user id to attach when adding book
                    //append the new book to the users books
                }
            }
        });

        startActivity(new Intent(AddBook.this, ProfileActivity.class));
    }
}
