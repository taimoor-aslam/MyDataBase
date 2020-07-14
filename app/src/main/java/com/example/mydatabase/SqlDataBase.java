package com.example.mydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SqlDataBase extends SQLiteOpenHelper {
    private static String DB_NAME="Student Information Management System";
    private static String TABLE_NAME="studentInfo";
    private static Integer DB_VERSION=11;
    private static String COLUMN_ID="id";
    private static String ST_NAME="student_name";
    private static String ST_AGE="student_age";
    private static String ST_EMAIL="email";
    private static String ST_PASSCODE="password";
    private static String ST_HOBBIES="hobbies";
    private static String ST_IMAGE="img";
    private static String ST_SEX="sex";
    SQLiteDatabase db;
    public SqlDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + ST_NAME + " TEXT,"
                        + ST_AGE + " TEXT,"
                        + ST_EMAIL + " TEXT,"
                        + ST_PASSCODE + " TEXT,"
                        + ST_HOBBIES + " TEXT,"
                        + ST_IMAGE + " BLOB,"
                        + ST_SEX + " TEXT"
                        + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long insert(String name, String age, String email, String passcode, String hobbies, String sex, Bitmap photo)
    {
        db=this.getWritableDatabase();
        byte [] img=getByteArray(photo);
        ContentValues cv=new ContentValues();
        cv.put(ST_NAME,name);
        cv.put(ST_AGE,age);
        cv.put(ST_EMAIL,email);
        cv.put(ST_PASSCODE,passcode);
        cv.put(ST_HOBBIES,hobbies);
        cv.put(ST_SEX,sex);
        cv.put(ST_IMAGE,img);
        long id=db.insert(TABLE_NAME,null,cv);
        db.close();
        return id;
    }
    public ArrayList<MyListData> show()
    {
        db=this.getReadableDatabase();
        Cursor c=db.query(TABLE_NAME,new String [] {ST_NAME,ST_AGE,ST_EMAIL,ST_PASSCODE,ST_HOBBIES,ST_SEX,ST_IMAGE},
                null,null,null,null,null);
        int firstIndex=c.getColumnIndex(ST_NAME);
        int secondIndex=c.getColumnIndex(ST_AGE);
        int thirdIndex=c.getColumnIndex(ST_EMAIL);
        int fourthIndex=c.getColumnIndex(ST_PASSCODE);
        int fifthIndex=c.getColumnIndex(ST_HOBBIES);
        int sixthIndex=c.getColumnIndex(ST_SEX);
        int seventhIndex=c.getColumnIndex(ST_IMAGE);
        ArrayList<MyListData> listData=new ArrayList<>();
        MyListData data;
       // String result="";
      //  String name;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
//         result+=c.getString(firstIndex)+" "+c.getString(secondIndex)+" "+c.getString(thirdIndex)+
//                 " "+ c.getString(fourthIndex)+" "+c.getString(fifthIndex)+
//                 " "+c.getString(sixthIndex) +"\n";
////            name=c.getColumnName(firstIndex);
////            result.add(name);
            String mName=c.getString(firstIndex);
            String  mAge=c.getString(secondIndex);
            String mEmail=c.getString(thirdIndex);
            String mPasscode=c.getString(fourthIndex);
            String mHobbies=c.getString(fifthIndex);
            String mGender=c.getString(sixthIndex);
            byte[] mPhoto=c.getBlob(seventhIndex);
            Bitmap photo=getImage(mPhoto);
            data=new MyListData(mName,mAge,mEmail,mPasscode,mHobbies,mGender,photo);
            listData.add(data);
        }
        return listData;
    }
    public boolean verify(String email,String passcode)
    {
        db=this.getReadableDatabase();
        Cursor c=db.query(TABLE_NAME,new String[]{ST_EMAIL,ST_PASSCODE},
                ST_EMAIL + "=? AND " +ST_PASSCODE + "=?",new String[] {email,passcode},
                null,null,null,null);
        if(c!=null && c.moveToFirst())
        {
            return true;
        }
        return false;
    }
    // to store image in DB
    public static byte[] getByteArray(Bitmap photo)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    //to retrieve image from DB
    public static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }
    public String getRecord(String email)
    {
       // db=getReadableDatabase();
        db=this.getReadableDatabase();
        String result="";
        Cursor c=db.query(TABLE_NAME,new String [] {ST_NAME,ST_AGE,ST_EMAIL,ST_HOBBIES,ST_SEX},
                ST_EMAIL + "=?",new String[]{email},null,null,null,null);
        if(c!=null&&c.moveToFirst())
        {
            int firstIndex=c.getColumnIndex(ST_NAME);
            int secondIndex=c.getColumnIndex(ST_AGE);
            int thirdIndex=c.getColumnIndex(ST_EMAIL);
            int fifthIndex=c.getColumnIndex(ST_HOBBIES);
            int sixthIndex=c.getColumnIndex(ST_SEX);
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
            {
                String mName = c.getString(firstIndex);
                String mAge = c.getString(secondIndex);
                String mEmail = c.getString(thirdIndex);
                String mHobbies = c.getString(fifthIndex);
                String mGender = c.getString(sixthIndex);
                result="Name: "+mName+"\n\n"+"Age: "+mAge+"\n\n"+"Email: "+mEmail+"\n\n" +
                        ""+"Hobbies: "+mHobbies+"\n\n"+"Gender: "+mGender+"\n";
                return result;
            }
        }
        return result;
    }
    public Bitmap getUserImage(String Email)
    {
      //  db=getReadableDatabase();
        db=this.getReadableDatabase();
        Cursor c=db.query(TABLE_NAME,new String [] {ST_IMAGE},
                ST_EMAIL + "=?",new String[]{Email},null,null,null,null);
        if(c!=null&&c.moveToFirst())
        {
            int colIndex=c.getColumnIndex(ST_IMAGE);
            byte[] image=c.getBlob(colIndex);
            Bitmap photo=getImage(image);
            return photo;
        }
        return null;
    }
}
