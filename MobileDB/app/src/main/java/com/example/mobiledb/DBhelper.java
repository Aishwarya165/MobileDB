
package com.example.mobiledb;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class DBhelper extends SQLiteOpenHelper {
    String AES="AES";
    public static final String DBNAME="mobiledb";



    public DBhelper(Context context) {
        super(context, "mobiledb", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase Mydb) {
        Mydb.execSQL("create table users(username TEXT primary key, password TEXT)");
        Mydb.execSQL("create table profile(username TEXT,address TEXT,age TEXT,dob TEXT,educational_qualification TEXT,sslc NUMBER,hsc NUMBER,ug NUMBER,pg NUMBER,FOREIGN KEY (username) REFERENCES users(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase Mydb, int oldversion,int newversion) {
        Mydb.execSQL("drop table if exists users");
        Mydb.execSQL("drop table if exists profile");

    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username", username);
        try {
            String en = encrypt(password);
            contentValues.put("password",en);
        }catch (Exception e){
            e.printStackTrace();
        }

        long results=Mydb.insert("users",null,contentValues);
        if (results==-1) {
            return false;
        }
        else{
            return true;
        }

    }

    private String encrypt(String password) throws Exception{
        SecretKeySpec key= generateKey(password);
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(password.getBytes());
        String encryptedValue= Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }



    public Boolean insertprofile(String user,String dobv,String agev,String addr,String eduq,String sslcm,String hscm,String ugm,String pgm){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",user);
        contentValues.put("dob",dobv);
        contentValues.put("age",agev);
        contentValues.put("address",addr);
        contentValues.put("educational_qualification",eduq);
        contentValues.put("sslc",sslcm);
        contentValues.put("hsc",hscm);
        contentValues.put("ug",ugm);
        contentValues.put("pg",pgm);

        long results=Mydb.insert("profile",null,contentValues);
        if (results==-1) {
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean checkusername(String username){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        Cursor cursor = Mydb.rawQuery("select * from users where username=?",new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username ,String password){
        SQLiteDatabase Mydb=this.getWritableDatabase();

        try {
            String en = encrypt(password);
            Cursor cursor = Mydb.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, en});
            if(cursor.getCount() > 0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


   public Boolean checkprofile(String username)
   {
       SQLiteDatabase Mydb=this.getWritableDatabase();
       Cursor cursor = Mydb.rawQuery("select * from profile where username=?",new String[]{username});
       if(cursor.getCount()>0)
           return true;
       else
           return false;
   }


}

