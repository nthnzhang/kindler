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
    private String user1Email;
    private String user2Email;
    private Double user1TotalScore;
    private Double user2TotalScore;
    private Double user1TotalRatings;
    private Double user2TotalRatings;

    // false - no choice made
    // true - accepted match
    private boolean user1Choice;
    private boolean user2Choice;
    private boolean matchAccepted;

    // false - no choice made
    // true - choice (denied or accepted) made
    private boolean user1ChoiceMade;
    private boolean user2ChoiceMade;

    private boolean user1HasRated;
    private boolean user2HasRated;

    private DatabaseReference mDatabase;


    public Match(String userId1, String userId2, String user1Email, String user2Email,
                 Double user1TotalRatings, Double user2TotalRatings, Double user1TotalScore,
                 Double user2TotalScore) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.user1Email = user1Email;
        this.user2Email = user2Email;
        this.user1Choice = false;
        this.user2Choice = false;
        this.matchAccepted = false;
        this.user1ChoiceMade = false;
        this.user2ChoiceMade = false;
        this.user1TotalRatings = user1TotalRatings;
        this.user2TotalRatings = user2TotalRatings;
        this.user1TotalScore = user1TotalScore;
        this.user2TotalScore = user2TotalScore;
        this.user1HasRated = false;
        this.user2HasRated = false;
    }

    public Match() {
        // empty constructor for firebase purposes
    }

    protected Match(Parcel in) {
        matchId = in.readString();
        userId1 = in.readString();
        userId2 = in.readString();
        user1Email = in.readString();
        user2Email = in.readString();
        user1Choice = in.readByte() != 0;
        user2Choice = in.readByte() != 0;
        matchAccepted = in.readByte() != 0;
        user1ChoiceMade = in.readByte() != 0;
        user2ChoiceMade = in.readByte() != 0;
        user1TotalRatings = in.readDouble();
        user2TotalRatings = in.readDouble();
        user1TotalScore = in.readDouble();
        user2TotalScore = in.readDouble();
        user1HasRated = in.readByte() != 0;
        user2HasRated = in.readByte() != 0;
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

    public void setUser1Choice(boolean user1Choice) {
        this.user1Choice = user1Choice;
    }

    public void setUser2Choice(boolean user2Choice) {
        this.user2Choice = user2Choice;
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
            user1Choice = true;
            mDatabase.child("user1Choice").setValue(true);
            user1ChoiceMade = true;
            mDatabase.child("user1ChoiceMade").setValue(true);
        }
        // user2 is accepting the match
        else {
            // user1 has accepted the match already
            if(user1Choice) {
                // show contact info
                matchAccepted = true;
                mDatabase.child("matchAccepted").setValue(true);
            }
            user2Choice = true;
            mDatabase.child("user2Choice").setValue(true);
            user2ChoiceMade = true;
            mDatabase.child("user2ChoiceMade").setValue(true);

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
        if(userId.equals(userId1)) {
            user2TotalScore += Double.valueOf(rating);
            user2TotalRatings++;
            user1HasRated = true;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
            mDatabase.child("user1HasRated").setValue(true);

            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId2);
        }
        else {
            user1TotalScore += Double.valueOf(rating);
            user1TotalRatings++;
            user2HasRated = true;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
            mDatabase.child("user2HasRated").setValue(true);

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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
        mDatabase.child("userRated").setValue(true);
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

    public boolean isUser1ChoiceMade() { return user1ChoiceMade || user1Choice; }

    public void setUser1ChoiceMade(boolean user1ChoiceMade) { this.user1ChoiceMade = user1ChoiceMade; }

    public boolean isUser2ChoiceMade() { return user2ChoiceMade || user2Choice; }

    public void setUser2ChoiceMade(boolean user2ChoiceMade) { this.user2ChoiceMade = user2ChoiceMade; }

    public String getUser1Email() {
        return this.user1Email;
    }

    public String getUser2Email() {
        return this.user2Email;
    }

    public Double getUser1TotalScore() {
        return user1TotalScore;
    }

    public Double getUser2TotalScore() {
        return user2TotalScore;
    }

    public Double getUser1TotalRatings() {
        return user1TotalRatings;
    }

    public Double getUser2TotalRatings() {
        return user2TotalRatings;
    }

    public Double getUserRating(String userId) {
        if(userId.equals(userId1)) {
            if(user2TotalRatings == 0.0) {
                return 0.0;
            }
            return user2TotalScore/user2TotalRatings;
        }
        else {
            if(user1TotalRatings == 0.0) {
                return 0.0;
            }
            return user1TotalScore/user1TotalRatings;
        }
    }

    public boolean isUser1HasRated() {
        return user1HasRated;
    }

    public boolean isUser2HasRated() {
        return user2HasRated;
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
        parcel.writeString(user1Email);
        parcel.writeString(user2Email);
        parcel.writeByte((byte) (user1Choice ? 1 : 0));
        parcel.writeByte((byte) (user2Choice ? 1 : 0));
        parcel.writeByte((byte) (matchAccepted ? 1: 0));
        parcel.writeByte((byte) (user1ChoiceMade ? 1 : 0));
        parcel.writeByte((byte) (user2ChoiceMade ? 1 : 0));
        parcel.writeDouble(user1TotalRatings);
        parcel.writeDouble(user2TotalRatings);
        parcel.writeDouble(user1TotalScore);
        parcel.writeDouble(user2TotalScore);
        parcel.writeByte((byte) (user1HasRated ? 1 : 0));
        parcel.writeByte((byte) (user2HasRated ? 1 : 0));
    }
}
