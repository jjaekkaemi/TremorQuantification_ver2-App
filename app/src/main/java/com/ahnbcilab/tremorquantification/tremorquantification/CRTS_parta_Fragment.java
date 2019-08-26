package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.Adapters.TaskListViewAdapter;
import com.ahnbcilab.tremorquantification.data.DoctorData;
import com.ahnbcilab.tremorquantification.data.TaskItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class CRTS_parta_Fragment extends Fragment {

    int c1_1, c1_2, c1_3;
    int c2_1, c2_2, c2_3;
    int c3_1, c3_2, c3_3;
    int c4_1, c4_2, c4_3;
    int c5_1, c5_2, c5_3;
    int c6_1, c6_2, c6_3;
    int c7_1, c7_2, c7_3;
    int c8_1, c8_2, c8_3;
    int c9_1, c9_2, c9_3;
    int c10_1, c10_2, c10_3;

    boolean bool = true;

    RadioButton crts1_1_0, crts1_1_1, crts1_1_2, crts1_1_3, crts1_1_4;
    RadioButton crts1_2_0, crts1_2_1, crts1_2_2, crts1_2_3, crts1_2_4;
    RadioButton crts1_3_0, crts1_3_1, crts1_3_2, crts1_3_3, crts1_3_4;

    RadioButton crts2_1_0, crts2_1_1, crts2_1_2, crts2_1_3, crts2_1_4;
    RadioButton crts2_2_0, crts2_2_1, crts2_2_2, crts2_2_3, crts2_2_4;
    RadioButton crts2_3_0, crts2_3_1, crts2_3_2, crts2_3_3, crts2_3_4;

    RadioButton crts3_1_0, crts3_1_1, crts3_1_2, crts3_1_3, crts3_1_4;
    RadioButton crts3_2_0, crts3_2_1, crts3_2_2, crts3_2_3, crts3_2_4;
    RadioButton crts3_3_0, crts3_3_1, crts3_3_2, crts3_3_3, crts3_3_4;

    RadioButton crts4_1_0, crts4_1_1, crts4_1_2, crts4_1_3, crts4_1_4;
    RadioButton crts4_2_0, crts4_2_1, crts4_2_2, crts4_2_3, crts4_2_4;
    RadioButton crts4_3_0, crts4_3_1, crts4_3_2, crts4_3_3, crts4_3_4;

    RadioButton crts5_1_0, crts5_1_1, crts5_1_2, crts5_1_3, crts5_1_4;
    RadioButton crts5_2_0, crts5_2_1, crts5_2_2, crts5_2_3, crts5_2_4;
    RadioButton crts5_3_0, crts5_3_1, crts5_3_2, crts5_3_3, crts5_3_4;

    RadioButton crts6_1_0, crts6_1_1, crts6_1_2, crts6_1_3, crts6_1_4;
    RadioButton crts6_2_0, crts6_2_1, crts6_2_2, crts6_2_3, crts6_2_4;
    RadioButton crts6_3_0, crts6_3_1, crts6_3_2, crts6_3_3, crts6_3_4;

    RadioButton crts7_1_0, crts7_1_1, crts7_1_2, crts7_1_3, crts7_1_4;
    RadioButton crts7_2_0, crts7_2_1, crts7_2_2, crts7_2_3, crts7_2_4;
    RadioButton crts7_3_0, crts7_3_1, crts7_3_2, crts7_3_3, crts7_3_4;

    RadioButton crts8_1_0, crts8_1_1, crts8_1_2, crts8_1_3, crts8_1_4;
    RadioButton crts8_2_0, crts8_2_1, crts8_2_2, crts8_2_3, crts8_2_4;
    RadioButton crts8_3_0, crts8_3_1, crts8_3_2, crts8_3_3, crts8_3_4;

    RadioButton crts9_1_0, crts9_1_1, crts9_1_2, crts9_1_3, crts9_1_4;
    RadioButton crts9_2_0, crts9_2_1, crts9_2_2, crts9_2_3, crts9_2_4;
    RadioButton crts9_3_0, crts9_3_1, crts9_3_2, crts9_3_3, crts9_3_4;

    RadioButton crts10_1_0, crts10_1_1, crts10_1_2, crts10_1_3, crts10_1_4;
    RadioButton crts10_2_0, crts10_2_1, crts10_2_2, crts10_2_3, crts10_2_4;
    RadioButton crts10_3_0, crts10_3_1, crts10_3_2, crts10_3_3, crts10_3_4;

    String Clinic_ID, PatientName;

    String crts_num;

    Button pre_button, next_button ;
    TextView page_text ;





    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.crts_parta_fragment, container, false);

        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
        }

        Log.v("crts_num", "partA fragment");

        pre_button = (Button) view.findViewById(R.id.preButton) ;
        pre_button.setVisibility(View.GONE);
        next_button = (Button) view.findViewById(R.id.nextButton) ;
        page_text = (TextView) view.findViewById(R.id.page_text) ;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity)getActivity()).frgment1();
            }
        });

        TextView tv = (TextView)getActivity().findViewById(R.id.crts_title);
        tv.setText("CRTS. A");

        Log.v("crts_num", "partA fragment2");

         crts1_1_0 = (RadioButton)view.findViewById(R.id.crts1_1_0);
         crts1_1_1 = (RadioButton)view.findViewById(R.id.crts1_1_1);
         crts1_1_2 = (RadioButton)view.findViewById(R.id.crts1_1_2);
         crts1_1_3 = (RadioButton)view.findViewById(R.id.crts1_1_3);
         crts1_1_4 = (RadioButton)view.findViewById(R.id.crts1_1_4);

         crts1_2_0 = (RadioButton)view.findViewById(R.id.crts1_2_0);
         crts1_2_1 = (RadioButton)view.findViewById(R.id.crts1_2_1);
         crts1_2_2 = (RadioButton)view.findViewById(R.id.crts1_2_2);
         crts1_2_3 = (RadioButton)view.findViewById(R.id.crts1_2_3);
         crts1_2_4 = (RadioButton)view.findViewById(R.id.crts1_2_4);

         crts1_3_0 = (RadioButton)view.findViewById(R.id.crts1_3_0);
         crts1_3_1 = (RadioButton)view.findViewById(R.id.crts1_3_1);
         crts1_3_2 = (RadioButton)view.findViewById(R.id.crts1_3_2);
         crts1_3_3 = (RadioButton)view.findViewById(R.id.crts1_3_3);
         crts1_3_4 = (RadioButton)view.findViewById(R.id.crts1_3_4);

         crts2_1_0 = (RadioButton)view.findViewById(R.id.crts2_1_0);
         crts2_1_1 = (RadioButton)view.findViewById(R.id.crts2_1_1);
         crts2_1_2 = (RadioButton)view.findViewById(R.id.crts2_1_2);
         crts2_1_3 = (RadioButton)view.findViewById(R.id.crts2_1_3);
         crts2_1_4 = (RadioButton)view.findViewById(R.id.crts2_1_4);

         crts2_2_0 = (RadioButton)view.findViewById(R.id.crts2_2_0);
         crts2_2_1 = (RadioButton)view.findViewById(R.id.crts2_2_1);
         crts2_2_2 = (RadioButton)view.findViewById(R.id.crts2_2_2);
         crts2_2_3 = (RadioButton)view.findViewById(R.id.crts2_2_3);
         crts2_2_4 = (RadioButton)view.findViewById(R.id.crts2_2_4);

         crts2_3_0 = (RadioButton)view.findViewById(R.id.crts2_3_0);
         crts2_3_1 = (RadioButton)view.findViewById(R.id.crts2_3_1);
         crts2_3_2 = (RadioButton)view.findViewById(R.id.crts2_3_2);
         crts2_3_3 = (RadioButton)view.findViewById(R.id.crts2_3_3);
         crts2_3_4 = (RadioButton)view.findViewById(R.id.crts2_3_4);

         crts3_1_0 = (RadioButton)view.findViewById(R.id.crts3_1_0);
         crts3_1_1 = (RadioButton)view.findViewById(R.id.crts3_1_1);
         crts3_1_2 = (RadioButton)view.findViewById(R.id.crts3_1_2);
         crts3_1_3 = (RadioButton)view.findViewById(R.id.crts3_1_3);
         crts3_1_4 = (RadioButton)view.findViewById(R.id.crts3_1_4);

         crts3_2_0 = (RadioButton)view.findViewById(R.id.crts3_2_0);
         crts3_2_1 = (RadioButton)view.findViewById(R.id.crts3_2_1);
         crts3_2_2 = (RadioButton)view.findViewById(R.id.crts3_2_2);
         crts3_2_3 = (RadioButton)view.findViewById(R.id.crts3_2_3);
         crts3_2_4 = (RadioButton)view.findViewById(R.id.crts3_2_4);

         crts3_3_0 = (RadioButton)view.findViewById(R.id.crts3_3_0);
         crts3_3_1 = (RadioButton)view.findViewById(R.id.crts3_3_1);
         crts3_3_2 = (RadioButton)view.findViewById(R.id.crts3_3_2);
         crts3_3_3 = (RadioButton)view.findViewById(R.id.crts3_3_3);
         crts3_3_4 = (RadioButton)view.findViewById(R.id.crts3_3_4);

         crts4_1_0 = (RadioButton)view.findViewById(R.id.crts4_1_0);
         crts4_1_1 = (RadioButton)view.findViewById(R.id.crts4_1_1);
         crts4_1_2 = (RadioButton)view.findViewById(R.id.crts4_1_2);
         crts4_1_3 = (RadioButton)view.findViewById(R.id.crts4_1_3);
         crts4_1_4 = (RadioButton)view.findViewById(R.id.crts4_1_4);

         crts4_2_0 = (RadioButton)view.findViewById(R.id.crts4_2_0);
         crts4_2_1 = (RadioButton)view.findViewById(R.id.crts4_2_1);
         crts4_2_2 = (RadioButton)view.findViewById(R.id.crts4_2_2);
         crts4_2_3 = (RadioButton)view.findViewById(R.id.crts4_2_3);
         crts4_2_4 = (RadioButton)view.findViewById(R.id.crts4_2_4);

         crts4_3_0 = (RadioButton)view.findViewById(R.id.crts4_3_0);
         crts4_3_1 = (RadioButton)view.findViewById(R.id.crts4_3_1);
         crts4_3_2 = (RadioButton)view.findViewById(R.id.crts4_3_2);
         crts4_3_3 = (RadioButton)view.findViewById(R.id.crts4_3_3);
         crts4_3_4 = (RadioButton)view.findViewById(R.id.crts4_3_4);

         crts5_1_0 = (RadioButton)view.findViewById(R.id.crts5_1_0);
         crts5_1_1 = (RadioButton)view.findViewById(R.id.crts5_1_1);
         crts5_1_2 = (RadioButton)view.findViewById(R.id.crts5_1_2);
         crts5_1_3 = (RadioButton)view.findViewById(R.id.crts5_1_3);
         crts5_1_4 = (RadioButton)view.findViewById(R.id.crts5_1_4);

         crts5_2_0 = (RadioButton)view.findViewById(R.id.crts5_2_0);
         crts5_2_1 = (RadioButton)view.findViewById(R.id.crts5_2_1);
         crts5_2_2 = (RadioButton)view.findViewById(R.id.crts5_2_2);
         crts5_2_3 = (RadioButton)view.findViewById(R.id.crts5_2_3);
         crts5_2_4 = (RadioButton)view.findViewById(R.id.crts5_2_4);

         crts5_3_0 = (RadioButton)view.findViewById(R.id.crts5_3_0);
         crts5_3_1 = (RadioButton)view.findViewById(R.id.crts5_3_1);
         crts5_3_2 = (RadioButton)view.findViewById(R.id.crts5_3_2);
         crts5_3_3 = (RadioButton)view.findViewById(R.id.crts5_3_3);
         crts5_3_4 = (RadioButton)view.findViewById(R.id.crts5_3_4);

         crts6_1_0 = (RadioButton)view.findViewById(R.id.crts6_1_0);
         crts6_1_1 = (RadioButton)view.findViewById(R.id.crts6_1_1);
         crts6_1_2 = (RadioButton)view.findViewById(R.id.crts6_1_2);
         crts6_1_3 = (RadioButton)view.findViewById(R.id.crts6_1_3);
         crts6_1_4 = (RadioButton)view.findViewById(R.id.crts6_1_4);

         crts6_2_0 = (RadioButton)view.findViewById(R.id.crts6_2_0);
         crts6_2_1 = (RadioButton)view.findViewById(R.id.crts6_2_1);
         crts6_2_2 = (RadioButton)view.findViewById(R.id.crts6_2_2);
         crts6_2_3 = (RadioButton)view.findViewById(R.id.crts6_2_3);
         crts6_2_4 = (RadioButton)view.findViewById(R.id.crts6_2_4);

         crts6_3_0 = (RadioButton)view.findViewById(R.id.crts6_3_0);
         crts6_3_1 = (RadioButton)view.findViewById(R.id.crts6_3_1);
         crts6_3_2 = (RadioButton)view.findViewById(R.id.crts6_3_2);
         crts6_3_3 = (RadioButton)view.findViewById(R.id.crts6_3_3);
         crts6_3_4 = (RadioButton)view.findViewById(R.id.crts6_3_4);

         crts7_1_0 = (RadioButton)view.findViewById(R.id.crts7_1_0);
         crts7_1_1 = (RadioButton)view.findViewById(R.id.crts7_1_1);
         crts7_1_2 = (RadioButton)view.findViewById(R.id.crts7_1_2);
         crts7_1_3 = (RadioButton)view.findViewById(R.id.crts7_1_3);
         crts7_1_4 = (RadioButton)view.findViewById(R.id.crts7_1_4);

         crts7_2_0 = (RadioButton)view.findViewById(R.id.crts7_2_0);
         crts7_2_1 = (RadioButton)view.findViewById(R.id.crts7_2_1);
         crts7_2_2 = (RadioButton)view.findViewById(R.id.crts7_2_2);
         crts7_2_3 = (RadioButton)view.findViewById(R.id.crts7_2_3);
         crts7_2_4 = (RadioButton)view.findViewById(R.id.crts7_2_4);

         crts7_3_0 = (RadioButton)view.findViewById(R.id.crts7_3_0);
         crts7_3_1 = (RadioButton)view.findViewById(R.id.crts7_3_1);
         crts7_3_2 = (RadioButton)view.findViewById(R.id.crts7_3_2);
         crts7_3_3 = (RadioButton)view.findViewById(R.id.crts7_3_3);
         crts7_3_4 = (RadioButton)view.findViewById(R.id.crts7_3_4);

         crts8_1_0 = (RadioButton)view.findViewById(R.id.crts8_1_0);
         crts8_1_1 = (RadioButton)view.findViewById(R.id.crts8_1_1);
         crts8_1_2 = (RadioButton)view.findViewById(R.id.crts8_1_2);
         crts8_1_3 = (RadioButton)view.findViewById(R.id.crts8_1_3);
         crts8_1_4 = (RadioButton)view.findViewById(R.id.crts8_1_4);

         crts8_2_0 = (RadioButton)view.findViewById(R.id.crts8_2_0);
         crts8_2_1 = (RadioButton)view.findViewById(R.id.crts8_2_1);
         crts8_2_2 = (RadioButton)view.findViewById(R.id.crts8_2_2);
         crts8_2_3 = (RadioButton)view.findViewById(R.id.crts8_2_3);
         crts8_2_4 = (RadioButton)view.findViewById(R.id.crts8_2_4);

         crts8_3_0 = (RadioButton)view.findViewById(R.id.crts8_3_0);
         crts8_3_1 = (RadioButton)view.findViewById(R.id.crts8_3_1);
         crts8_3_2 = (RadioButton)view.findViewById(R.id.crts8_3_2);
         crts8_3_3 = (RadioButton)view.findViewById(R.id.crts8_3_3);
         crts8_3_4 = (RadioButton)view.findViewById(R.id.crts8_3_4);

         crts9_1_0 = (RadioButton)view.findViewById(R.id.crts9_1_0);
         crts9_1_1 = (RadioButton)view.findViewById(R.id.crts9_1_1);
         crts9_1_2 = (RadioButton)view.findViewById(R.id.crts9_1_2);
         crts9_1_3 = (RadioButton)view.findViewById(R.id.crts9_1_3);
         crts9_1_4 = (RadioButton)view.findViewById(R.id.crts9_1_4);

         crts9_2_0 = (RadioButton)view.findViewById(R.id.crts9_2_0);
         crts9_2_1 = (RadioButton)view.findViewById(R.id.crts9_2_1);
         crts9_2_2 = (RadioButton)view.findViewById(R.id.crts9_2_2);
         crts9_2_3 = (RadioButton)view.findViewById(R.id.crts9_2_3);
         crts9_2_4 = (RadioButton)view.findViewById(R.id.crts9_2_4);

         crts9_3_0 = (RadioButton)view.findViewById(R.id.crts9_3_0);
         crts9_3_1 = (RadioButton)view.findViewById(R.id.crts9_3_1);
         crts9_3_2 = (RadioButton)view.findViewById(R.id.crts9_3_2);
         crts9_3_3 = (RadioButton)view.findViewById(R.id.crts9_3_3);
         crts9_3_4 = (RadioButton)view.findViewById(R.id.crts9_3_4);

         crts10_1_0 = (RadioButton)view.findViewById(R.id.crts10_1_0);
         crts10_1_1 = (RadioButton)view.findViewById(R.id.crts10_1_1);
         crts10_1_2 = (RadioButton)view.findViewById(R.id.crts10_1_2);
         crts10_1_3 = (RadioButton)view.findViewById(R.id.crts10_1_3);
         crts10_1_4 = (RadioButton)view.findViewById(R.id.crts10_1_4);

         crts10_2_0 = (RadioButton)view.findViewById(R.id.crts10_2_0);
         crts10_2_1 = (RadioButton)view.findViewById(R.id.crts10_2_1);
         crts10_2_2 = (RadioButton)view.findViewById(R.id.crts10_2_2);
         crts10_2_3 = (RadioButton)view.findViewById(R.id.crts10_2_3);
         crts10_2_4 = (RadioButton)view.findViewById(R.id.crts10_2_4);

         crts10_3_0 = (RadioButton)view.findViewById(R.id.crts10_3_0);
         crts10_3_1 = (RadioButton)view.findViewById(R.id.crts10_3_1);
         crts10_3_2 = (RadioButton)view.findViewById(R.id.crts10_3_2);
         crts10_3_3 = (RadioButton)view.findViewById(R.id.crts10_3_3);
         crts10_3_4 = (RadioButton)view.findViewById(R.id.crts10_3_4);


        return view;

    }

}