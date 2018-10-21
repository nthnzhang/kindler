package com.example.wijih.a310.model;

import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

public class User {
    private String username;
    private String userID;
    private String email;
    private List<String> matchIDs;
    private List<String> uploadedBookIDs;
    private Double totalScore;
    private Double totalReviews;
    private Map<String, List<String>> likedBooks;

    private DatabaseReference mDatabase;
    private ArrayAdapter<String> arrayAdapter;

    public User(String username, String email, List<String> matches, List<String> booksUploaded, Double totalScore, Double totalReviews, Map<String, List<String>> likedBooks) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        this.username = username;
        this.email = email;
        this.matchIDs = matches;
        this.uploadedBookIDs = booksUploaded;
        this.totalScore = totalScore;
        this.totalReviews = totalReviews;
        this.likedBooks = likedBooks;

        this.userID = mDatabase.push().getKey();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
//        mDatabase.addChildEventListener();

    }

    public void addNewUser(User user) {
        mDatabase.child(this.userID).setValue(this);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matchIDs);
    }


    public void addBook(Book book) {
    }

    public void addLike(Book book) {

    }

    public Map<User, List<String>> getLikedBooks() {
        return likedBooks;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getMatches() {
        return matchIDs;
    }

    public List<String> getBooksUploaded() {
        return uploadedBookIDs;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public Double getTotalReviews() {
        return totalReviews;
    }

    public String getUserID() {
        return userID;
    }
}
