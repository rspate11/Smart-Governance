
package com.example.android.smartgovernance;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
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


public class StrayAnimal extends Fragment {


    FirebaseDatabase database,database1;
    DatabaseReference myRef,myRef1,databaseReference;
    List<Contact> list;
    Contact ls;
    List<String> list1;
    RecyclerView recyclerview;
    FirebaseUser user;
    String uid,name1,email1,area1,date1,category1,uid1,pno1,complain1,name,area,complainid1,status1,wn,url1;
    RecyclerviewAdapter mAdapter;
    SharedPreferenceUtils sharedPreferenceUtils;


    public StrayAnimal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stray_animal, container, false);


        list = new ArrayList<>();
        list1=new ArrayList<>();
        // ls =new ArrayList<>();

        user= FirebaseAuth.getInstance().getCurrentUser();
        uid= user.getUid();
        recyclerview = (RecyclerView)view.findViewById(R.id.rview);

        mAdapter = new RecyclerviewAdapter(list,getActivity().getApplicationContext());

        //  recyclerview.setHasFixedSize(true);
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

// set the adapter
        recyclerview.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(),Find.class);
                startActivity(intent);
            }
        });



        database1 = FirebaseDatabase.getInstance();
        myRef1 = database1.getReference("animal");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {
                {
                    for (DataSnapshot questionSnapshot : dataSnapshot2.getChildren()) {

                        Detail userdetails = questionSnapshot.getValue(Detail.class);
                        Contact listdata = new Contact();
                        name = userdetails.getName();
                        String email = userdetails.getEmail();
                        area = userdetails.getArea();
                        String date = userdetails.getDate();
                        String category = userdetails.getCategory();
                        String complain = userdetails.getComplain();
                        String imageURL = userdetails.getImageURL();
                        String pno = userdetails.getPno();
                        String uid = userdetails.getUid();
                        String complainid = userdetails.getComplainid();
                        String status =userdetails.getStatus();



                        listdata.setStatus(status);
                        listdata.setName(name);
                        listdata.setEmail(email);
                        listdata.setArea(area);
                        listdata.setCategory(category);
                        listdata.setComplain(complain);
                        listdata.setDate(date);
                        listdata.setPno(pno);
                        listdata.setImageURL(imageURL);
                        listdata.setUid(uid);
                        listdata.setComplainid(complainid);
                        list.add(listdata);

                        mAdapter.notifyDataSetChanged();




                    }
                    recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerview, new RecyclerTouchListener.ClickListener() {
                        public void onClick(View view, int position) {
                            ls =list.get(position);
                            name1= ls.getName();
                            area1= ls.getArea();
                            // category1= ls.getCategory();
                            email1= ls.getEmail();
                            date1= ls.getDate();
                            complain1=ls.getComplain();
                            pno1=ls.getPno();
                            uid1= ls.getUid();
                            complainid1=ls.getComplainid();
                             status1 =ls.getStatus();
                            url1=ls.getImageURL();
                             if(status1.equals("Submitted")) {
                                 list1.add(uid1);
                                 list1.add(complainid1);
                                 list1.add(name1);
                                 list1.add(email1);
                                 list1.add(pno1);
                                 list1.add(area1);
                                 list1.add(complain1);
                                 list1.add(date1);
                                 list1.add(status1);
                                 //assign s1=new assign();
                                 Bundle bundle = new Bundle();
                                 bundle.putStringArrayList("array", (ArrayList<String>) list1);
                                 Intent in = new Intent(getActivity(), assign.class);
                                 bundle.putString("key", "StrayAnimal");
                                 bundle.putString("url",url1);
                                 in.putExtra("ws", bundle);
                                 in.putExtra("some", bundle);
                                 in.putExtra("u",bundle);
                                 startActivity(in);
                             }
                             else
                             {

                                 databaseReference = FirebaseDatabase.getInstance().getReference("info").child(complainid1).child("worker");
                                 databaseReference.addValueEventListener(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(DataSnapshot dataSnapshot) {
                                         wn = dataSnapshot.getValue(String.class);
                                     }

                                     @Override
                                     public void onCancelled(DatabaseError databaseError) {

                                     }
                                 });

                                 //Toast.makeText(getActivity(),"Complain is in process",Toast.LENGTH_SHORT).show();
                                 Intent in = new Intent(getActivity(), Chat.class);


                                 sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                                 sharedPreferenceUtils.setString(getActivity(),"uid",uid1);
                                 sharedPreferenceUtils.setString(getActivity(),"cat2","StrayAnimal");
                                 Bundle bundle1=new Bundle();
                                 bundle1.putString("cid",complainid1);
                                 bundle1.putString("st",status1);
                                 bundle1.putString("wn",wn);
                                 bundle1.putString("cat","StrayAnimal");
                                 in.putExtra("id",bundle1);
                                 in.putExtra("s",bundle1);
                                 in.putExtra("n",bundle1);
                                 startActivity(in);

                                 // Toast.makeText(getActivity(),"Complain is in process",Toast.LENGTH_SHORT).show();
                             }
                        }
                        public void onLongClick(View view, int position) {

                        }
                    }));

                    RecyclerviewAdapter recycler = new RecyclerviewAdapter(list,getActivity().getApplicationContext());
                    RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
                    recyclerview.setLayoutManager(layoutmanager);
                    recyclerview.setAdapter(recycler);

                }
            }

            @Override
            public void onCancelled(DatabaseError error1) {
                // Failed to read value
                //  Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        FloatingActionButton fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
                // layoutManager.scrollToPositionWithOffset(0, 0);
                recyclerview.smoothScrollToPosition(recyclerview.getAdapter().getItemCount() - 1);
            }
        });
        return view;
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

}
