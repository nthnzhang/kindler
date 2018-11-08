package com.example.wijih.a310;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wijih.a310.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    ListView verticalBooks;
    //List<String> books = user.getBooks();
    List<String> books = new ArrayList<String>();

    private User currentUser;
    private DatabaseReference mDatabase;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        final Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");

        //List<String> books = new ArrayList<String>(currentUser.getBooksUploaded());

        verticalBooks = (ListView) findViewById(R.id.verticalBooksView);

        adapter = new ArrayAdapter<String>(BooksActivity.this, R.layout.activity_books_view, R.id.booksDisplay, books);
        verticalBooks.setAdapter(adapter);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserID()).child("uploadedBookIDs");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // getting each bookId
                    Log.d("test", userSnapshot.getValue(String.class));
                    books.add(userSnapshot.getValue(String.class));
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    /*public void editBook(View view) {
        Intent intent = new Intent(this, EditBookActivity.class);
        intent.putExtra("current_user", currentUser);
        startActivity(intent);
    }*/

}
