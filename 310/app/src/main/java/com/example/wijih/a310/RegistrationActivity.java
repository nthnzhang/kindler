package com.example.wijih.a310;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wijih.a310.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private Button mRegister;
    private EditText mEmail, mUsername, mNumber, mPassword;

    private User currentUser;
    private DatabaseReference mDatabase;
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // will be revisited when improving security
//        mAuth = FirebaseAuth.getInstance();
//        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {
//                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//            }
//        };


        mRegister = (Button) findViewById(R.id.register);

        mEmail = (EditText) findViewById(R.id.email);
        mUsername = (EditText) findViewById(R.id.username);
        mNumber = (EditText) findViewById(R.id.number);
        mPassword = (EditText) findViewById(R.id.password);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String username = mUsername.getText().toString();
                final String number = mNumber.getText().toString();
                final String password = mPassword.getText().toString();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // make sure email has not been used already
                        for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            if(userSnapshot.child("email").getValue(String.class).equals(email)) {
                                // email has already been used
                                Toast.makeText(RegistrationActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        // email has not been used, so create account
                        // automatically adds user into db
                        currentUser = new User(username, email, password, number, new ArrayList<String>(),
                                new ArrayList<String>(), 0.0, 0.0, new HashMap<String, List<String>>());

                        // go to swiping UI + pass along intent
                        Intent intent = new Intent(RegistrationActivity.this, SwipingActivity.class);
                        intent.putExtra("current_user", currentUser);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // will be revisited
//                mAuth.createUser (email, username, number, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(!task.isSuccessful()) {
//                            Toast.makeText(RegistrationActivity.this, “sign up error”, Toast.LENGTH)Short).show();)
//                        }
//                    }
//                });
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(firebaseAuthStateListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mAuth.removeAuthStateListener(firebaseAuthStateListener);
//    }
}
