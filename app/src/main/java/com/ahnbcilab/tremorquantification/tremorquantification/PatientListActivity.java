package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.PatientCardAdapter;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerViewAdapter;
import com.ahnbcilab.tremorquantification.data.PatientData;
import com.ahnbcilab.tremorquantification.data.PatientData2;
import com.google.firebase.auth.FirebaseAuth ;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.* ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


public class PatientListActivity extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener, Observer {
    private long lastTimeBackPressed;

    PatientData pd;
    DatabaseReference databasePatient;
    DatabaseReference databasePatientList;
    String uid;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ActionMode mActionMode;
    Menu context_menu;
    boolean isMultiSelect = false;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    ArrayList<Patient> data = new ArrayList<>();
    ArrayList<PatientItem> patientList = new ArrayList<>();
    ArrayList<PatientItem> selected_patientList = new ArrayList<>();
    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        TextView userAccount = (TextView) findViewById(R.id.userAccount) ;
        userAccount.setText(user.getDisplayName()) ;

        alertDialogHelper = new AlertDialogHelper(this);
        recyclerView = findViewById(R.id.patientList);

        databasePatient = firebaseDatabase.getReference();
        uid = user.getUid();

        databasePatientList = firebaseDatabase.getReference("PatientList") ;
        databasePatientList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerViewAdapter.clear() ;
                for(DataSnapshot mData : dataSnapshot.getChildren()){
                    String msg = mData.getValue().toString() ;
                    if(msg.contains(user.getUid())) {
                        String result = msg.substring(1,msg.length()-1);
                        String[] array = result.split(", ") ;
                        String id = array[3].substring(array[3].lastIndexOf("=")+1) ;
                        String name = array[1].substring(array[1].lastIndexOf("=")+1) ;
                        patientList.add(new PatientItem(null, id, name, null, null)) ;
                    }
                }
                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, patientList, selected_patientList);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Button button = (Button) findViewById(R.id.patientAdd);
        final ConstraintLayout userAccountLayout = (ConstraintLayout) findViewById(R.id.userAccountLayout) ;
        final TextView typeView = (TextView) findViewById(R.id.typeView);
        final TextView clinicIDView = (TextView) findViewById(R.id.clinicIDView);
        final TextView patientNameView = (TextView) findViewById(R.id.patientNameView);
        final TextView dateView = (TextView) findViewById(R.id.dateView);

        //타입에 따라 정렬
        typeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<PatientItem> filteredList = new ArrayList<>() ;
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, typeView) ;
                popupMenu.getMenuInflater().inflate(R.menu.menu_type, popupMenu.getMenu()) ;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.item_all:
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;

                            case R.id.item_et:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getPatientType() == "ET")
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;

                            case R.id.item_p:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getPatientType() == "P")
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;

                            case R.id.item_no:
                                for (int i = 0; i < patientList.size(); i++) {
                                    if (patientList.get(i).getPatientType() == null)
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show() ;
            }
        });

        //id에 따라 정렬
        clinicIDView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<PatientItem> filteredList = new ArrayList<>() ;
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, clinicIDView) ;
                popupMenu.getMenuInflater().inflate(R.menu.menu_clinicid, popupMenu.getMenu()) ;

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.id_내림차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o2.getClinicID().compareTo(o1.getClinicID());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;

                            case R.id.id_오름차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o1.getClinicID().compareTo(o2.getClinicID());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show() ;
            }
        });

        //이름에 따라 정렬
        patientNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<PatientItem> filteredList = new ArrayList<>() ;
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, patientNameView) ;
                popupMenu.getMenuInflater().inflate(R.menu.menu_patientname, popupMenu.getMenu()) ;

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.name_내림차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o2.getPatientName().compareTo(o1.getPatientName());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;

                            case R.id.name_오름차순:
                                Collections.sort(patientList, new Comparator<PatientItem>() {
                                    @Override
                                    public int compare(PatientItem o1, PatientItem o2) {
                                        return o1.getPatientName().compareTo(o2.getPatientName());
                                    }
                                });
                                for (int i = 0; i < patientList.size(); i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show() ;
            }
        });

        //환자 등록 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        //계정 버튼
        userAccountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, userAccountLayout) ;
                popupMenu.getMenuInflater().inflate(R.menu.menu_account, popupMenu.getMenu()) ;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.id_accountChange :
                                FirebaseAuth.getInstance().signOut() ;
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class) ;
                                intent.putExtra("ac", "Account Change") ;
                                startActivity(intent) ;
                                Toast.makeText(getApplicationContext(), "계정 선택을 누른 다음 로그인 후 계정을 변경하십시오.", Toast.LENGTH_LONG).show();
                                finish() ;
                                return true ;

                            case R.id.id_logout :
                                FirebaseAuth.getInstance().signOut() ;
                                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class) ;
                                startActivity(intent2) ;
                                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                                return true ;
                        }
                        return true;
                    }
                });
                popupMenu.show() ;
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAdapter = new RecyclerViewAdapter(this, patientList, selected_patientList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);

        //리스트 클릭했을 때 이벤트
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
                else {
                    Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    selected_patientList = new ArrayList<PatientItem>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);

            }
        }));
