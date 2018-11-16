package com.example.wijih.a310.matches;

import android.app.Activity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.matches_info_window);

        // getting current match object and current user
        match = getIntent().getParcelableExtra("match_obj");
        currentUser = getIntent().getParcelableExtra("current_user");
        boolean isUser1;

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
        TextView matchNameView = findViewById(R.id.matchNameView);
        matchNameView.setText(nameToDisplay);


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

            TextView  matchEmailView = findViewById(R.id.matchContactInfo);
            matchEmailView.setText(email);
            matchEmailView.setVisibility(View.VISIBLE);
        }
        else {
            // user has yet to accept or deny match
            Button acceptButton = findViewById(R.id.acceptMatchButton);
            Button denyButton = findViewById(R.id.denyMatchButton);

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
        match.acceptMatch(currentUser.getUserID());
    }

    public void denyMatchClicked(View view) {
        match.denyMatch();
    }
}
