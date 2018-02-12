package com.example.petpack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.michaelevans.colorart.library.ColorArt;

import java.io.IOException;

public class imgLoader extends AppCompatActivity {
    //this static var is letting us pass various 'intents' to the on activity result listener
    //well use switch statements so we only need one override function
    //this is just a random number, it only needs to be unique and a constant
    //named for clarity
    private static final int RESULT_LOAD_IMAGE = 217;
    Bitmap petPhoto;
    ColorArt primaryColors;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_loader);
        //called once on load, shoudl probably change this but whatevz
        //TODO move this to a button or something and design a layout
        pickImage();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
               loadImage(data);

        }
    }
    public void loadImage(Intent data) {
        //Get image set as imgview
        Uri imageUri = data.getData();


        try {
            petPhoto=MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView mImg = findViewById(R.id.loadedPic);
        Drawable drawable = new BitmapDrawable(getResources(),petPhoto);
        mImg.setImageDrawable(drawable);
        //now we have a photo to feed wherever

        //extract the colors

        // get the colors
        //https://github.com/MichaelEvans/ColorArt
//            colorArt.getBackgroundColor()
//            colorArt.getPrimaryColor()
//            colorArt.getSecondaryColor()
//            colorArt.getDetailColor()


        //TODO  //if bitmap is too large color picking crashes
        if (petPhoto != null) {
            primaryColors = new ColorArt(petPhoto);
            findViewById(R.id.backgroundColor).setBackgroundColor(primaryColors.getSecondaryColor());
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
