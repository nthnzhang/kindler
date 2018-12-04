package com.example.wijih.a310.matches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.example.wijih.a310.R;
import com.example.wijih.a310.SwipingActivity;
import com.example.wijih.a310.model.Match;
import com.example.wijih.a310.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private User currentUser;
    private RecyclerView rView;
    private RecyclerView.Adapter matchesAdapter;
    private RecyclerView.LayoutManager matchesLayoutManager;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        startUpdatingMatchList();
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

        matchesAdapter.notifyDataSetChanged();
    }


    private ArrayList<Match> resultsMatches = new ArrayList<Match>();
    private List<Match> getDataSetMatches() {
        return resultsMatches;
    }

    private void startUpdatingMatchList() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("matches");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Match match = dataSnapshot.getValue(Match.class);

                // match is one of current user's matches
                if (match.getUserId1() != null && match.getUserId2() != null) {
                    if (match.getUserId1().equals(currentUser.getUserID()) || match.getUserId2().equals(
                            currentUser.getUserID())) {
                        Log.d("match", match.getMatchId());
                        resultsMatches.add(match);
                        matchesAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Match match = dataSnapshot.getValue(Match.class);
                if (match.getUserId1() != null && match.getUserId2() != null) {
                    if(match.getUserId1().equals(currentUser.getUserID()) || match.getUserId2().equals(
                            currentUser.getUserID())) {
                        if(match.isMatchAccepted()) {
                            // show contact information

                        }
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Match match = dataSnapshot.getValue(Match.class);

                if (match.getUserId1() != null && match.getUserId2() != null) {
                    if(match.getUserId1().equals(currentUser.getUserID()) || match.getUserId2().equals(
                            currentUser.getUserID())) {
                        // remove match
                        resultsMatches.remove(match);
                        matchesAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void goToSwiping(View view) {
        Intent goToMatchesIntent = new Intent(MatchesActivity.this, SwipingActivity.class);
        goToMatchesIntent.putExtra("current_user", currentUser);
        startActivity(goToMatchesIntent);
        return;
    }
}
