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


    //creation function
    public packedPet(Context context, String name) {
        this.context=context;
        this.name=name;
        compileData();
        savePet();
    }

    //TODO compile
    private void compileData() {
        compiledSaveData=name+"!";
    }
    private void savePet(){
        writeToFile(compiledSaveData, context);
    }
    //TODO load/delete functions
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

    public Color getPriColor() {
        return priColor;
    }

    public void setPriColor(Color priColor) {
        this.priColor = priColor;
    }

    public Color getSecColor() {
        return secColor;
    }

    public void setSecColor(Color secColor) {
        this.secColor = secColor;
    }

    public Color getTertColor() {
        return tertColor;
    }

    public void setTertColor(Color tertColor) {
        this.tertColor = tertColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Uri getGeneratedBitmapURI() {
        return generatedBitmapURI;
    }

}
