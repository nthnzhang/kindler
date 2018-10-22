package com.example.wijih.a310.model;

import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
//    private ArrayAdapter<String> arrayAdapter;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.addChildEventListener();


    }

    // input: new instance of User class
    // adds the new user to the db
    // uses userId as key
    public void addNewUser(User user) {
        mDatabase.child("users").child(this.userID).setValue(this);

//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matchIDs);
    }

    // input: book
    // adds book id to list of uploaded book ids
    // add book to database
    public void addBook(Book book) {

        // add to entire db of books
        String bookId = mDatabase.child("books").push().getKey();
        book.setBookId(bookId);
        mDatabase.child("books").child(bookId).setValue(book);

        // add to own list of uploaded books
        uploadedBookIDs.add(bookId);
        mDatabase.child("users").child("uploadedBooksIDs").push().setValue(bookId);


    }

    // input: bookID of liked book
    // finds book in db with bookID
    // finds ownerID for found book
    // adds this ownerID and bookID to own list of liked books
    public void addLike(String bookID) {
        String ownerId = mDatabase.child("books").child(bookID);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("books").child(bookID);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);


//                likedBooks.put(ownerId, likedBooks.get(ownerId).add(bookID));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // returns map of ownerIDs to books that you like from that owner
    public Map<String, List<String>> getLikedBooks() {

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
