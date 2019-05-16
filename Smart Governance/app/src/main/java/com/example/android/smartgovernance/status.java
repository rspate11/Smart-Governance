package com.example.android.smartgovernance;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class status extends Fragment {

    TextView ename,eemail,eaddress;
    Button fab;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Contact> list;
    RecyclerView recyclerview;
    FirebaseUser user;
    String uid;
    RecyclerviewAdapter mAdapter;
    String checkCategory;




    public status()
    {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       View view =inflater.inflate(R.layout.fragment_status, container, false);

        list = new ArrayList<>();

        user= FirebaseAuth.getInstance().getCurrentUser();
        uid= user.getUid();
        recyclerview = (RecyclerView)view.findViewById(R.id.rview);
        //fab =(Button)view.findViewById((R.id.fab));

        mAdapter = new RecyclerviewAdapter(list,getActivity().getApplicationContext());

      //  recyclerview.setHasFixedSize(true);
       recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

// set the adapter
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerview, new RecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {
                Contact movie = list.get(position);

            }


            public void onLongClick(View view, int position) {

            }
        }));


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("complains").child(uid);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        list.clear();
                        // StringBuffer stringbuffer = new StringBuffer();
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                          //  Long i = dataSnapshot1.getChildrenCount();
                            //ArrayList arr = dataSnapshot1.getChildren();
                            Detail userdetails = dataSnapshot1.getValue(Detail.class);
                            Contact listdata = new Contact();
                           String name=userdetails.getName();
                           String email=userdetails.getEmail();
                           String area=userdetails.getArea();
                            String date =userdetails.getDate();
                            String category=userdetails.getCategory();
                            String complain=userdetails.getComplain();
                            String complainid =userdetails.getComplainid();
                          //  String url=userdetails.getUrl();
                            String pno=userdetails.getPno();
                            String status =userdetails.getStatus();
                            listdata.setStatus(status);
                            listdata.setName(name);
                            listdata.setEmail(email);
                            listdata.setArea(area);
                           listdata.setCategory(category);
                           listdata.setComplain(complain);
                           listdata.setDate(date);
                           listdata.setPno(pno);
                           listdata.setComplainid(complainid);
                           //listdata.setUrl(url);
                            list.add(listdata);
                            mAdapter.notifyDataSetChanged();

                            checkCategory=category;


                        }

                        RecyclerviewAdapter recycler = new RecyclerviewAdapter(list,getActivity().getApplicationContext());
                        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
                        recyclerview.setLayoutManager(layoutmanager);
                        recyclerview.setAdapter(recycler);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
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

}


