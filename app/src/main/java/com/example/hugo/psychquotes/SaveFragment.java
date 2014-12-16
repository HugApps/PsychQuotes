package com.example.hugo.psychquotes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Hugo on 12/14/2014.
 */
public class SaveFragment extends Fragment {



    EditText inputbox;
    Button save;
    Button cancel;
    String Quote;
    int index;
    DataBase dataBank;

    MainMenu mainmenu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.savescreen, container, false);
        inputbox = (EditText) v.findViewById(R.id.SaveTag);
        dataBank=new DataBase(getActivity(),"Quotes",null,1);
       this.Quote=getArguments().getString("q");
        this.index=getArguments().getInt("i");


        save = (Button) v.findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            //This should save the Tag and add it to a database//
            public void onClick(View v) {
                if(Quote == null || inputbox.getText()==null){
                 //say saved failed
                getActivity().onBackPressed();}

                else{
                    CreateTag();
                }


            }
        });


        cancel = (Button) v.findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            // return to main activity. or main fragment//
            public void onClick(View v) {

                getActivity().onBackPressed();

            }


        });
        return inflater.inflate(R.layout.savescreen, container, false);


    }


    public  void CreateTag (){
        String type="";
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

        dataBank.addQuote(inputbox.getText().toString(),Quote,type);
        dataBank.close();
    }
       // someSQL lite?




    }
