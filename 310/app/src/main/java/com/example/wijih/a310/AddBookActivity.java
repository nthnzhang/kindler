package com.example.wijih.a310;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wijih.a310.model.Book;
import com.example.wijih.a310.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private User currentUser;
    private EditText bookTitle, bookDescription, bookTags;
    private boolean checked, forSale = false;
    private Button add;
    private RadioButton radioChoice;
    private RadioGroup exchangeOrSale;
    private String tagString;
    private Button uploadImage;
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private Bitmap bm;
    private ImageView imgThumbnail;
    private String encodedImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final Intent currUserIntent = getIntent();
        currentUser = currUserIntent.getParcelableExtra("current_user");

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

        addBook();
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



    public void addBook () {
        bookTitle = (EditText) findViewById(R.id.bookTitle);
        bookDescription = (EditText) findViewById(R.id.bookDescription);
        add = (Button) findViewById(R.id.addBook);
        exchangeOrSale = (RadioGroup) findViewById(R.id.exchangeOrSale);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTags = (EditText) findViewById(R.id.bookTags);
                tagString = (String) bookTags.getText().toString();
                Log.d("string test", tagString);

                if (currentUser != null && !bookTitle.getText().toString().trim().equals("") && !bookDescription.getText().toString().trim().equals("") && !bookTags.getText().toString().trim().equals("")) {

                    //split the tags by commas

                    List<String> tags = Arrays.asList(tagString.split(","));
                    Log.d("tags", tags.get(0));

                    int selected = exchangeOrSale.getCheckedRadioButtonId();

                    radioChoice = (RadioButton) findViewById(selected);
                    if (radioChoice.getText() == "Sale") {
                        forSale = true;
                    }
                    else {
                        forSale = false;
                    }



                    //encode the picture
                    if (bm != null) {
                        encodedImg = encodeAndSaveImg(bm);
                        System.out.println("img: " + encodedImg);
                    }



                    // add book
                    Book newBook = new Book(bookTitle.getText().toString(), bookDescription.getText().toString(),
                            currentUser.getUserID(), forSale, tags);
                    if (encodedImg != null) {
                        newBook.addImageString(encodedImg);
                    }
                    currentUser.addBook(newBook);


                    Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                    intent.putExtra("current_user", currentUser);
                    startActivity(intent);
                }
            }
        });


    }

    public String encodeAndSaveImg(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}
