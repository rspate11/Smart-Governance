package com.example.android.smartgovernance;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.graphics.Movie;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class assignments extends AppCompatActivity {

    DatabaseReference databaseReference, databaseReference1,databaseReferencecat,databaseReferencename;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<WorkerContact> list;
    RecyclerView recyclerview;
    SharedPreferenceUtils sharedPreferenceUtils;
    RecyclerviewAdapter1 mAdapter;
    TextView uid,cat;
    Bundle bundle,bundle1;
    int string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);



        list = new ArrayList<>();
        recyclerview = (RecyclerView) findViewById(R.id.rview);
        //  uid = (TextView) findViewById(R.id.uid);
        //   cat = (TextView) findViewById(R.id.category);
        bundle = getIntent().getExtras();

        mAdapter = new RecyclerviewAdapter1(list, assignments.this.getApplicationContext());
        recyclerview.addItemDecoration(new DividerItemDecoration(assignments.this, LinearLayoutManager.VERTICAL));

        recyclerview.setAdapter(mAdapter);
        sharedPreferenceUtils =SharedPreferenceUtils.getInstance();
        String cat1 = sharedPreferenceUtils.getString(assignments.this,"category");
        String name1 = sharedPreferenceUtils.getString(assignments.this,"name");
        //  uid.setText(name1);
        //  cat.setText(cat1);
        databaseReference =  FirebaseDatabase.getInstance().getReference("worker").child(cat1).child(name1);


        databaseReference.child("assignments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        WorkerDetail userdetails = dataSnapshot1.getValue(WorkerDetail.class);
                        WorkerContact listdata = new WorkerContact();
                        String name = userdetails.getName();
                        String email = userdetails.getEmail();
                        String area = userdetails.getArea();
                        String issued_date = userdetails.getIssued_date();
                        String category = userdetails.getCategory();
                        String complain = userdetails.getComplain();
                        //  String url=userdetails.getUrl();
                        String pno = userdetails.getPno();
                        String assigned_date = userdetails.getAssigned_date();
                        String uid = userdetails.getUid();
                        String complainid = userdetails.getComplainid();
                        String status = userdetails.getStatus();
                        String no_of_days = userdetails.getNo_of_days();
                        listdata.setName(name);
                        listdata.setEmail(email);
                        listdata.setArea(area);
                        listdata.setCategory(category);
                        listdata.setComplain(complain);
                        listdata.setIssued_date(issued_date);
                        listdata.setPno(pno);
                        listdata.setAssigned_date(assigned_date);
                        listdata.setUid(uid);
                        listdata.setComplainid(complainid);
                        listdata.setStatus(status);
                        listdata.setNo_of_days(no_of_days);
                        //listdata.setUrl(url);
                        list.add(listdata);
                        mAdapter.notifyDataSetChanged();
                    }

                    RecyclerviewAdapter1 recycler = new RecyclerviewAdapter1(list, assignments.this.getApplicationContext());
                    RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(assignments.this,LinearLayoutManager.VERTICAL,true);
                    recyclerview.setLayoutManager(layoutmanager);
                    recyclerview.setAdapter(recycler);
                   // string = getItemCount();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

   // @Override
  /*  public void onBackPressed() {
        //int count = 0;
        if (mAdapter != null) {
            string = mAdapter.getItemCount();
        }
        else {
            string = 0;
        }
        // Toast.makeText(assignments.this, "hey"+string,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("keyName",string);
        setResult(RESULT_OK, intent);
        finish();
    }*/
}



