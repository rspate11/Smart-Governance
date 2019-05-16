
        package com.example.android.smartgovernance;
import android.app.ProgressDialog;
import android.content.Context;
        import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WaterSupply extends Fragment {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    FirebaseDatabase database, database1;
    DatabaseReference myRef, myRef1, databaseReference,databaseReference1;
    List<Contact> list;
    Contact ls;
    List<String> list1;
    RecyclerView recyclerview;
    FirebaseUser user;
    String uid, name1, email1, area1, date1,uid1, pno1, complain1, name, area, complainid1, status1,url1;
    RecyclerviewAdapter mAdapter;
    String questionKey, wn, phone_no, msg;
    EditText editTextSearch;
    SharedPreferenceUtils sharedPreferenceUtils;
    Date date,asdate;
    SimpleDateFormat simpleDateFormat;
    String DateTostr;
    long nodays;


    public WaterSupply() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_water_supply, container, false);


        list = new ArrayList<>();
        list1 = new ArrayList<>();
        // ls =new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
       // editTextSearch = (EditText)view.findViewById(R.id.editTextSearch);
        recyclerview = (RecyclerView) view.findViewById(R.id.rview);
        mAdapter = new RecyclerviewAdapter(list, getActivity().getApplicationContext());
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm a");
        DateTostr = DateFormat.getDateTimeInstance().format(date);


        recyclerview.setAdapter(mAdapter);


        database1 = FirebaseDatabase.getInstance();
        myRef1 = database1.getReference("water");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), Find.class);
                startActivity(intent);
            }
        });


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
                        String status = userdetails.getStatus();





                        if (status.equals("In Process")) {

                           /* databaseReference1 = FirebaseDatabase.getInstance().getReference("assign").child(complainid);
                            databaseReference1.child("assigned_date").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String date = dataSnapshot.getValue(String.class);
                                    try {
                                        asdate=DateFormat.getDateTimeInstance().parse(date);
                                    } catch (java.text.ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getActivity(),date, Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            databaseReference1.child("no_of_days").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String no_of_days = dataSnapshot.getValue(String.class);
                                    nodays= Long.parseLong(no_of_days);

                                    Toast.makeText(getActivity(),no_of_days, Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/

                            long diff = 0;
                            try {
                                diff = DateFormat.getDateTimeInstance().parse(DateTostr).getTime() - DateFormat.getDateTimeInstance().parse(date).getTime();
                               // Toast.makeText(getActivity(), "done" +diff, Toast.LENGTH_SHORT).show();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }

                            long seconds = diff / 1000;
                            long minutes = seconds / 60;
                            long hours = minutes / 60;
                            long days = (hours / 24) + 1;
                           // Toast.makeText(getActivity(), "done" +days, Toast.LENGTH_SHORT).show();

                          //  long days1=nodays-days;


                            if(days>=2) {

                                // databaseReference1.removeValue();
                                new SendMail(email).execute("");

                            }
                            else {


                                Toast.makeText(getActivity(), "done" + days, Toast.LENGTH_SHORT).show();
                            }
                        }







                        listdata.setComplainid(complainid);
                        listdata.setUid(uid);
                        listdata.setName(name);
                        listdata.setEmail(email);
                        listdata.setArea(area);
                        listdata.setCategory(category);
                        listdata.setComplain(complain);
                        listdata.setDate(date);
                        listdata.setPno(pno);
                        listdata.setImageURL(imageURL);
                        listdata.setStatus(status);
                        list.add(listdata);

                        mAdapter.notifyDataSetChanged();

                    }
                    recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerview, new RecyclerTouchListener.ClickListener() {
                        public void onClick(View view, int position) {

                            ls = list.get(position);
                            name1 = ls.getName();
                            area1 = ls.getArea();
                            url1=ls.getImageURL();
                            // category1= ls.getCategory();
                            email1 = ls.getEmail();
                            date1 = ls.getDate();
                            complain1 = ls.getComplain();
                            pno1 = ls.getPno();
                            uid1 = ls.getUid();
                            complainid1 = ls.getComplainid();
                            status1 = ls.getStatus();

                            if (status1.equals("Submitted")) {
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
                                bundle.putString("key", "WaterSupply");
                                bundle.putString("url",url1);
                                bundle.putStringArrayList("array", (ArrayList<String>) list1);
                                Intent in = new Intent(getActivity(), assign.class);
                                in.putExtra("some", bundle);
                                in.putExtra("ws", bundle);
                                in.putExtra("u",bundle);
                                startActivity(in);
                            } else {
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
                                // Toast.makeText(getActivity(),"Complain is in process",Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getActivity(), Chat.class);


                                sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                                sharedPreferenceUtils.setString(getActivity(),"uid",uid1);
                                String w = "WaterSupply";
                                sharedPreferenceUtils.setString(getActivity(),"cat2",w);
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("cid", complainid1);
                                bundle1.putString("st", status1);
                                bundle1.putString("cat","WaterSupply");
                                bundle1.putString("wn", wn);
                                in.putExtra("id", bundle1);
                                in.putExtra("s", bundle1);
                                in.putExtra("n", bundle1);
                                in.putExtra("c",bundle1);
                                startActivity(in);

                            }
                        }

                        public void onLongClick(View view, int position) {

                        }
                    }));

                    RecyclerviewAdapter recycler = new RecyclerviewAdapter(list, getActivity().getApplicationContext());
                    RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
                    // recyclerview.scrollToPosition(mAdapter.getItemCount()-1);
                    recyclerview.setLayoutManager(layoutmanager);
                    recyclerview.setAdapter(recycler);

                }
            }

            @Override
            public void onCancelled(DatabaseError error1) {

            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    private class SendMail extends AsyncTask<String, Integer, Void> {

        private ProgressDialog progressDialog;
        private String email;

        public  SendMail(String email)
        {
            this.email=email;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        protected Void doInBackground(String... params) {
            Mail m = new Mail("78vrundapatel@gmail.com", "vrundapatel78");

            String[] toArr ={email, "78vrundapatel@gmail.com"};
            m.setTo(toArr);
            m.setFrom("78vrundapatel@gmail.com");
            m.setSubject("Alert Message");
            m.setBody("Work pending");

            try {
                if (m.send()) {
                    Toast.makeText(getActivity(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }
    }




}
