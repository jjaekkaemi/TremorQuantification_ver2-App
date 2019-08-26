package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ahnbcilab.tremorquantification.data.CRTS;
import com.ahnbcilab.tremorquantification.data.CRTS_Data;
import com.ahnbcilab.tremorquantification.data.CRTS_Score;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class CRTS_partc_Fragment extends Fragment {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databasePatientList;
    DatabaseReference databaseCRTS;

    String Clinic_ID, PatientName;
    String crts_num;

    Button pre_button, next_button ;

    CRTS_partb_Fragment frag2;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    boolean bool = true;

    int crts_taskno;

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

    int c11, c12, c13;
    int c14;
    int c15;

    int c16, c17, c18, c19;
    int c20, c21, c22, c23;

    RadioButton crts16_0, crts16_1, crts16_2, crts16_3, crts16_4;
    RadioButton crts17_0, crts17_1, crts17_2, crts17_3, crts17_4;
    RadioButton crts18_0, crts18_1, crts18_2, crts18_3, crts18_4;
    RadioButton crts19_0, crts19_1, crts19_2, crts19_3, crts19_4;
    RadioButton crts20_0, crts20_1, crts20_2, crts20_3, crts20_4;
    RadioButton crts21_0, crts21_1, crts21_2, crts21_3, crts21_4;
    RadioButton crts22_0, crts22_1, crts22_2, crts22_3, crts22_4;
    RadioButton crts23_0, crts23_1, crts23_2, crts23_3, crts23_4;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.crts_partc_fragment, container, false);

        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
        }

        pre_button = (Button) view.findViewById(R.id.preButton) ;
        next_button = (Button) view.findViewById(R.id.nextButton) ;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity)getActivity()).fragment3();
            }
        });
        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity)getActivity()).setFrag(1);
            }
        });

        TextView tv = (TextView)getActivity().findViewById(R.id.crts_title);
        tv.setText("CRTS. C");


         crts16_0 = (RadioButton)view.findViewById(R.id.crts16_0);
         crts16_1 = (RadioButton)view.findViewById(R.id.crts16_1);
         crts16_2 = (RadioButton)view.findViewById(R.id.crts16_2);
         crts16_3 = (RadioButton)view.findViewById(R.id.crts16_3);
         crts16_4 = (RadioButton)view.findViewById(R.id.crts16_4);

         crts17_0 = (RadioButton)view.findViewById(R.id.crts17_0);
         crts17_1 = (RadioButton)view.findViewById(R.id.crts17_1);
         crts17_2 = (RadioButton)view.findViewById(R.id.crts17_2);
         crts17_3 = (RadioButton)view.findViewById(R.id.crts17_3);
         crts17_4 = (RadioButton)view.findViewById(R.id.crts17_4);

         crts18_0 = (RadioButton)view.findViewById(R.id.crts18_0);
         crts18_1 = (RadioButton)view.findViewById(R.id.crts18_1);
         crts18_2 = (RadioButton)view.findViewById(R.id.crts18_2);
         crts18_3 = (RadioButton)view.findViewById(R.id.crts18_3);
         crts18_4 = (RadioButton)view.findViewById(R.id.crts18_4);

         crts19_0 = (RadioButton)view.findViewById(R.id.crts19_0);
         crts19_1 = (RadioButton)view.findViewById(R.id.crts19_1);
         crts19_2 = (RadioButton)view.findViewById(R.id.crts19_2);
         crts19_3 = (RadioButton)view.findViewById(R.id.crts19_3);
         crts19_4 = (RadioButton)view.findViewById(R.id.crts19_4);

         crts20_0 = (RadioButton)view.findViewById(R.id.crts20_0);
         crts20_1 = (RadioButton)view.findViewById(R.id.crts20_1);
         crts20_2 = (RadioButton)view.findViewById(R.id.crts20_2);
         crts20_3 = (RadioButton)view.findViewById(R.id.crts20_3);
         crts20_4 = (RadioButton)view.findViewById(R.id.crts20_4);

         crts21_0 = (RadioButton)view.findViewById(R.id.crts21_0);
         crts21_1 = (RadioButton)view.findViewById(R.id.crts21_1);
         crts21_2 = (RadioButton)view.findViewById(R.id.crts21_2);
         crts21_3 = (RadioButton)view.findViewById(R.id.crts21_3);
         crts21_4 = (RadioButton)view.findViewById(R.id.crts21_4);

         crts22_0 = (RadioButton)view.findViewById(R.id.crts22_0);
         crts22_1 = (RadioButton)view.findViewById(R.id.crts22_1);
         crts22_2 = (RadioButton)view.findViewById(R.id.crts22_2);
         crts22_3 = (RadioButton)view.findViewById(R.id.crts22_3);
         crts22_4 = (RadioButton)view.findViewById(R.id.crts22_4);

         crts23_0 = (RadioButton)view.findViewById(R.id.crts23_0);
         crts23_1 = (RadioButton)view.findViewById(R.id.crts23_1);
         crts23_2 = (RadioButton)view.findViewById(R.id.crts23_2);
         crts23_3 = (RadioButton)view.findViewById(R.id.crts23_3);
         crts23_4 = (RadioButton)view.findViewById(R.id.crts23_4);


        return view;

    }


}