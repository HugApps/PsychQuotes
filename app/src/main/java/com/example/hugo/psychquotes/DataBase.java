package com.example.hugo.psychquotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.close();

        // use db.execSQL(string) to execute a sql statement, to create tables



        //Create Table Statements//
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1,int arg2){
       return;
}



    //add methods + get query methods //


    public void addQuote ( String tag, String quote, String type ) {
        ContentValues values = new ContentValues();
        values.put("Tag",tag);
        values.put("Quote",quote);
        values.put("Type",type);

        SQLiteDatabase db= this.getWritableDatabase();
        db.insert("Quotes",null,values);
        db.close();




    }



    public boolean removeQuote(String tag){
        String query =" SELECT * FROM Quotes WHERE" + tag+"=Quotes.Tag;";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor Cursor = db.rawQuery(query,null);
        if(Cursor.moveToFirst()){
            db.delete("Quotes",tag+"=Quotes.Tag",null);
            Cursor.close();

            db.close();
            return true;
        }
        else{
            return false;}


    }


     public String getQuote(String tag){

        String output="";
        String query = " Select Quotes.Quote FROM Quotes WHERE" + tag + "=Quotes.Tag;";
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


    public ArrayList<String> FetchAllSaved(){
        String query = "SELECT Quotes.Tag , Quotes.Quote FROM Quotes";
        ArrayList<String> allquotes = new ArrayList<String>();

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query,null);
        int i=0;
        while(cursor.moveToNext()){
            i=cursor.getColumnIndex("Quote");
            allquotes.add(cursor.getString(i));
        }
         cursor.close();
         db.close();
         return allquotes;
    }


}

