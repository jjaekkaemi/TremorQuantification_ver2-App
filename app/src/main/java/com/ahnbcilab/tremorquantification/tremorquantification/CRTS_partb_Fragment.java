package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


public class CRTS_partb_Fragment extends Fragment {
    CRTS_parta_Fragment frag1;
    CRTS_partc_Fragment frag3;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    String crts_num;
    Button pre_button, next_button ;

    int c11;
    int c12;
    int c13;
    int c14;
    int c15;

    RadioButton crts11_0, crts11_1, crts11_2, crts11_3, crts11_4;
    RadioButton crts12_0, crts12_1, crts12_2, crts12_3, crts12_4;
    RadioButton crts13_0, crts13_1, crts13_2, crts13_3, crts13_4;
    RadioButton crts14_0, crts14_1, crts14_2, crts14_3, crts14_4;
    RadioButton crts15_0, crts15_1, crts15_2, crts15_3, crts15_4;

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

    String Clinic_ID, PatientName;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.crts_partb_fragment, container, false);

        if(getArguments() != null){
            Clinic_ID = getArguments().getString("Clinic_ID");
            PatientName = getArguments().getString("PatientName");
        }

        TextView tv = (TextView)getActivity().findViewById(R.id.crts_title);
        tv.setText("CRTS. B");

        Button gotowrite = (Button)view.findViewById(R.id.gotowrite);

        pre_button = (Button) view.findViewById(R.id.preButton) ;
        next_button = (Button) view.findViewById(R.id.nextButton) ;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity)getActivity()).frgment2();
            }
        });
        pre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CRTSActivity)getActivity()).setFrag(0);
            }
        });

         crts11_0 = (RadioButton)view.findViewById(R.id.crts11_0);
         crts11_1 = (RadioButton)view.findViewById(R.id.crts11_1);
         crts11_2 = (RadioButton)view.findViewById(R.id.crts11_2);
         crts11_3 = (RadioButton)view.findViewById(R.id.crts11_3);
         crts11_4 = (RadioButton)view.findViewById(R.id.crts11_4);

        crts12_0 = (RadioButton)view.findViewById(R.id.crts12_0);
        crts12_1 = (RadioButton)view.findViewById(R.id.crts12_1);
        crts12_2 = (RadioButton)view.findViewById(R.id.crts12_2);
        crts12_3 = (RadioButton)view.findViewById(R.id.crts12_3);
        crts12_4 = (RadioButton)view.findViewById(R.id.crts12_4);

        crts13_0 = (RadioButton)view.findViewById(R.id.crts13_0);
        crts13_1 = (RadioButton)view.findViewById(R.id.crts13_1);
        crts13_2 = (RadioButton)view.findViewById(R.id.crts13_2);
        crts13_3 = (RadioButton)view.findViewById(R.id.crts13_3);
        crts13_4 = (RadioButton)view.findViewById(R.id.crts13_4);

         crts14_0 = (RadioButton)view.findViewById(R.id.crts14_0);
         crts14_1 = (RadioButton)view.findViewById(R.id.crts14_1);
         crts14_2 = (RadioButton)view.findViewById(R.id.crts14_2);
         crts14_3 = (RadioButton)view.findViewById(R.id.crts14_3);
         crts14_4 = (RadioButton)view.findViewById(R.id.crts14_4);

         crts15_0 = (RadioButton)view.findViewById(R.id.crts15_0);
         crts15_1 = (RadioButton)view.findViewById(R.id.crts15_1);
         crts15_2 = (RadioButton)view.findViewById(R.id.crts15_2);
         crts15_3 = (RadioButton)view.findViewById(R.id.crts15_3);
         crts15_4 = (RadioButton)view.findViewById(R.id.crts15_4);


        gotowrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WritingActivity.class);
                intent.putExtra("ClinicID", Clinic_ID);
                intent.putExtra("PatientName", PatientName);
                intent.putExtra("crts_num", crts_num);
                startActivity(intent);
            }
        });


        return view;

    }

}