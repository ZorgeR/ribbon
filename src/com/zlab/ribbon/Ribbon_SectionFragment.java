package com.zlab.ribbon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Ribbon_SectionFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    static int pageID;

    public static ListView ribbon_list;
    public static Ribbon_ListElemetsAdaptor adaptor;
    public static List<Ribbon_Elements> ribbon_listelements;

    public Ribbon_SectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pageID = getArguments().getInt(ARG_SECTION_NUMBER);

        View rootView = inflater.inflate(R.layout.ribbon_page, container, false);
        //TextView pageIDtext = (TextView) rootView.findViewById(R.id.section_label);

        if(pageID == Ribbon_Main.TAB_RIBBON){
            ribbon_list = (ListView) rootView.findViewById(R.id.listRow);
            ribbon_list.setDividerHeight(0);

            if(ribbon_listelements!=null){
                adaptor = new Ribbon_ListElemetsAdaptor(Ribbon_Main.mContext,R.layout.ribbon_elements,ribbon_listelements);
                ribbon_list.setAdapter(adaptor);
            } else {
                //new twitter_requestToken(new Ribbon_Worker_Twitter()).execute();
                /* TODO Пустой список */
            }

            //pageIDtext.setText("RIBBON HERE!");
        } else if (pageID == Ribbon_Main.TAB_USER){
            //pageIDtext.setText("USER HERE!");
        } else if (pageID == Ribbon_Main.TAB_NETWORK){
            //pageIDtext.setText("NETWORK HERE!");
        }

        return rootView;
    }
}

