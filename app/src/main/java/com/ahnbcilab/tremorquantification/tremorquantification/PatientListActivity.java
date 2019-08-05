package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
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
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ahnbcilab.tremorquantification.Adapters.AlertDialogHelper;
import com.ahnbcilab.tremorquantification.Adapters.PatientCardAdapter;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerItemClickListener;
import com.ahnbcilab.tremorquantification.Adapters.RecyclerViewAdapter;
import com.ahnbcilab.tremorquantification.data.DoctorData;
import com.ahnbcilab.tremorquantification.data.PatientData;
import com.ahnbcilab.tremorquantification.data.PatientData2;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


public class PatientListActivity extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener, Observer, GoogleApiClient.OnConnectionFailedListener {
    //
    private static final int RC_SIGN_IN = 10;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    String name, email, uid, r_uid;
    DatabaseReference databaseDoctor;
    ArrayList<String> uid_list = new ArrayList<String>();
    //
    private long lastTimeBackPressed;

    PatientData pd;
    DatabaseReference databasePatient;
    DatabaseReference databasePatientList;
    int taskNo;
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

        //
        databaseDoctor = firebaseDatabase.getReference("UserList");
        databaseDoctor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    r_uid = fileSnapshot.child("uid").getValue(String.class);
                    if (r_uid != null) {

                    } else {
                        Log.v("알림", "null");
                    }
                    uid_list.add(r_uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("알림", "Failed");
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
//
        TextView userAccount = (TextView) findViewById(R.id.userAccount);
        userAccount.setText(user.getDisplayName());

        alertDialogHelper = new AlertDialogHelper(this);
        recyclerView = findViewById(R.id.patientList);

        databasePatient = firebaseDatabase.getReference();
        uid = user.getUid();

        databasePatientList = firebaseDatabase.getReference("PatientList");
        databasePatientList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerViewAdapter.clear();
                for (DataSnapshot mData : dataSnapshot.getChildren()) {
                    String msg = mData.getValue().toString();
                    Log.v("disease", msg);
                    if (msg.contains(user.getUid())) {
                        String result = msg.substring(1, msg.length());
                        String[] array = result.split(", |\\}");
                        String id = array[4].substring(array[4].lastIndexOf("=")+1);
                        String name = array[2].substring(array[2].lastIndexOf("=") + 1);
                        String updrs_task = String.valueOf(mData.child("UPDRS List").getChildrenCount());
                        String crts_task = String.valueOf(mData.child("CRTS List").getChildrenCount());
                        String task = String.valueOf(Integer.parseInt(updrs_task) + Integer.parseInt(crts_task));
                        String diseaseType = String.valueOf(mData.child("DiseaseType").getValue());
                        if(task.equals("1")&&updrs_task.equals("1")){
                            Log.v("comehere", "come");
                            patientList.add(new PatientItem("P", id, name, null, null));
                            databasePatientList.child(id).child("DiseaseType").setValue("P");
                        }
                        else if(task.equals("1")&&crts_task.equals("1")){
                            patientList.add(new PatientItem("ET", id, name, null, null));
                            databasePatientList.child(id).child("DiseaseType").setValue("ET");
                        }
                        else {
                            if(diseaseType == "P"){
                                patientList.add(new PatientItem("P", id, name, null, null));
                            }
                            else if(diseaseType == "ET"){
                                patientList.add(new PatientItem("ET", id, name, null, null));
                            }
                            else{
                                patientList.add(new PatientItem(null, id, name, null, null));
                            }
                        }
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

        final TextView t = (TextView) findViewById(R.id.typeViewItem);
        Button button = (Button) findViewById(R.id.patientAdd);
        final ConstraintLayout userAccountLayout = (ConstraintLayout) findViewById(R.id.userAccountLayout);
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
                            case R.id.item_all :
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true ;

                            case R.id.item_et :
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    if (patientList.get(i).getPatientType()=="ET")
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true ;

                            case R.id.item_p :
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    if (patientList.get(i).getPatientType()=="P")
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true ;

                            case R.id.item_no :
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    if (patientList.get(i).getPatientType()==null)
                                        filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                                return true ;
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
                            case R.id.id_내림차순 : Collections.sort(patientList, new Comparator<PatientItem>() {
                                @Override
                                public int compare(PatientItem o1, PatientItem o2) {
                                    return o2.getClinicID().compareTo(o1.getClinicID()) ;
                                }
                            }) ;
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true ;

                            case R.id.id_오름차순 : Collections.sort(patientList, new Comparator<PatientItem>() {
                                @Override
                                public int compare(PatientItem o1, PatientItem o2) {
                                    return o1.getClinicID().compareTo(o2.getClinicID()) ;
                                }
                            }) ;
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true ;
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
                            case R.id.name_내림차순 : Collections.sort(patientList, new Comparator<PatientItem>() {
                                @Override
                                public int compare(PatientItem o1, PatientItem o2) {
                                    return o2.getPatientName().compareTo(o1.getPatientName()) ;
                                }
                            }) ;
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true ;

                            case R.id.name_오름차순 : Collections.sort(patientList, new Comparator<PatientItem>() {
                                @Override
                                public int compare(PatientItem o1, PatientItem o2) {
                                    return o1.getPatientName().compareTo(o2.getPatientName()) ;
                                }
                            }) ;
                                for(int i = 0 ; i<patientList.size() ; i++) {
                                    filteredList.add(patientList.get(i));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));
                                recyclerViewAdapter = new RecyclerViewAdapter(PatientListActivity.this, filteredList, selected_patientList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerViewAdapter.notifyDataSetChanged();
                                return true ;
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
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(PatientListActivity.this, userAccountLayout);
                popupMenu.getMenuInflater().inflate(R.menu.menu_account, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.id_accountChange:
                                Log.v("알림", "구글 LOGOUT");
                                AlertDialog.Builder alt_bld = new AlertDialog.Builder(v.getContext());
                                alt_bld.setMessage("다른 계정으로 로그인 하시겠습니까?").setCancelable(false)
                                        .setPositiveButton("네",

                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int id) {

                                                        signOut();

                                                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                                                        startActivityForResult(signInIntent, RC_SIGN_IN);
                                                    }

                                                }).setNegativeButton("아니오",

                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int id) {

                                                // 아니오 클릭. dialog 닫기.

                                                dialog.cancel();

                                            }

                                        });

