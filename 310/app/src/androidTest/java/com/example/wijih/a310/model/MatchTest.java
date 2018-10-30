package com.example.wijih.a310.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class MatchTest {

    private Match testMatch;
    private DatabaseReference testDatabaseReference;

    private CountDownLatch lock = new CountDownLatch(1);


    @Before
    public void setUp() throws Exception {
        testMatch = new Match("1234", "5678");
        testMatch.setMatchId("-123456789");
        testDatabaseReference = FirebaseDatabase.getInstance().getReference();
        testDatabaseReference.child("matches").child("-123456789").setValue(testMatch);

        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }

    }

    @After
    public void tearDown() throws Exception {
        testDatabaseReference = FirebaseDatabase.getInstance().getReference();
        testDatabaseReference.child("matches").child(testMatch.getMatchId()).removeValue();
    }

    @Test
    public void testMatchCreation() {
        Match testMatchConstruct = new Match("1234", "5678");

        assertEquals("1234", testMatchConstruct.getUserId1());
        assertEquals("5678", testMatchConstruct.getUserId2());
        assertEquals(false, testMatchConstruct.isUser1Choice());
        assertEquals(false, testMatchConstruct.isUser2Choice());
    }

    @Test
    public void acceptMatchFromUser1() throws Exception {
        testMatch.acceptMatch("1234");

        testDatabaseReference.child("matches").child(testMatch.getMatchId()).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertEquals(true, dataSnapshot.child("user1Choice").getValue(Boolean.class));
                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lock.await();
    }

    @Test
    public void acceptMatchFromUser2() throws Exception {
        testMatch.acceptMatch("5678");

        testDatabaseReference.child("matches").child(testMatch.getMatchId()).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertEquals(true, dataSnapshot.child("user2Choice").getValue(Boolean.class));
                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lock.await();
    }
}