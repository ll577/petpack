package com.example.petpack;

import android.app.DatePickerDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.michaelevans.colorart.library.ColorArt;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.argb;
//todo this is hella messy
public class createPet extends Activity {
    private static Context context;
    //this static var is letting us pass various 'intents' to the on activity result listener
    //well use switch statements so we only need one override function
    //this is just a random number, it only needs to be unique and a constant
    //named for clarity
    private static final int RESULT_LOAD_IMAGE = 217;
    private static Bitmap petPhoto;
    private Bitmap processedBmp;
    Calendar myCalendar = Calendar.getInstance();
    EditText dateBox;


    //petstuff
    ColorArt primaryColors;
    int priColor = 0;
    int secColor =0;


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
        dateBox = findViewById(R.id.birthEdit);


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


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(createPet.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateBox.setText(sdf.format(myCalendar.getTime()));
    }
    private void OnLoadImageButtonClick() {
        pickImage();
    }






    private boolean OnCreatePetButtonClick(){

        //todo sanitize inputs, pull all data from fields, make pretty
        //todo pull the image to pet object, and also save
        //todo figure out why error message is white text
        String name = parseName();

        Date birthdate=parseDate();
        if (name.equals("")) {
            return false;
        }
        if (birthdate.equals(new Date())) {
            return false;
        }

        packedPet pet = new packedPet(context, name);
        pet.setBirthDate(birthdate);
        return true;

    }

    private String parseName(){
        EditText mEdit;
        mEdit = (EditText) findViewById(R.id.nameEdit);
        String name=mEdit.getText().toString();
        if (name.equals("")) {
            mEdit.setError("ayy dont do dis");
            return "";
        }
        return name;}

    private Date parseDate(){
        EditText mEdit;
        Date birthdate;
        mEdit = (EditText) findViewById(R.id.birthEdit);
        try {
            birthdate = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH).parse(mEdit.getText().toString());
        } catch (ParseException e) {
            mEdit.setError( "First name is required!" );
            return new Date();
        }
        return birthdate;
    }







    //feed picasso to get a bmp back
    Target loadPicassoImage = new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from) {createPet.setPetPhoto(bitmap);}
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            Log.d("err", "onBitmapFailed: ");
        }
    };


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
        //reduce memory footprint
        petTemplateIM=null;


        //now we have a photo to feed wherever

        //extract the colors

        // get the colors
        //https://github.com/MichaelEvans/ColorArt
//            colorArt.getBackgroundColor()
//            colorArt.getPrimaryColor()
//            colorArt.getSecondaryColor()
//            colorArt.getDetailColor()


        if (petPhoto != null) {
            primaryColors = new ColorArt(petPhoto);
            priColor=primaryColors.getBackgroundColor();
            if (priColor==Color.BLACK) {
                priColor=primaryColors.getDetailColor();
            }
            secColor=primaryColors.getPrimaryColor();
            if (secColor==Color.BLACK) {
                secColor=primaryColors.getSecondaryColor();
            }

        }
        processedBmp = processBitmap(petTemplate,priColor,secColor);
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
        for(int i = 0; i < allpixels.length; i++){
            if (allpixels[i]==-1) {
            }
            //color is stored as a weird int, we set a specific color in our template, and replace all instances of that
            //color with whatever we pull from submitted photo
            else if(allpixels[i] == -64980){
                //02-19 20:56:43.735 23189-23189/com.example.petpack D/AYYY: processBitmap: -16777216>-65536
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
    //pick image leads into this
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            //todo log something probs
            return;
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            loadImage(data);

        }
    }


}

