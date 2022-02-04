package com.example.doforyou;


//https://www.youtube.com/watch?v=312RhjfetP8
//for the reference

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME ="doForYou.db";
    private static final int DATABASE_VERSION= 1;

    private static final String TABLE_NAME= "my_library";
    private static final String COLUMN_ID= "ID";

    private static final String COLUMN_FIRSTNAME= "first_name";
    private static final String COLUMN_LASTNAME= "last_name";


    //Create the constructor for this sqliteopernhelper
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Fist time generate the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =
                "CREATE TABLE "+ TABLE_NAME +
                        " (" + COLUMN_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_FIRSTNAME+ " TEXT, " +
                        COLUMN_LASTNAME + " TEXT); ";
        sqLiteDatabase.execSQL(query);

        //create users id and password for each column
        sqLiteDatabase.execSQL("create Table users(username TEXT primary key, password TEXT) ");

    }



    //This is called if the database version number changes. It prevents users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVersion) {
        sqLiteDatabase.execSQL("drop Table if exists users");
    }


    //Check if the contentvalue has been inserted into the database 04/022022
    public Boolean insertData(String username, String password){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", username);
        contentValues.put("password", password);
        long result = myDB.insert("users",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addOne(User customer){

        //crud
        SQLiteDatabase db  = this.getWritableDatabase();
        //like intent, passing values (a pair) to assoicate with it, one to the other
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_FIRSTNAME,customer.getFirstName());
        cv.put(COLUMN_LASTNAME,customer.getLastName());

        //Add to the database
        long insert = db.insert(TABLE_NAME, null, cv);

        if (insert==-1){
            return false;

        }else {
        return true;
        }
    }

    public boolean DeleteOne(User customer){
        // find customer in the database. if it found, delete it and return true.
        //

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME+ " WHERE " + COLUMN_ID+ " = "+ customer.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public List<User> getEveryone(){
        List<User> returnList = new ArrayList<>();

        //Get data from the database
        String queryingString = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        //cursor is result set
        Cursor cursor = db.rawQuery(queryingString,null);

        //Move to the first item in the cursor set if there is
        if (cursor.moveToFirst()){
            //Loop
            do {
                int customerID = cursor.getInt(0);
                String customerFirstName = cursor.getString(1);
                String customerLastName = cursor.getString(2);

                User newCustomer = new User(customerID,customerFirstName, customerLastName);
                returnList.add(newCustomer);

            }while (cursor.moveToNext());
            }

        else {

            //Failed, don't add anything

        }

        // close both the cursor and db
        cursor.close();
        db.close();
        return returnList;

    }

}
