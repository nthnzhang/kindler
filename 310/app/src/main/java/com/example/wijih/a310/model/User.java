package com.example.wijih.a310.model;

import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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
    private List<String> swipableBookIds;

    // used when creating a new account
    public User(String username, String email, List<String> matches, List<String> booksUploaded, Double totalScore, Double totalReviews, Map<String, List<String>> likedBooks) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        this.username = username;
        this.email = email;
        this.matchIDs = matches;
        this.uploadedBookIDs = booksUploaded;
        this.totalScore = totalScore;
        this.totalReviews = totalReviews;
        this.likedBooks = likedBooks;
        this.swipableBookIds = new ArrayList<String>();

        this.userID = mDatabase.push().getKey();

        mDatabase.child("users").child(this.userID).setValue(this);
    }

    // used when logging in
    public User() {
        // empty constructor for firebase purposes
    }

    // input: new instance of User class
    // should be called when a new account is created
    // adds the new user to the db
    // uses userId as key
    public void addNewUser(User user) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(this.userID).setValue(this);
    }

    // input: book
    // adds book id to list of uploaded book ids
    // add book to database
    public void addBook(Book book) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("books").child(bookID);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String ownerId = dataSnapshot.child("ownerID").getValue(String.class);
                Book book = dataSnapshot.getValue(Book.class);

                // add liked book to map
                if(likedBooks.containsKey(ownerId)) {
                    likedBooks.get(ownerId).add(book.getBookId());
                }
                else {
                    List<String> newList = new ArrayList<>();
                    newList.add(book.getBookId());
                    likedBooks.put(ownerId, newList);
                }

                // update list in currentUser in dB
                mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
                mDatabase.child("likedBooks").setValue(likedBooks);

                // check other book owner to see if there is a match
                mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("likedBooks");
                // test
                Map<String, List<String>> testMap = new HashMap<>();
                List<String> t = new ArrayList<>();
                t.add("asdlkfj");
                testMap.put(userID, t);
                mDatabase.setValue(testMap);

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // other book owner has liked one of currentUsers books
                        if(dataSnapshot.child(userID).exists()) {
                            // create Match and add to db
                            Match match = new Match(userID, ownerId);
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("matches");

                            String matchId = mDatabase.push().getKey();
                            match.setMatchId(matchId);
                            mDatabase.child(matchId).setValue(match);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // basic book getting functionality - doesn't account for seen / own books
    public void getBooks() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("books");
        mDatabase.addChildEventListener(new ChildEventListener() {
            // new book has been added
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // update the list and the array adapter
                String newBookId = dataSnapshot.child("bookId").getValue(String.class);
                swipableBookIds.add(newBookId);
//                Log.d("testAddBook", "size: " + String.valueOf(swipableBookIds.size()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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