package com.example.wijih.a310.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class UserTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    private DatabaseReference testDatabaseReference;
    private User testUser;

    private CountDownLatch lock = new CountDownLatch(1);

    @Before
    public void setUp() throws Exception {

        testDatabaseReference = FirebaseDatabase.getInstance().getReference();

        testUser = new User("testUser", "unit@test", "testing", "123456789", new ArrayList<String>(),
                new ArrayList<String>(), 0.0, 0.0, new HashMap<String, List<String >>());

    }

    // should be successful
    @Test
    public void addBook() throws Exception {
        Book testBook = new Book("testBook", "this is a unit test", "testOwnerId",
                false, new ArrayList<String>());
        testUser.addBook(testBook);
        testDatabaseReference = testDatabaseReference.child("books");

        testDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean foundBook = false;
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.child("title").getValue(String.class).equals("testBook")) {
                        foundBook = true;
                    }
                }
                try {
                    assertEquals(foundBook, true);
                } catch (Throwable t) {
                    collector.addError(t);
                }
                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lock.await();
    }

    // should fail because books need a title
    @Test
    public void addBookNoTitle() throws Exception {
        Book testBook = new Book("", "this is a unit test", "testOwnerId",
                false, new ArrayList<String>());
        testUser.addBook(testBook);

        testDatabaseReference = testDatabaseReference.child("books");

        testDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.child("title").getValue(String.class).equals("")) {
                        try {
                            assertEquals("fail", false);
                        } catch (Throwable t) {
                            collector.addError(t);
                        }
                        lock.countDown();
                        return;
                    }
                }
                try {
                    assertEquals("true", "true");
                } catch (Throwable t) {
                    collector.addError(t);
                }
                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lock.await();
    }

    // should fail, as a book needs an ownerID
    @Test
    public void addBookNoOwnerId() throws Exception {
        Book testBook = new Book("testBook", "this is a unit test", "",
                false, new ArrayList<String>());
        testUser.addBook(testBook);

        testDatabaseReference = testDatabaseReference.child("books");

        testDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.child("ownerID").getValue(String.class).equals("")) {
                        try {
                            assertEquals("fail", false);
                        } catch (Throwable t) {
                            collector.addError(t);
                        }
                        lock.countDown();
                        return;

                    }
                }
                try {
                    assertEquals("true", "true");
                } catch (Throwable t) {
                    collector.addError(t);
                }
                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lock.await();

    }


    // test to see if the owner updates their liked book list
    @Test
    public void addLike() throws Exception {
        testUser.addLike("-LQ0wlQcrHmzCZVzZwdp");

        testDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users")
            .child(testUser.getUserID());

        testDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;
                if(dataSnapshot.exists()) {
                    found = true;
                }

                try {
                    assertEquals(true, found);
                } catch (Throwable t) {
                    collector.addError(t);
                }
                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lock.await();
    }

    // should fail
    @Test
    public void addLikeToFakeBook() throws Exception {
        testUser.addLike("-123456789");

        testDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(testUser.getUserID());

        testDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;
                if(dataSnapshot.child("likedBooks").child("-LQ0upBUjqGtZjKOxHOH").
                        child("-123456789").exists()) {
                    found = true;
                }

                try {
                    assertEquals(false, found);
                } catch (Throwable t) {
                    collector.addError(t);
                }
                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lock.await();
    }

    // delete all added data from db
    @AfterClass
    public static void tearDown() throws Exception {
        DatabaseReference testDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final CountDownLatch lockEnd = new CountDownLatch(1);

        testDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.child("books").getChildren()) {
                    if(data.child("title").getValue(String.class).equals("testBook") ||
                            data.child("title").getValue(String.class).equals("")) {
                        data.getRef().removeValue();
                    }
                }

                for(DataSnapshot data : dataSnapshot.child("users").getChildren()) {
                    if(data.child("email").getValue(String.class).equals("unit@test")) {
                        data.getRef().removeValue();
                    }
                }
                lockEnd.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lockEnd.await();

    }
}