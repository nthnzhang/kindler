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

    @BindView(R.id.frame) SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swiping_activity);
        ButterKnife.bind(this);

        // getting current user form login activity
        Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");


        // al = UNIVERSAL BOOK LIST FROM DATABASE
        alBooks = new ArrayList<>();
        al = new ArrayList<>();

        // FAKE BOOK LISTS HERE
        List<String> tags = new ArrayList<>();
        alBooks.add(new Book("Harry Potter and the Sorcerer's Stone", "blah blah blah description", "ownerID?!?!?!", false, tags));
        alBooks.add(new Book("Harry Potter and the Chamber of Secrets", "blah blah blah description", "ownerID?!?!?!", false, tags));
        alBooks.add(new Book("Harry Potter and the Prisoner of Azkaban", "blah blah blah description", "ownerID?!?!?!", false, tags));
        alBooks.add(new Book("Harry Potter and the Goblet of Fire", "blah blah blah description", "ownerID?!?!?!", false, tags));
        alBooks.add(new Book("Harry Potter and the Order of the Phoenix", "blah blah blah description", "ownerID?!?!?!", false, tags));
        alBooks.add(new Book("Harry Potter and the Half Blood Prince", "blah blah blah description", "ownerID?!?!?!", false, tags));
        alBooks.add(new Book("Harry Potter and the Deathly Hallows", "blah blah blah description", "ownerID?!?!?!", false, tags));

        al.add("Harry Potter and the Sorcerer's Stone");
        al.add("Harry Potter and the Chamber of Secrets");
        al.add("Harry Potter and the Prisoner of Azkaban");
        al.add("Harry Potter and the Goblet of Fire");
        al.add("Harry Potter and the Order of the Phoenix");
        al.add("Harry Potter and the Half Blood Prince");
        al.add("Harry Potter and the Deathly Hallows");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.bookTitleText, al );


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
}
