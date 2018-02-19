package com.example.petpack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.michaelevans.colorart.library.ColorArt;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.argb;

public class createPet extends Activity {
    private static Context context;
    //this static var is letting us pass various 'intents' to the on activity result listener
    //well use switch statements so we only need one override function
    //this is just a random number, it only needs to be unique and a constant
    //named for clarity
    private static final int RESULT_LOAD_IMAGE = 217;
    private static Bitmap petPhoto;
    ColorArt primaryColors;

    public static Bitmap getPetPhoto() {
        return petPhoto;
    }

    public static void setPetPhoto(Bitmap petPhoto) {
        createPet.petPhoto = petPhoto;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);
        this.context = getApplicationContext();


        //
        //
        //Buttons
        //
        //
        final Button createPetButton = findViewById(R.id.createThePet);
        createPetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnCreatePetButtonClick();
            }
        });
        final ImageButton loadImageButton = findViewById(R.id.loadImage);
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnLoadImageButtonClick();
            }
        });















    }

    private void OnLoadImageButtonClick() {
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
    private void OnCreatePetButtonClick(){
        EditText mEdit;
        //todo sanitize inputs, pull all data from fields, make pretty
        mEdit = (EditText) findViewById(R.id.NameEdit);
        String name = mEdit.getText().toString();
        mEdit = (EditText) findViewById(R.id.ageEdit);
        String temp=mEdit.getText().toString();
        if (!temp.equals("")) {
            int age = Integer.parseInt(temp);
        }
        mEdit = (EditText) findViewById(R.id.ageEdit);
        try {
            Date birthdate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(mEdit.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        packedPet pet = new packedPet(context, name);
    }








    //feed picasso to get a bmp back
    Target loadPicassoImage = new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from) {
                            /* Save the bitmap or do something with it here */
            createPet.setPetPhoto(bitmap);

            //Set it in the ImageView
        }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
        }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            Log.d("err", "onBitmapFailed: ");
        }
    };
    //TODO this is broken
    public void loadImage(Intent data) {
        //get the file URI from data
        Uri imageUri = data.getData();
            //petPhoto=  MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        Picasso.with(this)
                .load(imageUri)
                .resize(1000, 1000)
                .into(loadPicassoImage);


        Bitmap petTemplateIM = BitmapFactory.decodeResource(getResources(), R.drawable.template_cat_1);
        //required to make it editable
        Bitmap petTemplate = petTemplateIM.copy(Bitmap.Config.ARGB_8888, true);



        //now we have a photo to feed wherever

        //extract the colors

        // get the colors
        //https://github.com/MichaelEvans/ColorArt
//            colorArt.getBackgroundColor()
//            colorArt.getPrimaryColor()
//            colorArt.getSecondaryColor()
//            colorArt.getDetailColor()

        int priColor = 0;
        int secColor =0;
        //TODO  //if bitmap is too large color picking crashes— use picasso library
        if (petPhoto != null) {
            primaryColors = new ColorArt(petPhoto);
            priColor=primaryColors.getBackgroundColor();
            secColor=primaryColors.getPrimaryColor();
        }

        Log.d("LMAO", "processBitmap: "+priColor+">"+secColor+">"+Color.RED);
        Bitmap processedBmp = processBitmap(petTemplate,priColor,secColor);
        ImageButton mImg = findViewById(R.id.loadImage);
        try {
            mImg.setImageBitmap(processedBmp);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private Bitmap processBitmap(Bitmap petTemplate, int priColor, int secColor) {
        int [] allpixels = new int [petTemplate.getHeight() * petTemplate.getWidth()];
        petTemplate.getPixels(allpixels, 0, petTemplate.getWidth(), 0, 0, petTemplate.getWidth(), petTemplate.getHeight());
        Log.d(TAG, "processBitmap: "+priColor+">"+secColor+">"+Color.RED);
        for(int i = 0; i < allpixels.length; i++){
            if(allpixels[i] == Color.RED){
                allpixels[i] = priColor;
            } else if(allpixels[i] == Color.GREEN){
                allpixels[i] = secColor;
            }
        }
        petTemplate.setPixels(allpixels,0,petTemplate.getWidth(),0, 0, petTemplate.getWidth(),petTemplate.getHeight());
        return petTemplate;
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