//검색 기능
        final EditText searchPatient = (EditText) findViewById(R.id.searchPatient);

        searchPatient.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();
                final ArrayList<PatientItem> filteredList = new ArrayList<>();
                for (int i = 0; i < patientList.size(); i++) {
                    final String text = patientList.get(i).getClinicID();
                    final String text2 = patientList.get(i).getPatientName();
                    if (text.contains(query) || text2.contains(query)) {
                        filteredList.add(patientList.get(i));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
            }
        });

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchPatient.getWindowToken(), 0);

        searchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.showSoftInput(searchPatient, 0);
            }
        });

    }

    //환자 등록
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_patient_dialog, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.patienAddButton);
        final Button cancel = (Button) view.findViewById(R.id.patienAddCancel);
        final EditText clinicID = (EditText) view.findViewById(R.id.addClinicID);
        final EditText patientName = (EditText) view.findViewById(R.id.addPatientName);

        final AlertDialog dialog = builder.create();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clinic_ID = clinicID.getText().toString();
                String patient_Name = patientName.getText().toString();

                Map<String, Object> childUpdates = new HashMap<>();
                Map<String, Object> postValues = null;
                pd = new PatientData(clinic_ID, patient_Name, uid, 0);
                postValues = pd.toMap();

                if (TextUtils.isEmpty(clinicID.getText().toString()) || TextUtils.isEmpty(patientName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "빈칸을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    childUpdates.put("/PatientList/" + clinic_ID, postValues);
                    databasePatient.updateChildren(childUpdates);
                    //databasePatient.child(clinic_ID).setValue(patient_Name);
                    //databasePatient.child(clinic_ID).setValue(uid);
                    //databasePatient.child(clinic_ID).setValue(0);
                    patientList.add(new PatientItem(null, clinic_ID, patient_Name, null, null));
                    recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, patientList, selected_patientList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    Toast.makeText(getApplicationContext(), "환자 추가", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //delete
    public void multi_select(int position) {
        if (mActionMode != null) {
            if (selected_patientList.contains(patientList.get(position)))
                selected_patientList.remove(patientList.get(position));
            else
                selected_patientList.add(patientList.get(position));

            if (selected_patientList.size() > 0)
                mActionMode.setTitle("" + selected_patientList.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }
    //어댑터 정비
    public void refreshAdapter() {
        recyclerViewAdapter.selected_patientList = selected_patientList;
        recyclerViewAdapter.patientList = patientList;
        recyclerViewAdapter.notifyDataSetChanged();
    }

    //삭제할 때 모드 변환하는 거
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    alertDialogHelper.showAlertDialog("", "삭제 하시겠습니까?", "네", "아니오", 1, false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            selected_patientList = new ArrayList<PatientItem>();
            refreshAdapter();
        }
    };

    //dialog
    @Override
    public void onPositiveClick(int from) {
        if (from == 1) {
            if (selected_patientList.size() > 0) {
                for (int i = 0; i < selected_patientList.size(); i++)
                    patientList.remove(selected_patientList.get(i));
                recyclerViewAdapter.notifyDataSetChanged();
                if (mActionMode != null) {
                    mActionMode.finish();
                }
                Toast.makeText(getApplicationContext(), "Delete Click", Toast.LENGTH_SHORT).show();
            }
        } else if (from == 2) {

        }
    }

    @Override
    public void onNegativeClick(int from) {}

    @Override
    public void onNeutralClick(int from) {}

    @Override
    public void update(Observable observable, Object o) {}

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            ActivityCompat.finishAffinity(this);
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this,"'뒤로' 버튼을 한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
    }
}
