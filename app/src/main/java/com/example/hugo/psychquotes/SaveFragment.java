package com.example.hugo.psychquotes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 12/14/2014.
 */
public class SaveFragment extends Fragment {



    EditText inputbox;
    Button save;
    Button cancel;
    ImageButton LikeButton;
    TextView status;
    String Quote,tag;

    int index;
    String url = "http://psych2go.org/php/likes.php";
    DataBase dataBank;
    JSONRead reader;

    MainMenu mainmenu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.savescreen, container, false);
        status=(TextView)v.findViewById(R.id.SaveTitle);
        inputbox = (EditText) v.findViewById(R.id.SaveTag);

        dataBank=new DataBase(getActivity(),"Quotes",null,1);


        this.tag=getArguments().getString("tag");
        this.Quote=getArguments().getString("q");
        this.index=getArguments().getInt("i");
        tag=getArguments().getString("tag");


        save = (Button) v.findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            //This should save the Tag and add it to a database//
            public void onClick(View v) {

                if(Quote == null || inputbox.getText().toString().length()<1) {
                    status.setText("Fail");

                }

                else{
                    if(CreateTag()){
                        status.setText("success");


                       // getActivity().onBackPressed();
                    }else{
                        status.setText("fail");


                    }

                    }
                }



        });

        LikeButton = (ImageButton)v.findViewById(R.id.LikeButton);
        LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  LikeQuotes().execute();
                getActivity().onBackPressed();

            }
        });
        cancel = (Button) v.findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            // return to main activity. or main fragment//
            public void onClick(View v) {



                getActivity().onBackPressed();}




        });
        return v;


    }


    public  boolean CreateTag (){
        String type="";
        boolean success=true;
        switch(index){

            case 0 : type="Success";
                     break;
            case 1: type="Inspiration";
                    break;

            case 2: type="Love";
                    break;
            case 3: type="Motivation";
                    break;
        }

        success= dataBank.addQuote(inputbox.getText().toString(),Quote,type);

        return success;
    }
       // someSQL lite?



    public class LikeQuotes extends AsyncTask<Void,Void,Boolean> {


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected Boolean doInBackground(Void... arg0) {

            List<NameValuePair> params= new ArrayList<NameValuePair>();
            params=getType();

            reader=new JSONRead();
            JSONObject json =reader.makeHttpRequest(url,"POST",params);
            try {
                int success = json.getInt("success");
                if (success == 1) {
                    return null;
                } else {
                    Log.d("Like failure", json.getString("message"));
                }
            } catch(JSONException e){
                    e.printStackTrace();
            }







            // ArrayList  QuoteList = new ArrayList<HashMap<String,String>>();
            //call json parser//

            // Fail here means no internet connection//

                    // HashMap<String, String> map = new HashMap<String, String>();

                    // map.put("quote", quotes[0]);
                    //QuoteList.add(map);





            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //update displays//


        }

    }

    private List<NameValuePair> getType(){
        List<NameValuePair> params= new ArrayList<NameValuePair>();
        int lastchar = tag.length()-1;
        char type = tag.charAt(lastchar);
        String t="";
        BasicNameValuePair tagPair= new BasicNameValuePair("Tag",tag);
        BasicNameValuePair typePair=null;

        switch(type){

            case 'd' :
                       typePair= new BasicNameValuePair("Type","DailyQuote");
                       break;
            case 'l':
                       typePair= new BasicNameValuePair("Type","Love");
                       break;

            case 'm':
                       typePair=new BasicNameValuePair("Type","Motivation");
                       break;


            case 'i':
                        typePair=new BasicNameValuePair("Type","Ins");




                }

            if(typePair==null){return null;}

                params.add(typePair);

                params.add(tagPair);

        return params;

    }


    }

