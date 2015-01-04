package com.example.hugo.psychquotes;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hugo on 12/26/2014.
 */
public class StatsPage  extends Fragment{
    String url;
    TextView DailyL;
    TextView LoveL;
    TextView InsL;
    TextView MoL;
    String  D;
    String  L;
    String I;
    String M;

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        //new Loadlikes().execute();
        new Loadlikes().execute();
        url ="http://psych2go.org/php/getlikes.php/";
        View view =inflater.inflate(R.layout.statspage,container,false);
        DailyL= (TextView)view.findViewById(R.id.dailyL);
        DailyL.setText(D);
        LoveL= (TextView)view.findViewById(R.id.LoveL);
        LoveL.setText(L);
        InsL= (TextView)view.findViewById(R.id.IL);
        InsL.setText(I);
        MoL= (TextView)view.findViewById(R.id.ML);
        MoL.setText(M);

        return view;


    }


    public class Loadlikes extends AsyncTask<Void,Void,Boolean> {


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected Boolean doInBackground(Void... arg0) {

            // ArrayList  QuoteList = new ArrayList<HashMap<String,String>>();
            //call json parser//

            // Fail here means no internet connection//
            try {
                JSONRead reader = new JSONRead();

                JSONObject json = reader.getJSONFromUrl(url);
                if(reader.getStatus()== false){

                    return true;
                }


               JSONArray Jsonarray = json.getJSONArray("posts");




                for (int i = 0; i < Jsonarray.length(); i++) {
                    JSONObject c = Jsonarray.getJSONObject(i);

                    D=c.getString("DailyL");
                    L=c.getString("LoveL");
                   M=c.getString("MotivationL");
                   I=c.getString("InspirationL");



                    /// HashMap<String, String> map = new HashMap<String, String>();

                    // map.put("quote", quotes[0]);
                    //QuoteList.add(map);
                }
                Log.d("dsdfsd",D);
                Log.d("dsdfsd",L);
                Log.d("dsdfsd",M);
                Log.d("dsdfsd",I);


            } catch (JSONException e) {

                return null;
            }



            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        DailyL.setText(D);
        LoveL.setText(L);
        InsL.setText(I);
        MoL.setText(M);
    }


        }

    }

   /* @Override
    public void onResume(){
        super.onResume();

        updateViews();

    }*/


