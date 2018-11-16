package com.example.wijih.a310.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Match implements Parcelable {
    private String matchId;
    private String userId1;
    private String userId2;

    // false - no choice made
    // true - accepted match
    private boolean user1Choice;
    private boolean user2Choice;
    private boolean matchAccepted;

    private DatabaseReference mDatabase;


    public Match(String userId1, String userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.user1Choice = false;
        this.user2Choice = false;
        this.matchAccepted = false;
    }

    public Match() {
        // empty constructor for firebase purposes
    }

    protected Match(Parcel in) {
        matchId = in.readString();
        userId1 = in.readString();
        userId2 = in.readString();
        user1Choice = in.readByte() != 0;
        user2Choice = in.readByte() != 0;
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public void setMatchId(String id) {
        this.matchId = id;
    }

    // input: user id of the user that is accepting the match
    public void acceptMatch(String userId) {
        // user1 is accepting the match
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
        if(userId.equals(userId1)) {
            // user2 has accepted the match already
            if(user2Choice) {
                matchAccepted = true;
                mDatabase.child("matchAccepted").setValue(true);
            }
            mDatabase.child("user1Choice").setValue(true);
        }
        // user2 is accepting the match
        else {
            // user1 has accepted the match already
            if(user1Choice) {
                // show contact info
                matchAccepted = true;
                mDatabase.child("matchAccepted").setValue(true);
            }
            mDatabase.child("user2Choice").setValue(true);
        }
    }

    // remove match from both users list of matches
    public void denyMatch() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches");
        mDatabase.child(matchId).removeValue();
        // this should automatically update the lists
    }

    // this will be called from the current User
    // input: your user ID, and the rating
    public void rateUser(String userId, final int rating) {
        // determine which user is submitting the rating, and which user is getting rated
        // set the database reference to the user which is getting rated
        if(userId == userId1) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId2);
        }
        else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId1);
        }
        // update the rating
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            // add rating to the total rating score, and then increment the rating count
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the current values from the db
                double totalScore = dataSnapshot.child("totalScore").getValue(Double.class);
                double totalReviews = dataSnapshot.child("totalReviews").getValue(Double.class);

                // update
                totalScore += Double.valueOf(rating);
                totalReviews++;

                // add in the updated values to the db
                mDatabase.child("totalScore").setValue(totalScore);
                mDatabase.child("totalReviews").setValue(totalReviews);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getMatchId() {
        return matchId;
    }

    public String getUserId1() {
        return userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public boolean isMatchAccepted() {
        return matchAccepted;
    }

    public boolean isUser1Choice() {
        return user1Choice;
    }

    public boolean isUser2Choice() {
        return user2Choice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(matchId);
        parcel.writeString(userId1);
        parcel.writeString(userId2);
        parcel.writeByte((byte) (user1Choice ? 1 : 0));
        parcel.writeByte((byte) (user2Choice ? 1 : 0));
    }
}
