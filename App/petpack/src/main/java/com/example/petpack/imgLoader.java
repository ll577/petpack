package com.example.petpack;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class imgLoader extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 217;
    Bitmap petPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_loader);
        //called once on load, shoudl probably change this but whatevz
        pickImage();
        ImageView mImg;
        mImg = findViewById(R.id.background);
        mImg.setImageBitmap(petPhoto);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
                //Get image set as imgview
                ImageView mImg;
                mImg = (ImageView) findViewById(R.id.background);
                Uri imageUri = data.getData();
                mImg.setImageURI(imageUri);
                //now we have a photo to feed wherever
        }
    }
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //data to pass to gallery to return a photo with X parameters
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        //open gallery
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }
}
