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
import android.widget.TextView;

/**
 * Created by Hugo on 12/14/2014.
 */
public class SaveFragment extends Fragment {



    EditText inputbox;
    Button save;
    Button cancel;
    TextView status,Display;
    String Quote;
    int index;
    DataBase dataBank;

    MainMenu mainmenu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.savescreen, container, false);
        status=(TextView)v.findViewById(R.id.SaveTitle);
        inputbox = (EditText) v.findViewById(R.id.SaveTag);
        Display=(TextView)v.findViewById(R.id.Display);
        dataBank=new DataBase(getActivity(),"Quotes",null,1);
        this.Quote=getArguments().getString("q");
        this.index=getArguments().getInt("i");


        save = (Button) v.findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            //This should save the Tag and add it to a database//
            public void onClick(View v) {

                if(Quote == null || inputbox.getText()==null) {
                    status.setText("Fail");

                }

                else{
                    if(CreateTag()){
                        status.setText("success");
                        Display.setText( dataBank.getQuote(inputbox.getText().toString()));

                       // getActivity().onBackPressed();
                    }else{
                        status.setText("fail");


                    }

                    }
                }



        });


        cancel = (Button) v.findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            // return to main activity. or main fragment//
            public void onClick(View v) {
                if(dataBank.removeQuote(inputbox.getText().toString())){
                    status.setText("success");

                }else{
                getActivity().onBackPressed();}

            }


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




    }

