package com.example.wijih.a310.matches;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.wijih.a310.R;
import com.example.wijih.a310.model.Match;
import com.example.wijih.a310.model.User;

public class MatchInfoWindow extends Activity {
    private Match match;
    private User currentUser;
    private boolean isUser1;
    private Button acceptButton;
    private Button denyButton;
    private TextView matchNameView;
    private TextView matchEmailView;
    private TextView matchPendingView;
    private RatingBar ratingBar;
    private Button ratingBarButton;
    private TextView ratingSubmitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.matches_info_window);

        // getting current match object and current user
        match = getIntent().getParcelableExtra("match_obj");
        currentUser = getIntent().getParcelableExtra("current_user");

        // displayed matched user's name
        String nameToDisplay;
        double rating;
        if(match.getUserId1().equals(currentUser.getUserID())) {
            nameToDisplay = match.getUserId2();

            if(match.getUserRating(match.getUserId2()) != null) {
                rating = match.getUserRating(match.getUserId2());
            }
            else {
                rating = 0.0;
            }

            nameToDisplay += " " + rating;
            isUser1 = true;
        }
        else {
            nameToDisplay = match.getUserId1();

            if(match.getUserRating(match.getUserId1()) != null) {
                rating = match.getUserRating(match.getUserId1());
            }
            else {
                rating = 0.0;
            }

            nameToDisplay += " " + rating;
            isUser1 = false;
        }

        matchNameView = findViewById(R.id.matchNameView);
        matchNameView.setText(nameToDisplay);

        matchEmailView = findViewById(R.id.matchContactInfo);
        matchPendingView = findViewById(R.id.matchPending);
        acceptButton = findViewById(R.id.acceptMatchButton);
        denyButton = findViewById(R.id.denyMatchButton);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBarButton = findViewById(R.id.ratingBarSubmit);
        ratingSubmitted = findViewById(R.id.ratingSubmitted);

        Log.d("function", "on create");
        Log.d("user name", currentUser.getUsername());
        Log.d("is user 1", (isUser1 ? "true": "false"));
        Log.d("user  1 choice", (match.isUser1Choice() ? "true": "false"));
        Log.d("user  2 choice", (match.isUser2Choice() ? "true": "false"));
        Log.d("user  1 choice made", (match.isUser1ChoiceMade() ? "true": "false"));
        Log.d("user  2 choice made", (match.isUser2ChoiceMade() ? "true": "false"));


        // checking if other user has accepted match
        if(match.isUser1Choice() && match.isUser2Choice()) {
            // safe to display contact information
            String email;
            if(isUser1) {
                email = match.getUser2Email();
            }
            else {
                email = match.getUser1Email();
            }

            matchEmailView.setText(email);
            matchEmailView.setVisibility(View.VISIBLE);

            displayRatingBar();
        }
        else if(isUser1 && match.isUser1ChoiceMade()) {
            // current user is user 1 and choice has been made

            if(match.isUser1Choice())  {
                // current user has accepted match
                matchPendingView.setText("Match accepted, waiting for other user's response.");
            }
            else {
                // current user denied match
                matchPendingView.setText("Match denied.");
            }
            matchPendingView.setVisibility(View.VISIBLE);
        }
        else if(!isUser1 && match.isUser2ChoiceMade()) {
            // current user is user 2 and choice has been made

            if(match.isUser2Choice())  {
                // current user has accepted match
                matchPendingView.setText("Match accepted, waiting for other user's response.");
            }
            else {
                matchPendingView.setText("Match denied.");
            }
            matchPendingView.setVisibility(View.VISIBLE);
        }
        else {
            // current user has yet to accept or deny match
            acceptButton.setVisibility(View.VISIBLE);
            denyButton.setVisibility(View.VISIBLE);
        }



        // adjusting pop up window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;

        getWindow().setLayout((int)(0.4*height), (int)(0.6*height));

    }

    public void acceptMatchClicked(View view) {
        // indicating that the current user has made a choice

        Log.d("function", "accept match clicked");
        Log.d("user name", currentUser.getUsername());
        Log.d("is user 1", (isUser1 ? "true": "false"));
        Log.d("user  1 choice", (match.isUser1Choice() ? "true": "false"));
        Log.d("user  2 choice", (match.isUser2Choice() ? "true": "false"));
        Log.d("user  1 choice made", (match.isUser1ChoiceMade() ? "true": "false"));
        Log.d("user  2 choice made", (match.isUser2ChoiceMade() ? "true": "false"));

        match.acceptMatch(currentUser.getUserID());

        acceptButton.setVisibility(View.GONE);
        denyButton.setVisibility(View.GONE);

        if(match.isUser1Choice() && match.isUser2Choice()) {
            // safe to display contact information
            String email;
            if(isUser1) {
                email = match.getUser2Email();
            }
            else {
                email = match.getUser1Email();
            }

            matchEmailView.setText(email);

            Log.d("email", email);

            matchEmailView.setVisibility(View.VISIBLE);
            displayRatingBar();
        }
        else {
            matchPendingView.setText("Match accepted, waiting for other user's response.");
            matchPendingView.setVisibility(View.VISIBLE);
        }

        Log.d("function", "accept match finish");
        Log.d("user name", currentUser.getUsername());
        Log.d("is user 1", (isUser1 ? "true": "false"));
        Log.d("user  1 choice", (match.isUser1Choice() ? "true": "false"));
        Log.d("user  2 choice", (match.isUser2Choice() ? "true": "false"));
        Log.d("user  1 choice made", (match.isUser1ChoiceMade() ? "true": "false"));
        Log.d("user  2 choice made", (match.isUser2ChoiceMade() ? "true": "false"));

        Intent returnIntent = new Intent();
        returnIntent.putExtra("userChoice", true);
        returnIntent.putExtra("currentUsername", currentUser.getUsername());
        returnIntent.putExtra("matchID", match.getMatchId());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void denyMatchClicked(View view) {

        Log.d("function", "deny match clicked");
        Log.d("user name", currentUser.getUsername());
        Log.d("is user 1", (isUser1 ? "true": "false"));
        Log.d("user  1 choice", (match.isUser1Choice() ? "true": "false"));
        Log.d("user  2 choice", (match.isUser2Choice() ? "true": "false"));
        Log.d("user  1 choice made", (match.isUser1ChoiceMade() ? "true": "false"));
        Log.d("user  2 choice made", (match.isUser2ChoiceMade() ? "true": "false"));

        // indicating that the current user has made a choice
        if(isUser1) {
            match.setUser1ChoiceMade(true);
        }
        else {
            match.setUser2ChoiceMade(true);
        }

        match.denyMatch();

        acceptButton.setVisibility(View.GONE);
        denyButton.setVisibility(View.GONE);

        matchPendingView.setText("Match denied.");
        matchPendingView.setVisibility(View.VISIBLE);

        Log.d("function", "deny match finished");
        Log.d("user name", currentUser.getUsername());
        Log.d("is user 1", (isUser1 ? "true": "false"));
        Log.d("user  1 choice", (match.isUser1Choice() ? "true": "false"));
        Log.d("user  2 choice", (match.isUser2Choice() ? "true": "false"));
        Log.d("user  1 choice made", (match.isUser1ChoiceMade() ? "true": "false"));
        Log.d("user  2 choice made", (match.isUser2ChoiceMade() ? "true": "false"));

        Intent returnIntent = new Intent();
        returnIntent.putExtra("userChoice", false);
        returnIntent.putExtra("currentUsername", currentUser.getUsername());
        returnIntent.putExtra("matchID", match.getMatchId());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void displayRatingBar() {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        ratingBar.setVisibility(View.VISIBLE);
        ratingBarButton.setVisibility(View.VISIBLE);

        ratingBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUser1) {
                    match.rateUser(match.getUserId1(), (int) ratingBar.getRating());
                }
                else {
                    match.rateUser(match.getUserId2(), (int) ratingBar.getRating());
                }

                ratingBar.setVisibility(View.GONE);
                ratingBarButton.setVisibility(View.GONE);
                ratingSubmitted.setText("Rating Submitted!");
                ratingSubmitted.setVisibility(View.VISIBLE);
            }
        });
    }
}
