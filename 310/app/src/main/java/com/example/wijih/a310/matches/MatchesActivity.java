package com.example.wijih.a310.matches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wijih.a310.R;
import com.example.wijih.a310.SwipingActivity;
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
        matchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
        rView.setAdapter(matchesAdapter);

        // DUMMY DATA
        Matches match1 = new Matches("testUserID1");
        Matches match2 = new Matches("testUserID2");
        Matches match3 = new Matches("testUserID3");
        Matches match4 = new Matches("testUserID4");
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


    private ArrayList<Matches> resultsMatches = new ArrayList<Matches>();
    private List<Matches> getDataSetMatches() {
        return resultsMatches;
    }


    public void goToSwiping(View view) {
        Intent goToMatchesIntent = new Intent(MatchesActivity.this, SwipingActivity.class);
        goToMatchesIntent.putExtra("current_user", currentUser);
        startActivity(goToMatchesIntent);
        return;
    }
}
