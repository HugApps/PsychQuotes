package com.example.hugo.psychquotes;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;



public class MainMenu extends Activity {

    Spinner Menu;
    FragmentManager manager=null;
    FragmentTransaction trans=null;




    // TextView quote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        manager = getFragmentManager();
       trans= manager.beginTransaction();
        //URL for database php file//
        Menu=(Spinner)findViewById(R.id.Menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.DropMenu, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Menu.setAdapter(adapter);
        Menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  TextView itemvalue =(TextView)view.getItemAtPosition(position);

                switch (position) {

                    case 0:
                            popStacks();
                            break;

                    case 1:
                           popStacks();





                            Fragment Main = new MainMenuFragment();
                            final FragmentTransaction main =manager.beginTransaction();
                            main.addToBackStack("Main");
                            main.replace(R.id.MainFrag2,Main,"Main");

                           main.commit();


                        break;

                    case 2:
                        popStacks();

                        Fragment Load = new LoadPage();
                        final FragmentTransaction load = manager.beginTransaction();
                        load.addToBackStack("Load");
                        load.replace(R.id.MainFrag2,Load,"Load");


                        load.commit();
                        break;


                    case 4:
                        popStacks();
                        final FragmentTransaction likes = manager.beginTransaction();
                        likes.addToBackStack("stats");

                        likes.replace(R.id.MainFrag2,new StatsPage(),"stats");

                        likes.commit();

                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;

            }
        });}


        protected void popStacks(){


        int size= manager.getBackStackEntryCount();

            for(int i =0;i<=size;i++){
                manager.popBackStack();

            }
        }




       /* // quote =(TextView)findViewById(R.id.QuoteDisplay);
        //Sliding interface setupp//
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







       /* class mydetector extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onDown(MotionEvent e){
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e){
                if(checkConnection()){
                switcher.setText(quotes[index]);
                switcher.setBackgroundColor(Color.LTGRAY);
                temp=(TextView)switcher.getChildAt(0);
                temp.setTextColor(Color.DKGRAY);
                keepable=true;
                }

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

                switcher.setText(covers.get(index));
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
                            switcher.setText(covers.get(index));
                            switcher.setBackgroundColor(Color.DKGRAY);
                            temp=(TextView)switcher.getChildAt(0);
                            temp.setTextColor(Color.WHITE);






                    }


                return true;

            }








            public boolean onDoubleTap(MotionEvent e){
                if(!keepable){return false;}
                final FragmentManager saveFrag= getFragmentManager();
                final FragmentTransaction fragmentTransder = saveFrag.beginTransaction();
                Fragment save = new SaveFragment();
                save.setArguments(SaveQuote());
                fragmentTransder.add(R.id.Frag,save,"save");
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



         t.setTextColor(Color.WHITE);
         t.setTextSize(50);





        return t;


    }




   /* public class LoadQuotes extends AsyncTask<Void,Void,Boolean> {


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


                if (json.getJSONArray("posts") != null) {
                    Jsonarray = json.getJSONArray("posts");
                } else {

                    return false;
                }


                for (int i = 0; i < Jsonarray.length(); i++) {
                    JSONObject c = Jsonarray.getJSONObject(i);
                    quotes[0] = c.getString("Daily");
                    quotes[1] = c.getString("Love");
                    quotes[2] = c.getString("Motivation");
                    quotes[3] = c.getString("Inspiration");
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

/*public Boolean checkConnection(){
    NetworkInfo WifiInfo =ConnectManager.getNetworkInfo(ConnectManager.TYPE_WIFI);
    NetworkInfo DataInfo =ConnectManager.getNetworkInfo(ConnectManager.TYPE_MOBILE);

    return WifiInfo.isConnected()|| DataInfo.isConnected();*/














 /*   public void ReturnToMain(){
        final FragmentManager frag= getFragmentManager();
        final FragmentTransaction fragmentTrans2 = frag.beginTransaction();

        fragmentTrans2.detach(frag.findFragmentByTag("save"));
        fragmentTrans2.commit();
    }*/

    }
