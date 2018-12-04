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

                // match is one of current user's matches
                if(book.getOwnerID().equals(currentUser.getUserID())) {
                    //Log.d("match", match.getMatchId());
                    resultsBooks.add(book);
                    booksAdapter.notifyDataSetChanged();
                    //matchesAdapter.notifyDataSetChanged();
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
                    // remove match
                    resultsBooks.remove(book);
                    booksAdapter.notifyDataSetChanged();
                    //matchesAdapter.notifyDataSetChanged();
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


























//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.wijih.a310.model.Book;
//import com.example.wijih.a310.model.Match;
//import com.example.wijih.a310.model.User;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.lang.String;
//
//
//public class BooksActivity extends AppCompatActivity {
//    ListView verticalBooks;
//    //List<String> books = user.getBooks();
//    List<String> books = new ArrayList<String>();
//    private User currentUser;
//    private List<String> uploadedBooks;
//    private DatabaseReference mDatabase;
//    private ArrayList<Book> resultsMatches = new ArrayList<Book>();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_books);
//
//        final Intent currUserIntent = getIntent();
//        currentUser = currUserIntent.getParcelableExtra("current_user");
//            startUpdatingMatchList();
//            //uploadedBooks = currentUser.getBooksUploaded();
//        verticalBooks = (ListView) findViewById(R.id.verticalBooksView);
//
//
//            /*if (resultsMatches != null && resultsMatches.size() != 0) {
//                System.out.println(resultsMatches.size());
//                for (int i = 0; i < resultsMatches.size(); i++) {
//                    System.out.println(resultsMatches.get(i).getTitle());
//                    books.add(resultsMatches.get(i).getTitle());
//                }
//            }
//            else {
//                books.add("harry potter");
//                books.add("Hunger games");
//                books.add("friends");
//                books.add("the office");
//                books.add("hello");
//                books.add("zach");
//            }
//
//
//
//
//
//            for (int i = 0; i < books.size(); i++) {
//                System.out.println(books.get(i));
//            }*/
//
//
//
//
//            verticalBooks.setAdapter(new MyListAdapter(this, R.layout.activity_books_view, getDataSetMatches()));
//            System.out.println("hello 1");
//
//
//        /*verticalBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(BooksActivity.this, books.get(position), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(view.getContext(), EditBookActivity.class);
//                startActivity(intent);
//            }
//        });*/
//        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_books_view, books);
//        //verticalBooks.setAdapter(adapter);
//    }
//
//
//
//    private void startUpdatingMatchList() {
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("books");
//
//        mDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Book book = dataSnapshot.getValue(Book.class);
//
//                // match is one of current user's matches
//                if(book.getOwnerID().equals(currentUser.getUserID())) {
//                    //Log.d("match", match.getMatchId());
//                    resultsMatches.add(book);
//                    //matchesAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Book book = dataSnapshot.getValue(Book.class);
//
//                if(book.getOwnerID().equals(currentUser.getUserID())) {
//
//                }
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Book book = dataSnapshot.getValue(Book.class);
//
//                if(book.getOwnerID().equals(currentUser.getUserID())) {
//                    // remove match
//                    resultsMatches.remove(book);
//                    //matchesAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
//    private List<String> getDataSetMatches() {
//        List<String> books = new ArrayList<String>();
//        for (int i = 0; i <resultsMatches.size(); i++) {
//            books.add(resultsMatches.get(i).getTitle());
//        }
//
//        return books;
//    }
//
//
//
//
//    private class MyListAdapter extends ArrayAdapter<String> {
//        private int layout;
//        public MyListAdapter(Context context, int resource, List<String> objects) {
//            super(context, resource, objects);
//            layout = resource;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final ViewHolder holder;
//            System.out.println("hello 2");
//            if (convertView == null) {
//                System.out.println("hello 3");
//                System.out.println("1: " + position);
//                System.out.println("1: " + books.get(position));
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                convertView = inflater.inflate(layout, parent, false);
//
//                holder = new ViewHolder();
//                holder.thumbnail = (ImageView) convertView.findViewById(R.id.listItemThumbnail);
//                holder.title = (TextView) convertView.findViewById(R.id.listItemText);
//                holder.button = (Button) convertView.findViewById(R.id.listItemBtn);
//                holder.button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //startActivity(new Intent(this, EditBookActivity.class));
//                        Intent intent = new Intent(v.getContext(), EditBookActivity.class);
//                        intent.putExtra("current_user", currentUser);
//                        intent.putExtra("bookId", books.get(position));
//                        startActivity(intent);
//                        Toast.makeText(getContext(), "Button was clicked for list item " + books.get(position), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                convertView.setTag(holder);
//            }
//            else  {
//                System.out.println("hello 4");
//                holder = (ViewHolder) convertView.getTag();
//                System.out.println("2: " + position);
//                System.out.println("2: " + books.get(position));
//
//            }
//            holder.title.setText(books.get(position));
//            return convertView;
//        }
//    }
//
//    public class ViewHolder {
//        ImageView thumbnail;
//        TextView title;
//        Button button;
//    }
//}
//
//
//
//
//
//
///*package com.example.wijih.a310;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.example.wijih.a310.model.User;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BooksActivity extends AppCompatActivity {
//
//    ListView verticalBooks;
//    //List<String> books = user.getBooks();
//    List<String> books = new ArrayList<String>();
//
//    private User currentUser;
//    private DatabaseReference mDatabase;
//
//    private ArrayAdapter<String> adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_books);
//
//        final Intent currUserIntent = getIntent();
//        currentUser = currUserIntent.getParcelableExtra("current_user");
//
//        //List<String> books = new ArrayList<String>(currentUser.getBooksUploaded());
//
//        verticalBooks = (ListView) findViewById(R.id.verticalBooksView);
//
//        adapter = new ArrayAdapter<String>(BooksActivity.this, R.layout.activity_books_view, R.id.booksDisplay, books);
//        verticalBooks.setAdapter(adapter);
//
//
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserID()).child("uploadedBookIDs");
//
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    // getting each bookId
//                    Log.d("test", userSnapshot.getValue(String.class));
//                    books.add(userSnapshot.getValue(String.class));
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//
//
//    }
//
//
//}*/
