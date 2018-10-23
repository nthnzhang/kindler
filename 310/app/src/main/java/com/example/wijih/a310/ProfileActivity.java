package com.example.wijih.a310;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    String username, email, phone, rating;
    List<String> itemList, booksList;
    ArrayAdapter<String> adapter;
    TextView usernameView, emailView, phoneView, ratingView, booksView;
    List<String> allBooks = new ArrayList<String>();
    String booksDisplay = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        username = "zharju";
        email = "zharju@usc.edu";
        phone = "7144211666";
        rating = "4.3";
        allBooks.add("harry potter");
        allBooks.add("hunger games");
        allBooks.add("hi");

        /*username = user.getUsername();
        email = user.getEmail();
        phone = user.getPhone();
        rating = user.getRating();
        allBooks = user.getBooks();*/


        usernameView = (TextView) findViewById(R.id.username);
        usernameView.setText(username);
        emailView = (TextView) findViewById(R.id.email);
        emailView.setText(email);
        phoneView = (TextView) findViewById(R.id.phone);
        phoneView.setText(phone);
        ratingView = (TextView) findViewById(R.id.rating);
        ratingView.setText(rating);

    }






    public void editProfile (View view) {
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    public void addBook (View view) {
        startActivity(new Intent(this, AddBook.class));
    }

    public void yourBooks (View view) {
        startActivity(new Intent(this, BooksActivity.class));
    }

    //Logout function done
    /*public void logoutUser(View view) {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class));
        finish();
        return;
    }*/
}
