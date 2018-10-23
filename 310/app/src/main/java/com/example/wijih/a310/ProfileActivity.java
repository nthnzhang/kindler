package com.example.wijih.a310;

import android.content.Intent;
import android.icu.util.Freezable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wijih.a310.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    String username, email, phone, rating;
    List<String> itemList, booksList;
    ArrayAdapter<String> adapter;
    TextView usernameView, emailView, phoneView, ratingView, booksView;
    List<String> allBooks = new ArrayList<String>();
    String booksDisplay = "";

    private User currentUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");


        /*username = user.getUsername();
        email = user.getEmail();
        phone = user.getPhone();
        rating = user.getRating();
        allBooks = user.getBooks();*/


        usernameView = (TextView) findViewById(R.id.username);
        usernameView.setText(currentUser.getUsername());
        emailView = (TextView) findViewById(R.id.email);
        emailView.setText(currentUser.getEmail());
        phoneView = (TextView) findViewById(R.id.phone);
        phoneView.setText(currentUser.getPhone());
        ratingView = (TextView) findViewById(R.id.rating);
        ratingView.setText(String.valueOf(currentUser.getTotalScore()/currentUser.getTotalReviews()));

//        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserID()).child("uploadedBookIDs");


    }






    public void editProfile (View view) {
//        startActivity(new Intent(this, EditProfileActivity.class));

        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("current_user", currentUser);
        startActivity(intent);
    }

    public void addBook (View view) {
//        startActivity(new Intent(this, AddBookActivity.class));

        Intent intent = new Intent(this, AddBookActivity.class);
        intent.putExtra("current_user", currentUser);
        startActivity(intent);
    }

    public void yourBooks (View view) {
//        startActivity(new Intent(this, BooksActivity.class));

        Intent intent = new Intent(this, BooksActivity.class);
        intent.putExtra("current_user", currentUser);
        startActivity(intent);
    }

    //Logout function done
    /*public void logoutUser(View view) {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class));
        finish();
        return;
    }*/
}
