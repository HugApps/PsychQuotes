package com.example.hugo.psychquotes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainMenu extends Activity implements ViewSwitcher.ViewFactory {
    GestureDetector listen;
    GestureDetector listen2;
    String[] quotes ;
    int index;
    String Tag;
    LoadQuotes daily;
    TextView temp;
    TextSwitcher switcher ;
    View v;
    String url;
    private JSONArray Jsonarray;

   // TextView quote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu2);

        url="http://psych2go.org/php/getquote.php";
        quotes = new String[4];


        // quote =(TextView)findViewById(R.id.QuoteDisplay);
        switcher = (TextSwitcher) findViewById(R.id.DailyQuote);
        v = switcher;
        switcher.setFactory(this);

        switcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        switcher.setCurrentText("PsychQuotes: Swipe to get new quotes" +'\n' +"Double Tab to SaveQuote, Press  the heart to like");
        listen = new GestureDetector ( this,new mydetector());
        //  switcher.addView(quote);
        v.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return listen.onTouchEvent(event);
            }
        });




        // index will be saved to restore last quote viewed//
        index = 0;
    }







        class mydetector extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDown(MotionEvent e){
                return true;
            }

            @Override
            // detect horizitonal fling, change text according to index
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // if swipe left, decrement index and load value

                if (e2.getX() < e1.getX() ) {
                index--;
                if(index<0){
                    index=quotes.length-1;
                    return true;
                }

                switcher.setText(quotes[0]);
                switcher.setBackgroundColor(Color.DKGRAY);
                temp=(TextView)switcher.getChildAt(0);
                temp.setTextColor(Color.WHITE);
                }





                if (e2.getX() > e1.getX()) {
                        index++;
                        if(index>quotes.length-1) {
                            index = 0;
                            return true;
                        }
                            switcher.setText(quotes[index]);
                            switcher.setBackgroundColor(Color.WHITE);
                             temp=(TextView)switcher.getChildAt(0);
                             temp.setTextColor(Color.BLACK);






                    }


                return true;

            }


            public boolean onDoubleTap(MotionEvent e){


                final FragmentManager saveFrag= getFragmentManager();
                final FragmentTransaction fragmentTransder = saveFrag.beginTransaction();
                Fragment save = new SaveFragment();
                save.setArguments(SaveQuote());
                fragmentTransder.add(R.id.container,save,"save");
                fragmentTransder.addToBackStack("save");
                fragmentTransder.commit();
                return true;
            }


        }




public Bundle SaveQuote (){
    String output= quotes[index];
    int type = index;

    Bundle bundle = new Bundle();
    bundle.putString("q",output);
    bundle.putInt("i",type);
    return bundle;
    //SaveFragment FragToSend= (SaveFragment)getFragmentManager().findFragmentByTag("save");
    //FragToSend.getQuote(output);

    // Call Fragment function to create string/tag pair//




}


    //@Override
  // public boolean onTouchEvent(MotionEvent event){

        //return listen.onTouchEvent(event);
   //}




    @Override
    public View  makeView(){
            TextView t = new TextView(this);
            t.setLayoutParams(new TextSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));



         t.setTextColor(Color.BLACK);
         t.setTextSize(50);




        return t;


    }




    public class LoadQuotes extends AsyncTask<Void,Void,Boolean> {


        @Override
        protected  void onPreExecute(){}



        @Override
          protected Boolean doInBackground(Void... arg0){

         // ArrayList  QuoteList = new ArrayList<HashMap<String,String>>();
                //call json parser//

           JSONRead reader = new JSONRead();

           JSONObject json =reader.getJSONFromUrl(url);

           try {
               Jsonarray = json.getJSONArray("posts");




               for (int i= 0; i < Jsonarray.length(); i++) {
                   JSONObject c = Jsonarray.getJSONObject(i);
                   quotes[0] = c.getString("Daily");
                   quotes[1]= c.getString("Love");
                   quotes[2]= c.getString("Motivation");
                   quotes[3]= c.getString("Inspiration");
                  // HashMap<String, String> map = new HashMap<String, String>();

                  // map.put("quote", quotes[0]);
                   //QuoteList.add(map);
               }

           }catch (JSONException e){
                e.printStackTrace();
           }





            return null;

            }
        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            //update displays//




            }




    }


    @Override
    protected void onResume(){
        super.onResume();
        new LoadQuotes().execute();
    }


    public void ReturnToMain(){
        final FragmentManager frag= getFragmentManager();
        final FragmentTransaction fragmentTrans2 = frag.beginTransaction();

        fragmentTrans2.detach(frag.findFragmentByTag("save"));
        fragmentTrans2.commit();
    }

}
