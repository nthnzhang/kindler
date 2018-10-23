package com.example.wijih.a310;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.example.wijih.a310.model.User;
import com.example.wijih.a310.model.Book;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

public class SwipingActivity extends Activity {
    private User currentUser;
    private ArrayList<Book> alBooks;

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

        // getting current user form login activity
        Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");

        // getting list of books from database
//        List<String> swipableBooksIDs = currentUser.getSwipableBookIds();
//        alBooks = getBookObjs(swipableBooksIDs);
        alBooks = new ArrayList<>();
        startUpdatingBookList();

        al = getBookTitles(alBooks);

        // FAKE FAKE FAKE FAKE FAKE FAKE FAKE
//        al = new ArrayList<>();


//        List<String> tags = new ArrayList<>();
//        alBooks.add(new Book("Harry Potter and the Sorcerer's Stone", "blah blah blah description", "ownerID?!?!?!", false, tags));
//        alBooks.add(new Book("Harry Potter and the Chamber of Secrets", "blah blah blah description", "ownerID?!?!?!", false, tags));
//        alBooks.add(new Book("Harry Potter and the Prisoner of Azkaban", "blah blah blah description", "ownerID?!?!?!", false, tags));
//        alBooks.add(new Book("Harry Potter and the Goblet of Fire", "blah blah blah description", "ownerID?!?!?!", false, tags));
//        alBooks.add(new Book("Harry Potter and the Order of the Phoenix", "blah blah blah description", "ownerID?!?!?!", false, tags));
//        alBooks.add(new Book("Harry Potter and the Half Blood Prince", "blah blah blah description", "ownerID?!?!?!", false, tags));
//        alBooks.add(new Book("Harry Potter and the Deathly Hallows", "blah blah blah description", "ownerID?!?!?!", false, tags));
//
//        al.add("Harry Potter and the Sorcerer's Stone");
//        al.add("Harry Potter and the Chamber of Secrets");
//        al.add("Harry Potter and the Prisoner of Azkaban");
//        al.add("Harry Potter and the Goblet of Fire");
//        al.add("Harry Potter and the Order of the Phoenix");
//        al.add("Harry Potter and the Half Blood Prince");
//        al.add("Harry Potter and the Deathly Hallows");

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
                // currentUser.addLike(alBooks.get(i).getBookId());
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // HAVE TO CHANGE THIS STATEMENT ENTIRELY TO QUERY DATABASE FOR MORE DATA
                // CALL GET BOOKS AGAIN

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

    private ArrayList<String> getBookTitles(ArrayList<Book> alBooks) {
        ArrayList<String> al = new ArrayList<>();
        for(Book b: alBooks) {
            al.add(b.getTitle());
        }
        return al;
    }

    private ArrayList<Book> getBookObjs(List<String> swipableBooksIDs) {
        ArrayList<Book> alBooks = new ArrayList<>();



        return alBooks;
    }
//
//    private void getBookObjects() {
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("books");
//
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Book book = dataSnapshot.getValue(Book.class);
//                alBooks.add(book);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void startUpdatingBookList() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("books");
        mDatabase.addChildEventListener(new ChildEventListener() {
            // new book has been added
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // update the list and the array adapter
                Book book = dataSnapshot.getValue(Book.class);
//                String newBookId = dataSnapshot.child("bookId").getValue(String.class);
                alBooks.add(book);
//                Log.d("testAddBook", "size: " + String.valueOf(swipableBookIds.size()));
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
}
