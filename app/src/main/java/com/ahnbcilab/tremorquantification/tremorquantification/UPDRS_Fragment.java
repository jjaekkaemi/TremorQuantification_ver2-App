package com.ahnbcilab.tremorquantification.tremorquantification;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UPDRS_Fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.non_task_fragment, container, false);
        Intent intent = new Intent(view.getContext(), UPDRSActivity.class);
        startActivity(intent);
        return view;
    }

}
