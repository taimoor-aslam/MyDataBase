package com.example.mydatabase;

import android.graphics.Bitmap;

public class MyListData {
    private String mName;
    private String mAge;
    private String mEmail;
    private String mPasscode;
    private String mHobbies;
    private String mGender;
    private Bitmap mPhoto;
    public MyListData(String mName,String mAge,String mEmail,String mPasscode,String mHobbies,String mGender,Bitmap mPhoto)
    {
        this.mName=mName;
        this.mAge=mAge;
        this.mEmail=mEmail;
        this.mPasscode=mPasscode;
        this.mHobbies=mHobbies;
        this.mGender=mGender;
        this.mPhoto=mPhoto;
    }
    public String getmName()
    {
        return mName;
    }
    public String getmAge()
    {
        return mAge;
    }
    public String getmEmail()
    {
        return mEmail;
    }
    public String getmPasscode()
    {
        return mPasscode;
    }
    public String getmHobbies()
    {
        return mHobbies;
    }
    public String getmGender()
    {
        return mGender;
    }
    public void setmPhoto(Bitmap mPhoto)
    {
        this.mPhoto=mPhoto;
    }
    public void setmName(String mName)
    {
        this.mName=mName;
    }
    public void setmAge(String mAge)
    {
        this.mAge=mAge;
    }
    public void setmEmail(String mEmail)
    {
        this.mEmail=mEmail;
    }
    public Bitmap getmPhoto()
    {
        return mPhoto;
    }
    public void setmPasscode(String mPasscode)
    {
        this.mPasscode=mPasscode;
    }
    public void setmHobbies(String mHobbies)
    {
        this.mHobbies=mHobbies;
    }
    public void setmGender(String mGender)
    {
        this.mGender=mGender;
    }

}
