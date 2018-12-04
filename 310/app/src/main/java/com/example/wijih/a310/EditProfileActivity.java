package com.example.wijih.a310;

import android.content.Intent;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wijih.a310.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditProfileActivity extends AppCompatActivity {

    private Button saveChanges;
    private EditText newUsername, newEmail, newPhone, newPassword;
    private User currentUser;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");


        editProfile();

    }

    public void editProfile() {
        saveChanges = (Button) findViewById(R.id.saveChanges);
        newUsername = (EditText) findViewById(R.id.usernameEdit);
        newEmail = (EditText) findViewById(R.id.emailEdit);
        newPhone = (EditText) findViewById(R.id.phoneEdit);
        newPassword = (EditText) findViewById(R.id.passwordEdit);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserID());

                if (currentUser != null && !newEmail.getText().toString().trim().equals("")) {
//                    mAuth.child("kindler-edfdb").child(currentUser.getId()).child("username").setValue(newEmail.getText().toString().trim());
                    currentUser.setEmail(newEmail.getText().toString().trim());
                    mDatabase.child("email").setValue(newEmail.getText().toString().trim());
                }


                //get the way to update username
                if (currentUser != null && !newUsername.getText().toString().trim().equals("")) {
//                    mAuth.child("kindler-edfdb").child(currentUser.getId()).child("username").setValue(newUsername.getText().toString().trim());
                    currentUser.setUsername(newUsername.getText().toString().trim());
                    mDatabase.child("username").setValue(newUsername.getText().toString().trim());
                }


                //get the way to update Phone number
                if (currentUser != null && !newPhone.getText().toString().trim().equals("")) {
//                    mAuth.child("kindler-edfdb").child(currentUser.getId()).child("username").setValue(newPhone.getText().toString().trim());
                    currentUser.setPhone(newPhone.getText().toString().trim());
                    mDatabase.child("phone").setValue(newPhone.getText().toString().trim());
                }

                //get the way to update password
                if (currentUser != null && !newPassword.getText().toString().trim().equals("")) {
//                    mAuth.child("kindler-edfdb").child(currentUser.getId()).child("username").setValue(newPhone.getText().toString().trim());
                    currentUser.setPassword(newPhone.getText().toString().trim());
                    mDatabase.child("password").setValue(newPassword.getText().toString().trim());
                }

//                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.putExtra("current_user", currentUser);
                startActivity(intent);
            }
        });
    }
}
