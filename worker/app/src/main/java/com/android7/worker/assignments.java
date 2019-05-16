package com.android7.worker;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.graphics.Movie;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.MenuItemCompat.getActionView;

public class assignments extends AppCompatActivity{

    DatabaseReference databaseReference, databaseReference1,databaseReferencecat,databaseReferencename;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<WorkerContact> list;
    WorkerContact ls;
    List<String> list1;
    String id,n,email1,area1,date1,category1,uid1,pno1,complain1,name,area,complainid1,status1;
    RecyclerView recyclerview;
    SharedPreferenceUtils sharedPreferenceUtils,sharedPreferenceUtils2;
    RecyclerviewAdapter mAdapter;
    TextView uid,cat;
    String cat1,name1,remark;
    Bundle bundle,bundle1,bundle2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        list = new ArrayList<>();
        recyclerview = (RecyclerView) findViewById(R.id.rview);
        uid = (TextView) findViewById(R.id.uid);
        cat = (TextView) findViewById(R.id.category);
        bundle = getIntent().getExtras();
        if (bundle!=null){
            bundle1 = bundle.getBundle("login");
            bundle2 = bundle.getBundle("n");
            cat1 = bundle1.getString("category");
            name1 =bundle2.getString("name");
            uid.setText(name1);
            cat.setText(cat1);
        }
        mAdapter = new RecyclerviewAdapter(list, assignments.this.getApplicationContext());
         recyclerview.addItemDecoration(new DividerItemDecoration(assignments.this, LinearLayoutManager.VERTICAL));

        recyclerview.setAdapter(mAdapter);
        sharedPreferenceUtils =SharedPreferenceUtils.getInstance();
        String cat2 = sharedPreferenceUtils.getString(assignments.this,"cat");
       String name2 = sharedPreferenceUtils.getString(assignments.this,"name");
        sharedPreferenceUtils2 = SharedPreferenceUtils.getInstance();
        sharedPreferenceUtils2.setString(assignments.this,"n",name2);
          databaseReference =  FirebaseDatabase.getInstance().getReference("worker").child(cat2).child(name2);
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



                        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerview, new RecyclerTouchListener.ClickListener() {
                            public void onClick(View view, int position) {

                                ls =list.get(position);
                                complainid1=ls.getComplainid();
                                status1 =ls.getStatus();
                                uid1=ls.getUid();
                                area1=ls.getArea();

                                databaseReference1 =  FirebaseDatabase.getInstance().getReference("assign").child(complainid1);
                                databaseReference1.child("remark").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                         remark = dataSnapshot.getValue(String.class).toString();


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                    //list1.add(complainid1);
                                    //list1.add(status1);

                                    //assign s1=new assign();
                                sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                                sharedPreferenceUtils.setString(assignments.this,"uid",uid1);

                                Bundle bundle = new Bundle();
                                    bundle.putString("id",complainid1 );
                                    bundle.putString("area",area1 );
                                    bundle.putString("remark",remark);
                                    Intent in = new Intent(assignments.this, Chat.class);
                                    in.putExtra("i", bundle);
                                    in.putExtra("r", bundle);
                                    in.putExtra("a", bundle);
                                    startActivity(in);

                            }
                            public void onLongClick(View view, int position) {

                            }
                        }));

                        RecyclerviewAdapter recycler = new RecyclerviewAdapter(list, assignments.this.getApplicationContext());
                        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(assignments.this,LinearLayoutManager.VERTICAL,true);
                        recyclerview.setLayoutManager(layoutmanager);
                        recyclerview.setAdapter(recycler);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(assignments.this, Find.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerview.smoothScrollToPosition(recyclerview.getAdapter().getItemCount() - 1);
            }
        });


        }
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);
    }

}



