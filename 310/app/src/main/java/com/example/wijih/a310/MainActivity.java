package com.example.wijih.a310;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.View;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;

public class MainActivity extends Activity {
    private ArrayList<String> al; // NEEDS TO BE REPLACED WITH ACTUAL ARRAY OF BOOKS HOOKED UP TO DATABASE
    private ArrayAdapter<String> arrayAdapter;
    private int i;

    @BindView(R.id.frame) SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);


        al = new ArrayList<>();
        al.add("Harry Potter and the Sorcerer's Stone");
        al.add("Harry Potter and the Chamber of Secrets");
        al.add("Harry Potter and the Prisoner of Azkaban");
        al.add("Harry Potter and the Goblet of Fire");
        al.add("The Hobbit");
        al.add("Life of Pi");
        al.add("The Lord of the Rings");
        al.add("Cat in a Hat");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );


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
                Toast.makeText(MainActivity.this, "Disliked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(MainActivity.this, "Liked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // HAVE TO CHANGE THIS STATEMENT ENTIRELY TO QUERY DATABASE FOR MORE DATA
                al.add("BOOK ".concat(String.valueOf(i)));


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
        });g

        // A user can tap on a book profile to view additional details on the book
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                // CHANGE FUNCTION TO QUERY BOOK INFORMATION FROM DATABASE AND DISPLAY IT

                startActivity(new Intent(MainActivity.this, BookInfoWindow.class));

                Toast.makeText(MainActivity.this, "Additional book info will go here...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
