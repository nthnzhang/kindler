package com.example.wijih.a310;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import com.example.wijih.a310.model.User;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import android.util.Base64;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.Toast;

        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.InputStream;
        import java.util.Arrays;
        import java.util.List;

public class EditBookActivity extends AppCompatActivity {
    private Button saveChanges;
    private EditText newTitle, newDescription, newTags;
    private User currentUser;
    private RadioGroup newExchangeOrSale;
    private RadioButton radioChoice;
    private boolean forSale = false;
    private String tagString;
    private ImageView imgThumbnail;
    private Button uploadImage;
    private Button deleteImage;
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private Bitmap bm;
    private String encodedImg;


    private DatabaseReference mDatabase;
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);


        final Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        currentUser = intent.getParcelableExtra("current_user");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("books").child(bookId);

        imgThumbnail = (ImageView) findViewById(R.id.imgThumbnail);

        uploadImage = (Button) findViewById(R.id.UploadPhoto);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use implicit intent to get to image gallery
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);

                //this is where to find the data
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();

                //URI
                Uri data = Uri.parse(pictureDirectoryPath);

                //set data and type
                galleryIntent.setDataAndType(data, "image/*");

                startActivityForResult(galleryIntent, IMAGE_GALLERY_REQUEST);
            }
        });


        //Remove this book
        deleteImage = (Button) findViewById(R.id.deleteBook);
        deleteImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mDatabase.removeValue();

                Intent intent = new Intent(EditBookActivity.this, ProfileActivity.class);
                intent.putExtra("current_user", currentUser);
                startActivity(intent);

            }
        });


        editProfile();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            //in here everything processed okay
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                //in here, we are hearing from gallery

                //address of image on SD
                Uri imgUri = data.getData();

                //declare stream to read img data from SD
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imgUri);

                    bm = BitmapFactory.decodeStream(inputStream);

                    imgThumbnail.setImageBitmap(bm);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }




    public void editProfile() {
        saveChanges = (Button) findViewById(R.id.saveChanges);
        newTitle = (EditText) findViewById(R.id.titleEdit);
        newDescription = (EditText) findViewById(R.id.descriptionEdit);
        newExchangeOrSale = (RadioGroup) findViewById(R.id.editExchangeOrSale);


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTags = (EditText) findViewById(R.id.tagsEdit);
                tagString = (String) newTags.getText().toString();



                if (currentUser != null) {

                    //edit exchangeOrSale
                    int selected = newExchangeOrSale.getCheckedRadioButtonId();
                    radioChoice = (RadioButton) findViewById(selected);
                    if (radioChoice.getText().equals("Sale")) {
                        System.out.println("In here");
                        forSale = true;
                    }
                    else {
                        forSale = false;
                    }
                    mDatabase.child("forSale").setValue(forSale);





                    //edit title
                    if (!newTitle.getText().toString().trim().equals("")) {
                        //mAuth.child("kindler-edfdb").child(bookId).child("title").setValue(newTitle.getText().toString().trim());
                        mDatabase.child("title").setValue(newTitle.getText().toString().trim());
                    }


                    //edit description
                    if (!newDescription.getText().toString().trim().equals("")) {
                        //mAuth.child("kindler-edfdb").child(bookId).child("username").setValue(newDescription.getText().toString().trim());
                        mDatabase.child("description").setValue(newDescription.getText().toString().trim());
                    }


                    //edit tags
                    if (!newTags.getText().toString().trim().equals("")) {
                        String num;
                        List<String> tags = Arrays.asList(tagString.split(","));
                        mDatabase.child("tags").removeValue();

                        for (int i = 0; i < tags.size(); i++) {
                            num = Integer.toString(i);
                            mDatabase.child("tags").child(num).setValue(tags.get(i));
                        }
                    }

                    //edit photo
                    if (bm != null) {
                        encodedImg = encodeAndSaveImg(bm);
                        System.out.println("img: " + encodedImg);
                    }
                    if (encodedImg != null) {
                        mDatabase.child("imageString").setValue(encodedImg);
                    }
                }

                Intent intent = new Intent(EditBookActivity.this, ProfileActivity.class);
                intent.putExtra("current_user", currentUser);
                startActivity(intent);
            }
        });
    }

    public String encodeAndSaveImg(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}
