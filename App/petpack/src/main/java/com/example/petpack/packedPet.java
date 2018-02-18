package com.example.petpack;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created by Alex on 2/17/2018.
 * pet object, probably important
 */

public class packedPet {
    private Color priColor,secColor,tertColor;
    private String name;
    private int age;
    private Date creationDate,birthDate;
    private Uri generatedBitmapURI;
    //save file
    private String compiledSaveData;
    private Context context;


    public packedPet(Context context, String petData) {
        this.context=context;
        name=petData;
        compileData();
        savePet();
    }


    private void compileData() {
        compiledSaveData=name+"!";
    }
    private void savePet(){
        writeToFile(compiledSaveData, context);
    }
    //to save a pet to file
    private void writeToFile(String data,Context context) {
        File path = context.getExternalFilesDir(null);
        File file = new File(path, name+"pet.pak");
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.e("Exception", "File open failed: " + e.toString());
        }
        try {
            stream.write(data.getBytes());
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        finally {
            try {
                stream.close();
            }
            catch (IOException e) {
                Log.e("Exception", "File close failed: " + e.toString());
            }
        }
        Log.e("filepath",path.toString());

    }



}
