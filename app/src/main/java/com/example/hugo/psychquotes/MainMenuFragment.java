package com.example.hugo.psychquotes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hugo on 12/18/2014.
 */
public class MainMenuFragment extends Fragment  implements ViewSwitcher.ViewFactory {
   // GestureDetector listen;
   // TextSwitcher switcher;
    ConnectivityManager ConnectManager;
    GestureDetector listen;
    Spinner Menu;
    String[] quotes;
    ArrayList<String> covers;
    String[] tags;
    int index;
    String Tag;
    boolean keepable = false;
    LoadQuotes daily;
    TextView temp;
    TextSwitcher switcher;
    View v;
    String url;
    private JSONArray Jsonarray;
    final FragmentManager manager =getFragmentManager();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.mainmenu, container, false);
        //Array to store sub category types of Quotes//

        quotes = new String[4];
        tags= new String[4];
        //Drop down menu set up//
        url="http://psych2go.org/php/getquote.php";
        covers = new ArrayList<String>();
        covers.add("Need Quotes?");
        covers.add("Need Advice?");
        covers.add("Need the Extra push?");
        covers.add("Need Inspiration?");
        ConnectManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        switcher = (TextSwitcher) v.findViewById(R.id.DailyQuote);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
                                @Override
                                public View makeView() {
                                    TextView t = new TextView(getActivity());
                                    t.setLayoutParams(new TextSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));


                                    t.setTextColor(Color.BLACK);
                                    t.setTextSize(50);


                                    return t;

                                }
                            });
        switcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
        switcher.setCurrentText("PsychQuotes: Swipe to get new quotes" + '\n' + "Double Tab to SaveQuote, Press  the heart to like");
        listen = new GestureDetector(getActivity(), new mydetector());
        //  switcher.addView(quote);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return listen.onTouchEvent(event);
            }
        });

        return v;
    }

    class mydetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            saveAble();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (checkConnection()) {
                switcher.setText(quotes[index]);
                switcher.setBackgroundColor(Color.WHITE);
                temp = (TextView) switcher.getChildAt(0);
                temp.setTextColor(Color.DKGRAY);


                final FragmentManager saveFrag = getFragmentManager();
                final FragmentTransaction fragmentTransder = saveFrag.beginTransaction();
                Fragment save = new SaveFragment();
                save.setArguments(SaveQuote());

                fragmentTransder.add(R.id.Frag, save, "save");
                fragmentTransder.addToBackStack("save");
                fragmentTransder.commit();
                keepable=true;

            }

        }

        @Override
        // detect horizitonal fling, change text according to index
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // if swipe left, decrement index and load value

            FragmentManager man = getFragmentManager();
            if(man.getBackStackEntryCount()>1){
                man.popBackStack();
            }
            if (e2.getX() < e1.getX()) {
                index--;
                if (index < 0) {
                    index = quotes.length - 1;
                    return true;
                }

                switcher.setText(covers.get(index));
                switcher.setBackgroundColor(Color.WHITE);
                temp = (TextView) switcher.getChildAt(0);
                temp.setTextColor(Color.BLACK);
            }


            if (e2.getX() > e1.getX()) {
                index++;
                if (index > quotes.length - 1) {
                    index = 0;

                }
                switcher.setText(covers.get(index));
                switcher.setBackgroundColor(Color.WHITE);
                temp = (TextView) switcher.getChildAt(0);
                temp.setTextColor(Color.BLACK);


            }


            return true;

        }


        public boolean onDoubleTap(MotionEvent e) {

           /* final FragmentManager saveFrag = getFragmentManager();
            final FragmentTransaction fragmentTransder = saveFrag.beginTransaction();
            Fragment save = new SaveFragment();
            save.setArguments(SaveQuote());
            fragmentTransder.add(R.id.Frag, save, "save");
            fragmentTransder.addToBackStack("save");
            fragmentTransder.commit();*/
            return true;
        }


    }


    public Bundle SaveQuote() {
        String output = quotes[index];
        int type = index;

        Bundle bundle = new Bundle();
        bundle.putString("tag",tags[index]);
        bundle.putString("q", output);
        bundle.putInt("i", type);
        return bundle;


    }




    @Override
    public View makeView() {
        TextView t = new TextView(getActivity());
        t.setLayoutParams(new TextSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));


        t.setTextColor(Color.BLACK);
        t.setTextSize(50);


        return t;


    }

    public class LoadQuotes extends AsyncTask<Void,Void,Boolean> {


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


                Jsonarray = json.getJSONArray("posts");



                tags= new String[4];
                for (int i = 0; i < Jsonarray.length(); i++) {
                    JSONObject c = Jsonarray.getJSONObject(i);
                    quotes[0] = c.getString("Daily");
                    tags[0]= c.getString("DailyT");

                    quotes[1] = c.getString("Love");
                    tags[1]= c.getString("LoveT");
                    quotes[2] = c.getString("Motivation");
                    tags[2]=c.getString("MoT");
                    quotes[3] = c.getString("Inspiration");
                    tags[3]=c.getString("IT");
                    // HashMap<String, String> map = new HashMap<String, String>();

                    // map.put("quote", quotes[0]);
                    //QuoteList.add(map);
                }

            } catch (JSONException e) {

                return null;
            }



            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //update displays//


        }

    }

    public Boolean checkConnection(){
        NetworkInfo WifiInfo =ConnectManager.getNetworkInfo(ConnectManager.TYPE_WIFI);
        NetworkInfo DataInfo =ConnectManager.getNetworkInfo(ConnectManager.TYPE_MOBILE);

        return WifiInfo.isConnected()|| DataInfo.isConnected();





    }


    public void saveAble(){
        TextView test =(TextView)switcher.getChildAt(0);
        for( int i =0;i<quotes.length-1;i++){
            if(quotes[i]==test.getText().toString()){
                keepable=false;
                return;
            }

            }
            keepable=true;
        }



    @Override
    public void onResume(){
        super.onResume();
        if(checkConnection()){
            new LoadQuotes().execute();}
    }


}
