package com.example.wijih.a310.matches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.wijih.a310.R;
import com.example.wijih.a310.SwipingActivity;
import com.example.wijih.a310.model.Match;
import com.example.wijih.a310.model.User;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private User currentUser;
    private RecyclerView rView;
    private RecyclerView.Adapter matchesAdapter;
    private RecyclerView.LayoutManager matchesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        // getting current user from swiping activity
        final Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");

        rView = findViewById(R.id.matchesRecyclerView);
        rView.setNestedScrollingEnabled(false);
        rView.setHasFixedSize(true);

        // set layout manager to recycler view
        matchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        rView.setLayoutManager(matchesLayoutManager);

        // set adapter to recycler view
        matchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this, currentUser);
        rView.setAdapter(matchesAdapter);


        // DUMMY DATA
        Match match1 = new Match("testUserID1", currentUser.getUserID());
        Match match2 = new Match("testUserID2", currentUser.getUserID());
        Match match3 = new Match("testUserID3", currentUser.getUserID());
        Match match4 = new Match("testUserID4", currentUser.getUserID());
        resultsMatches.add(match1);
        resultsMatches.add(match2);
        resultsMatches.add(match3);
        resultsMatches.add(match4);
        resultsMatches.add(match1);
        resultsMatches.add(match2);
        resultsMatches.add(match3);
        resultsMatches.add(match4);

        matchesAdapter.notifyDataSetChanged();
    }


    private ArrayList<Match> resultsMatches = new ArrayList<Match>();
    private List<Match> getDataSetMatches() {
        return resultsMatches;
    }

    public void goToSwiping(View view) {
        Intent goToMatchesIntent = new Intent(MatchesActivity.this, SwipingActivity.class);
        goToMatchesIntent.putExtra("current_user", currentUser);
        startActivity(goToMatchesIntent);
        return;
    }
}
