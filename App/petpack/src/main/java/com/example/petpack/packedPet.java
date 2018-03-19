package com.example.petpack;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
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
    private int priColor;
    private int secColor;
    private int tertColor;
    private String name;
    private int age;
    private Date creationDate,birthDate;
    private Uri generatedBitmapURI;
    //save file
    private String compiledSaveData;
    private Context context;


    //creation function
    public packedPet(Context context, String name, boolean save) {
        this.context=context;
        this.name=name;
        compileData();
        if (save) {savePet();}
    }




    //TODO compile all set pet data
    //iguess ! is a delimeter now
    // dont ever use invidivual peices, just decompile entire thing with decompileData(compiled save data)
    // 1: name
    // 2: creation date
    // 3: birth date
    // 4: age
    // 5: primary color
    // 6: secondary color
    // 7: tertiary color
    // 8: bitmap URIs
    //
    private void compileData() {

    compiledSaveData=name+"!"+creationDate.toString()+"!"+birthDate.toString()+"!"+age+"!"+priColor+"!"+secColor+"!"+tertColor+"!"+generatedBitmapURI+"!";
    }
    private void decompileDate(String compiledData) {
        int pri,sec,tert;
        String[] tokens = compiledData.split("!");
        name=tokens[0];
        //TODO PARSE DATE
        //creationDate=tokens[1];
        //birthDate=tokens[2];
        age=Integer.valueOf(tokens[3]);
        //todo parse color ints
        priColor= Color.parseColor(tokens[4]);

        secColor=Color.parseColor(tokens[5]);
        tertColor=Integer.valueOf(tokens[6]);
        name=tokens[7];

    }


    public boolean savePet(){
        return writeToFile(compiledSaveData, context);
    }
    //TODO load/delete functions
    //to save a pet to file
    private boolean writeToFile(String data,Context context) {
        String errmsg="";
        File path = context.getExternalFilesDir(null);
        File file = new File(path, name+"pet.pak");
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            errmsg="File open failed: " + e.toString();
            Log.e("Exception",errmsg );
            return false;
        }
        try {
            stream.write(data.getBytes());
        }
        catch (IOException e) {
            errmsg="File write failed: " + e.toString();
            Log.e("Exception",errmsg );
            return false;
        }
        finally {
            try {
                stream.close();
            }
            catch (IOException e) {
                errmsg="File close failed: " + e.toString();
                Log.e("Exception",errmsg );
                return false;
            }
        }
        Log.e("filepath",path.toString());
        return true;

    }

    public int getPriColor() {
        return priColor;
    }

    public void setPriColor(int priColor) {
        this.priColor = priColor;
    }

    public int getSecColor() {
        return secColor;
    }

    public void setSecColor(int secColor) {
        this.secColor = secColor;
    }

    public int getTertColor() {
        return tertColor;
    }

    public void setTertColor(int tertColor) {
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