                                AlertDialog alert = alt_bld.create();
                                alert.setTitle("계정 변경");
                                alert.setIcon(R.drawable.assignment);
                                alert.show();
                                return true;

                            case R.id.id_logout:
                                FirebaseAuth.getInstance().signOut();
                                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent2);
                                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
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
                    TextView patientClinicID = view.findViewById(R.id.clinicIDItem);
                    TextView patientClinicName = view.findViewById(R.id.patientNameItem);
                    Intent intent = new Intent(getApplicationContext(), PersonalPatient.class);
                    intent.putExtra("ClinicID", patientClinicID.getText());
                    intent.putExtra("PatientName", patientClinicName.getText());
                    intent.putExtra("doc_uid", uid);
                    intent.putExtra("task", "none");
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

                pd = new PatientData(clinic_ID, patient_Name, uid, taskNo, "null");
                postValues = pd.toMap();

                if (TextUtils.isEmpty(clinicID.getText().toString()) || TextUtils.isEmpty(patientName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "빈칸을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    childUpdates.put("/PatientList/" + clinic_ID, postValues);
                    databasePatient.updateChildren(childUpdates);
                    //databasePatient.child(clinic_ID).setValue(patient_Name);
                    //databasePatient.child(clinic_ID).setValue(uid);
                    //databasePatient.child(clinic_ID).setValue(0);                    patientList.add(new PatientItem(null, clinic_ID, patient_Name, null, null));
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
            if (selected_patientList.contains(patientList.get(position))) {
                selected_patientList.remove(patientList.get(position));

            } else
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
                for (int i = 0; i < selected_patientList.size(); i++) {
                    patientList.remove(selected_patientList.get(i));
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query deleteQuery = ref.child("PatientList").orderByChild("ClinicID").equalTo(selected_patientList.get(i).getClinicID());

                    deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {
                                deleteSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
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
    public void onNegativeClick(int from) {
    }

    @Override
    public void onNeutralClick(int from) {
    }

    @Override
    public void update(Observable observable, Object o) {
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            ActivityCompat.finishAffinity(this);
            return;
        }

        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.v("알림", "google sign 성공, FireBase Auth.");
                Toast.makeText(PatientListActivity.this, "계정 변경되었습니다.", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.v("알림", result.isSuccess() + " Google Sign In failed. Because : " + result.getStatus().toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("알림", "ONCOMPLETE");
                        if (!task.isSuccessful()) {
                            Log.v("알림", "!task.isSuccessful()");
                            Toast.makeText(PatientListActivity.this, "Authenticataion failed!", Toast.LENGTH_LONG).show();
                        } else {
                            Log.v("알림", "task.isSuccessful()");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (uid_list.size() == 0) {
                                alertDisplay();
                            } else {
                                for (int i = 0; i < uid_list.size(); i++) {
                                    if (uid_list.get(i).equals(user.getUid())) {
                                        getInfo();
                                        DoctorData doc = new DoctorData(name, email, uid);
                                        databaseDoctor.child(uid).setValue(doc);
                                    } else {
                                        alertDisplay();
                                    }
                                }
                            }

                        }

                    }
                });
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("알림", "onConnectionFailed");
    }

    public void signOut() {

        mGoogleApiClient.connect();

        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override

            public void onConnected(@Nullable Bundle bundle) {

                mAuth.signOut();

                if (mGoogleApiClient.isConnected()) {

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {

                        @Override

                        public void onResult(@NonNull Status status) {

                            if (status.isSuccess()) {

                                Log.v("알림", "로그아웃 성공");

                                setResult(1);

                            } else {

                                setResult(0);

                            }

                        }

                    });

                }

            }

            @Override

            public void onConnectionSuspended(int i) {

                Log.v("알림", "Google API Client Connection Suspended");

                setResult(-1);

                finish();

            }

        });

    }


    public void alertDisplay() {

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("프로필 가져오기")
                .setMessage("google 프로필을 가져오는 것에 동의하십니까?")
                .setPositiveButton("동의", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getInfo();
                        DoctorData doc = new DoctorData(name, email, uid);
                        databaseDoctor.child(uid).setValue(doc);
                        Toast.makeText(PatientListActivity.this, email, Toast.LENGTH_SHORT).show();


                    }
                })
                .setNegativeButton("동의안함", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }


    public void getInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();
        email = user.getEmail();
        uid = user.getUid();
    }
}

