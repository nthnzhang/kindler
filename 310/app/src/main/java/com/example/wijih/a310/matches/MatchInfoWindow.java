package com.example.wijih.a310.matches;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wijih.a310.R;
import com.example.wijih.a310.model.Book;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.matches_info_window);

        // getting current match object and current user
        match = getIntent().getParcelableExtra("match_obj");
        currentUser = getIntent().getParcelableExtra("current_user");

        // displayed matched user's name
        String nameToDisplay;
        if(match.getUserId1() == currentUser.getUserID()) {
            nameToDisplay = match.getUserId2();
            isUser1 = true;
        }
        else {
            nameToDisplay = match.getUserId1();
            isUser1 = false;
        }

        matchNameView = findViewById(R.id.matchNameView);
        matchNameView.setText(nameToDisplay);

        matchEmailView = findViewById(R.id.matchContactInfo);
        matchPendingView = findViewById(R.id.matchPending);
        acceptButton = findViewById(R.id.acceptMatchButton);
        denyButton = findViewById(R.id.denyMatchButton);


        // checking if other user has accepted match
        if(match.isUser1Choice() && match.isUser2Choice()) {
            // safe to display contact information
            String email;
            if(isUser1) {
                email = match.getUserId2(); // NEED TO CHANGE THIS TO DISPLAY ACTUAL EMAIL
            }
            else {
                email = match.getUserId1(); // NEED TO CHANGE THIS TO DISPLAY ACTUALY EMAIL
            }

            matchEmailView.setText(email);
            matchEmailView.setVisibility(View.VISIBLE);
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

        getWindow().setLayout((int)(0.4*height), (int)(0.4*height));

    }

    public void acceptMatchClicked(View view) {
        // indicating that the current user has made a choice
        if(isUser1) {
            match.setUser1ChoiceMade(true);
        }
        else {
            match.setUser2ChoiceMade(true);
        }

        match.acceptMatch(currentUser.getUserID());

        acceptButton.setVisibility(View.GONE);
        denyButton.setVisibility(View.GONE);

        if(match.isUser1Choice() && match.isUser2Choice()) {
            // safe to display contact information
            String email;
            if(isUser1) {
                email = match.getUserId2(); // NEED TO CHANGE THIS TO DISPLAY ACTUAL EMAIL
            }
            else {
                email = match.getUserId1(); // NEED TO CHANGE THIS TO DISPLAY ACTUAL EMAIL
            }

            matchEmailView.setText(email);
            matchEmailView.setVisibility(View.VISIBLE);
        }
        else {
            matchPendingView.setText("Match accepted, waiting for other user's response.");
            matchPendingView.setVisibility(View.VISIBLE);
        }
    }

    public void denyMatchClicked(View view) {
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
    }
}
