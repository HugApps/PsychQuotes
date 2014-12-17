package com.example.hugo.psychquotes;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Hugo on 12/16/2014.
 */
public class LoadPage extends Fragment {

    DataBase data;
    ListView listOfTags;
    TextView Display;
    int[] listpositions;
    String[] columns;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.savedquote,container,false);
        listOfTags=(ListView)v.findViewById(R.id.list);
        Display=(TextView)v.findViewById(R.id.display);
        data= new DataBase(getActivity(),"Quotes",null,1);
        columns= new String[]{"_id"};
        listpositions= new int[]{R.id.itemtext};

        cursor=data.FetchAllSaved();

        adapter = new SimpleCursorAdapter(getActivity(),R.layout.item,cursor,columns,listpositions,	0);

        listOfTags.setAdapter(adapter);
        listOfTags.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // display the Quote on to the textview

                TextView text=(TextView)view.findViewById(R.id.itemtext);
                String output=data.getQuote(text.getText().toString());
            //   String output=data.getQuote((String)parent.getItemAtPosition(position));
                Display.setText(output);


            }
        });
        return v;


    }


}
