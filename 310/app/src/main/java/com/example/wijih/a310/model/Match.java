package com.example.wijih.a310.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Match {
    private String matchId;
    private String userId1;
    private String userId2;

    // false - no choice made
    // true - accepted match
    private boolean user1Choice;
    private boolean user2Choice;

    private DatabaseReference mDatabase;


    public Match(String userId1, String userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.user1Choice = false;
        this.user2Choice = false;
    }

    public Match() {
        // empty constructor for firebase purposes
    }

    public void setMatchId(String id) {
        this.matchId = id;
    }

    // input: user id of the user that is accepting the match
    public void acceptMatch(String userId) {
        // user1 is accepting the match
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches").child(matchId);
        if(userId == userId1) {
            // user2 has accepted the match already
            if(user2Choice) {
                // show contact info
            }
            mDatabase.child("user1Choice").setValue(true);

        }
        // user2 is accepting the match
        else {
            // user1 has accepted the match already
            if(user1Choice) {
                // show contact info
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

    public void rateUser(String userId, int rating) {

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

    public boolean isUser1Choice() {
        return user1Choice;
    }

    public boolean isUser2Choice() {
        return user2Choice;
    }
}
