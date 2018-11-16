package com.example.wijih.a310;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wijih.a310.model.Book;
import com.example.wijih.a310.model.User;
import java.util.Arrays;

import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private User currentUser;
    private EditText bookTitle, bookDescription, bookTags;
    private boolean checked, forSale = false;
    private Button add;
    private RadioButton radioChoice;
    private RadioGroup exchangeOrSale;
    private String tagString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");

        addBook();
    }


    public void addBook () {
        bookTitle = (EditText) findViewById(R.id.bookTitle);
        bookDescription = (EditText) findViewById(R.id.bookDescription);
        add = (Button) findViewById(R.id.addBook);
        exchangeOrSale = (RadioGroup) findViewById(R.id.exchangeOrSale);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTags = (EditText) findViewById(R.id.bookTags);
                tagString = (String) bookTags.getText().toString();
                Log.d("string test", tagString);

                if (currentUser != null && !bookTitle.getText().toString().trim().equals("") && !bookDescription.getText().toString().trim().equals("") && !bookTags.getText().toString().trim().equals("")) {

                    //split the tags by commas

                    List<String> tags = Arrays.asList(tagString.split(","));
                    Log.d("tags", tags.get(0));

                    int selected = exchangeOrSale.getCheckedRadioButtonId();

                    radioChoice = (RadioButton) findViewById(selected);
                    if (radioChoice.getText() == "Sale") {
                        forSale = true;
                    }
                    else {
                        forSale = false;
                    }
                    // add book
                    Book newBook = new Book(bookTitle.getText().toString(), bookDescription.getText().toString(),
                            currentUser.getUserID(), forSale, tags);
                    currentUser.addBook(newBook);

                    Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                    intent.putExtra("current_user", currentUser);
                    startActivity(intent);
                }
            }
        });

//        startActivity(new Intent(AddBookActivity.this, ProfileActivity.class));

    }
}
