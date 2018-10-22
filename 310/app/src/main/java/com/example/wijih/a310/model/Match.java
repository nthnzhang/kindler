package com.example.wijih.a310.model;

public class Match {
    private String matchId;
    private String userId1;
    private String userId2;

    // false - no choice made
    // true - accepted match
    private boolean user1Choice;
    private boolean user2Choice;

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
        if(userId == userId1) {
            // user2 has accepted the match already
            if(user2Choice) {

            }
            else {
                user1Choice = true;
            }
        }
        else {
            // user1 has accepted the match already
            if(user1Choice) {

            }
            else {
                user2Choice = true;
            }
        }
    }

    // remove match from both users list of matches
    public void denyMatch() {

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
