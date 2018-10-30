package com.example.wijih.a310.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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

public class User implements Parcelable {
    private String username;
    private String userID;
    private String email;
    private String password;
    private String phone;
    private List<String> matchIDs;
    private List<String> uploadedBookIDs;
    private Double totalScore;
    private Double totalReviews;
    private Map<String, List<String>> likedBooks;

    private DatabaseReference mDatabase;
    private List<String> swipableBookIds;

    // used when creating a new account
    public User(String username, String email, String password, String phone, List<String> matches,
                List<String> booksUploaded, Double totalScore, Double totalReviews, Map<String, List<String>> likedBooks) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        this.username = username;
        this.email = email;
        this.matchIDs = matches;
        this.phone = phone;
        this.password = password;
        this.uploadedBookIDs = booksUploaded;
        this.totalScore = totalScore;
        this.totalReviews = totalReviews;
        this.likedBooks = likedBooks;
        this.swipableBookIds = new ArrayList<String>();

        this.userID = mDatabase.push().getKey();

        mDatabase.child(this.userID).setValue(this);
    }

    // used when logging in
    // get a snapshot of the user class from the db and store it
    public User() {
        // empty constructor for firebase purposes
    }

    protected User(Parcel in) {
        username = in.readString();
        userID = in.readString();
        email = in.readString();
        matchIDs = in.createStringArrayList();
        uploadedBookIDs = in.createStringArrayList();
        if (in.readByte() == 0) {
            totalScore = null;
        } else {
            totalScore = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalReviews = null;
        } else {
            totalReviews = in.readDouble();
        }
        swipableBookIds = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(userID);
        dest.writeString(email);
        dest.writeStringList(matchIDs);
        dest.writeStringList(uploadedBookIDs);
        if (totalScore == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalScore);
        }
        if (totalReviews == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalReviews);
        }
        dest.writeStringList(swipableBookIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
        Log.d("adding book", "test");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // add to entire db of books
        String bookId = mDatabase.child("books").push().getKey();
        book.setBookId(bookId);
        mDatabase.child("books").child(bookId).setValue(book);

        // add to own list of uploaded books
        if(uploadedBookIDs == null) {
            uploadedBookIDs = new ArrayList<>();
        }
        uploadedBookIDs.add(bookId);
        mDatabase.child("users").child(userID).child("uploadedBooksIDs").push().setValue(bookId);


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
                if(!dataSnapshot.child("ownerID").exists()) {
                    return;
                }
                final String ownerId = dataSnapshot.child("ownerID").getValue(String.class);
                Book book = dataSnapshot.getValue(Book.class);

                // add liked book to map
                if(likedBooks == null) {
                    likedBooks = new HashMap<>();
                }
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

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    // returns map of ownerIDs to books that you like from that owner
    // needed for firebase functionality
    public Map<String, List<String>> getLikedBooks() {
        return likedBooks;
    }


    // this function should only be called once (in the intialization of the matches page, perhaps)
    // will automatically update the array of matches
    public void startUpdatingMatchList() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // new match added - update list of matches
                String matchId = dataSnapshot.child("matchId").getValue(String.class);
                matchIDs.add(matchId);
//                Log.d("matches size", String.valueOf(matchIDs.size()));
            }

            // check if both users have accepted the match
            // show contact information if they have?
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.d("change", "child changed");

            }

            // match has been denied by one party
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String matchId = dataSnapshot.child("matchId").getValue(String.class);
                matchIDs.remove(matchId);
//                Log.d("matches size", String.valueOf(matchIDs.size()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // input: the matchId that the current user wants to accept
    // will find the respective match object from the db
    // and then call the acceptMatch function in the match object
    public void acceptMatch(String matchId) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Match match = dataSnapshot.getValue(Match.class);
                match.acceptMatch(userID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // input: the matchId that the current user wants to reject
    // will find the respective match object from the db
    // and then call the denyMatch function in the match object
    public void denyMatch(String matchId) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Match match = dataSnapshot.getValue(Match.class);
                match.denyMatch();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // this function will take in the matchId containing the user you are rating, and the rating
    // this will be called from the match activity
    public void rateUser(String matchId, final int rating) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            // create a match object with the match details
            // call the rate function
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Match match = dataSnapshot.getValue(Match.class);
                // pass in your own id and given rating
                match.rateUser(userID, rating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getSwipableBookIds() {return swipableBookIds;}
}