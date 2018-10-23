package com.example.wijih.a310;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.example.wijih.a310.model.Book;
import com.example.wijih.a310.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipingActivity extends Activity {
    private User currentUser;
    private ArrayList<Book> alBooks;
    private Button button;

    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;

    private DatabaseReference mDatabase;

    @BindView(R.id.frame) SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swiping_activity);
        ButterKnife.bind(this);

        //button to go to matches
//        button = (Button)findViewById(R.id.toMatches);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMatchesActivity();
//            }
//        });

        // getting current user form login activity
        final Intent currUserIntent = getIntent();
//        currentUser = currUserIntent.getParcelableExtra("current_user");

        alBooks = new ArrayList<Book>();
        al = new ArrayList<String>();

        // for testing purposes, as there is no user being passed in
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("-LPPnx7w9EA44RcPSdzQ");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                currentUser = user;
                startUpdatingBookList(currentUser.getUserID());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // currently commented out, but will be added back in when user intent is functional
//        startUpdatingBookList(currentUser.getUserID());

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.bookTitleText, al);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // deleting object from Adapter / AdapterView
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Toast.makeText(SwipingActivity.this, "Disliked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(SwipingActivity.this, "Liked!", Toast.LENGTH_SHORT).show();
                 currentUser.addLike(alBooks.get(i).getBookId());
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // A user can tap on a book profile to view additional details on the book
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Intent intent = new Intent(SwipingActivity.this, BookInfoWindow.class);
                intent.putExtra("book_info", alBooks.get(i));
                startActivity(intent);
                Toast.makeText(SwipingActivity.this, "Additional book info will go here...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startUpdatingBookList(final String userId) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("books");
        mDatabase.addChildEventListener(new ChildEventListener() {
            // new book has been added
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // update the list and the array adapter
                Book book = dataSnapshot.getValue(Book.class);
                if(book.getOwnerID() != userId) {
                    alBooks.add(book);
                    al.add(book.getTitle());
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //open matches
//    public void openMatchesActivity(){
//        Intent intent = new Intent(this, MatchesActivity.class);
//        startActivity(intent);
//    }
}
