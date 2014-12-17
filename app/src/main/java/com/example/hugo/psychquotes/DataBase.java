package com.example.hugo.psychquotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Hugo on 12/15/2014.
 */
public class DataBase extends SQLiteOpenHelper {
     private static final int version =1;
     private static final String DatabaseName ="Quotes_db";



    // Constructor

    public DataBase(Context context,String name,SQLiteDatabase.CursorFactory factory ,int version){
            super(context,DatabaseName,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String table1 = " CREATE TABLE Quotes( Tag TEXT PRIMARY KEY , Quote Text , Type TEXT);";
        db.execSQL(table1);


        // use db.execSQL(string) to execute a sql statement, to create tables



        //Create Table Statements//
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1,int arg2){
       return;
}



    //add methods + get query methods //


    public boolean addQuote ( String tag, String quote, String type ) {
        ContentValues values = new ContentValues();
        values.put("Tag",tag);
        values.put("Quote",quote);
        values.put("Type",type);

        SQLiteDatabase db= this.getWritableDatabase();

        try {
            db.insertOrThrow("Quotes", null, values);
        }
        catch(SQLiteConstraintException e){
            return false;}

        db.close();
        return true;




    }



    public boolean removeQuote(String tag){
        String query =" SELECT * FROM Quotes WHERE Quotes.Tag=?" +tag+"'";
        SQLiteDatabase db=this.getWritableDatabase();


        String[] args=new String[]{tag};
        return db.delete("Quotes","Tag=?",args) >0;
    }


     public String getQuote(String tag){

        String output="";
        String query = " Select Quotes.Quote FROM Quotes WHERE Quotes.Tag ='"+tag+"'";
        int column=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor Cursor = db.rawQuery(query,null);
         if(Cursor.moveToFirst()){
            column= Cursor.getColumnIndex("Quote");
             output=Cursor.getString(column);
             Cursor.close();
             db.close();
             return output;

         }

        else{ return null;}

    }


    public Cursor FetchAllSaved(){
        String query = "SELECT Tag as _id FROM Quotes";
        ArrayList<String> allquotes = new ArrayList<String>();
        Cursor out;
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query,null);
        out=cursor;
       // int i=0;
       // while(cursor.moveToNext()){
          //  i=cursor.getColumnIndex("Quote");
           // allquotes.add(cursor.getString(i));
       // }

         return out;
    }


}

