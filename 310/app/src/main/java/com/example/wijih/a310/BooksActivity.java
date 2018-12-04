package com.example.wijih.a310;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wijih.a310.R;
import com.example.wijih.a310.SwipingActivity;
import com.example.wijih.a310.model.Book;
import com.example.wijih.a310.model.Match;
import com.example.wijih.a310.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private User currentUser;
    private RecyclerView rView;
    private RecyclerView.Adapter booksAdapter;
    private RecyclerView.LayoutManager matchesLayoutManager;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        startUpdatingBookList();
        // getting current user from swiping activity
        final Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");

        rView = findViewById(R.id.matchesRecyclerView);
        rView.setNestedScrollingEnabled(false);
        rView.setHasFixedSize(true);

        // set layout manager to recycler view
        matchesLayoutManager = new LinearLayoutManager(BooksActivity.this);
        rView.setLayoutManager(matchesLayoutManager);

        // set adapter to recycler view
        booksAdapter = new BooksAdapter(getDataSetMatches(), BooksActivity.this, currentUser);
        rView.setAdapter(booksAdapter);

        booksAdapter.notifyDataSetChanged();
    }


    private ArrayList<Book> resultsBooks = new ArrayList<Book>();
    private List<Book> getDataSetMatches() {
        return resultsBooks;
    }

    private void startUpdatingBookList() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("books");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Book book = dataSnapshot.getValue(Book.class);

                // add book
                if(book.getOwnerID().equals(currentUser.getUserID())) {
                    resultsBooks.add(book);
                    booksAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Book book = dataSnapshot.getValue(Book.class);

                if(book.getOwnerID().equals(currentUser.getUserID())) {

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);

                if(book.getOwnerID().equals(currentUser.getUserID())) {
                    // remove book
                    resultsBooks.remove(book);
                    booksAdapter.notifyDataSetChanged();
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

    public void goToProfile (View view) {
        Intent intent = new Intent(BooksActivity.this, ProfileActivity.class);
        intent.putExtra("current_user", currentUser);
        startActivity(intent);
        return;
    }
}