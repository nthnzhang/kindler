package com.example.wijih.a310;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditProfileActivity extends AppCompatActivity {

    private Button saveChanges;
    private EditText newUsername, newEmail, newPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editProfile();

    }

    public void editProfile() {
        saveChanges = (Button) findViewById(R.id.saveChanges);
        newUsername = (EditText) findViewById(R.id.usernameEdit);
        newEmail = (EditText) findViewById(R.id.emailEdit);
        newPhone = (EditText) findViewById(R.id.phoneEdit);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && !newEmail.getText().toString().trim().equals("")) {
                    mAuth.child("kindler-edfdb").child(user.getId()).child("username").setValue(newEmail.getText().toString().trim());
                }


                //get the way to update username
                if (user != null && !newUsername.getText().toString().trim().equals("")) {
                    mAuth.child("kindler-edfdb").child(user.getId()).child("username").setValue(newUsername.getText().toString().trim());
                }


                //get the way to update Phone number
                if (user != null && !newPhone.getText().toString().trim().equals("")) {
                    mAuth.child("kindler-edfdb").child(user.getId()).child("username").setValue(newPhone.getText().toString().trim());
                }
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });
    }
}
