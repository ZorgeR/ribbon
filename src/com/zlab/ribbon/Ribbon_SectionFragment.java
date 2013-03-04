package com.zlab.ribbon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Ribbon_SectionFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    static int pageID;

    public Ribbon_SectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pageID = getArguments().getInt(ARG_SECTION_NUMBER);

        View rootView = inflater.inflate(R.layout.ribbon_page, container, false);
        TextView pageIDtext = (TextView) rootView.findViewById(R.id.section_label);

        if(pageID == Ribbon_Main.TAB_RIBBON){
            pageIDtext.setText("RIBBON HERE!");
        } else if (pageID == Ribbon_Main.TAB_USER){
            pageIDtext.setText("USER HERE!");
        } else if (pageID == Ribbon_Main.TAB_NETWORK){
            pageIDtext.setText("NETWORK HERE!");
        }

        return rootView;
    }
}

