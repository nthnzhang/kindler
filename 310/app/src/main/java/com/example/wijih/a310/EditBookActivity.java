package com.example.wijih.a310;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;

        import java.util.Arrays;
        import java.util.List;

public class EditBookActivity extends AppCompatActivity {
    /*private Button saveChanges;
    private EditText newUsername, newEmail, newPhone;
    private User currentUser;
    private RadioGroup newExchangeOrSale;
    private RadioButton radioChoice;
    private boolean forSale = false;


    private DatabaseReference mDatabase;*/
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);


        final Intent positionIntent = getIntent();
        position = positionIntent.getIntExtra("position", 0);
        System.out.println("POSITON: " + position);
        //final Intent currUserIntent = getIntent();
        /*currentUser = currUserIntent.getParcelableExtra("current_user");


        editProfile();*/

    }

    /*public void editProfile() {
        saveChanges = (Button) findViewById(R.id.saveChanges);
        newTitle = (EditText) findViewById(R.id.titleEdit);
        newDescription = (EditText) findViewById(R.id.descriptionEdit);
        newTags = (EditText) findViewById(R.id.tagsEdit);
        newExchangeOrSale = (RadioGroup) findViewById(R.id.exchangeOrSale);
        tagString = (String) newTags.toString();

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mDatabase = FirebaseDatabase.getInstance().getReference().child("books").child("ADD BOOK ID IN HER PASSED FROM LAST PAGE);

                if (currentUser != null) {
                    int selected = exchangeOrSale.getCheckedRadioButtonId();
                    radioChoice = (RadioButton) findViewById(selected);

                    if (radioChoice.getText() == "Sale") {
                        forSale = true;
                    }
                    else {
                        forSale = false;
                    }

                    mDatabase.child("forSale").setValue(forSale);

                    if (!newTitle.getText().toString().trim().equals("")) {
//                      mAuth.child("kindler-edfdb").child("ADD BOOK ID IN HER PASSED FROM LAST PAGE).child("title").setValue(newTitle.getText().toString().trim());
                        mDatabase.child("title").setValue(newTitle.getText().toString().trim());
                    }


                    //get the way to update username
                    if (!newDescription.getText().toString().trim().equals("")) {
    //                  mAuth.child("kindler-edfdb").child("ADD BOOK ID IN HER PASSED FROM LAST PAGE).child("username").setValue(newDescription.getText().toString().trim());
                        mDatabase.child("description").setValue(newDescription.getText().toString().trim());
                    }


                    //get the way to update Phone number
                    if (!newTags.getText().toString().trim().equals("")) {
                        List<String> tags = Arrays.asList(tagString.split(","));
    //                  mAuth.child("kindler-edfdb").child("ADD BOOK ID IN HER PASSED FROM LAST PAGE).child("username").setValue(newPhone.getText().toString().trim());
                        mDatabase.child("forSale").setValue(newPhone.getText().toString().trim());
                    }
                }

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.putExtra("current_user", currentUser);
                startActivity(intent);
            }
        });
    }*/
}
