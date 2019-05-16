package com.example.android.smartgovernance;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Riddhi on 12-Mar-18.
 */

public class vrundainfo extends AppCompatActivity {

    Bundle bundle,complain,bundle4,bundle3,bundle5,bundle2;
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3,databaseReference4;
    Date date;
    SimpleDateFormat simpleDateFormat;
    String workeremail1,workerpno1,workern1,type,url;
    String DateTostr,category,msgtxt,tos,subs,name,email,userid,complainid,phno,address,description,issueddate,assigneddt,status,daystocomplete;
    TextView vname , vaddress,vemail,vpno,vurl,vcategory,vdate,vcomplain,uid,cid,vst,vdt,workername,workeremail,workerpno,workern;
    ArrayList<String> list;
    String n;
    Integer i;
    Button info;
    int flag =0;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
   List<WorkerContact> list1;
    RecyclerviewAdapter1 mAdapter;
    SharedPreferenceUtils sharedPreferenceUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrundainfo);
        final   EditText days = (EditText)findViewById(R.id.subject);
        workername=(TextView)findViewById(R.id.wname);
        workeremail=(TextView)findViewById(R.id.wemail);
        workerpno=(TextView)findViewById(R.id.wpno);
        workern = (TextView) findViewById(R.id.wn);
        uid=(TextView)findViewById(R.id.uid);
        vst=(TextView)findViewById(R.id.vst);
        cid=(TextView)findViewById(R.id.cid);
        vname = (TextView)findViewById(R.id.vname);
        vemail = (TextView)findViewById(R.id.vemail);
        vaddress = (TextView)findViewById(R.id.vaddress);
        vpno = (TextView)findViewById(R.id.vpno);
        vcategory = (TextView)findViewById(R.id.vcategory);
        vcomplain= (TextView)findViewById(R.id.vcomplain);
        vdt= (TextView)findViewById(R.id.vdt);
        vdate = (TextView)findViewById(R.id.vdate);
        Button send = (Button) findViewById(R.id.assign);
        info= (Button) findViewById(R.id.info);
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm a");
        DateTostr = DateFormat.getDateTimeInstance().format(date);
        vdt.setText(DateTostr);
        list1 = new ArrayList<>();
        mAdapter = new RecyclerviewAdapter1(list1, vrundainfo.this.getApplicationContext());


        bundle = getIntent().getExtras();
        if (bundle != null) {

            bundle4 = bundle.getBundle("worker");
            type = bundle4.getString("name");
            workername.setText(type);
            bundle5 = bundle.getBundle("category");
            category = bundle5.getString("type");
            bundle2=bundle.getBundle("u");
            url =bundle2.getString("url");

            sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
            sharedPreferenceUtils.setString(vrundainfo.this,"name",type);
            sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
            sharedPreferenceUtils.setString(vrundainfo.this,"category",category);


            // databaseReference = FirebaseDatabase.getInstance().getReference("worker").child(category).child(type).child("assignments");
            databaseReference = FirebaseDatabase.getInstance().getReference("worker").child(category).child(type);
            databaseReference3=FirebaseDatabase.getInstance().getReference("info");
            databaseReference4=FirebaseDatabase.getInstance().getReference("assign");
            complain = getIntent().getExtras();

            databaseReference.child("wemail").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getValue(String.class);
                    workeremail.setText(email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            databaseReference.child("wpno").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String no = dataSnapshot.getValue(Long.class).toString();
                    workerpno.setText(no);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

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
                            list1.add(listdata);
                            mAdapter.notifyDataSetChanged();
                        }

                        RecyclerviewAdapter1 recycler = new RecyclerviewAdapter1(list1, vrundainfo.this.getApplicationContext());
                        Integer string = mAdapter.getItemCount();
                        String s =Integer.toString(string);
                        workern.setText(s);

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

         /*   databaseReference.child("no").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    i = dataSnapshot.getValue(Long.class).intValue();
                    n=i.toString();
                    workern.setText(n);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/

            if (complain != null) {
                list = new ArrayList<String>();
                bundle3 = complain.getBundle("complain");
                list = bundle3.getStringArrayList("complaininfo");
                uid.setText(list.get(0));
                vcategory.setText(category);
                cid.setText(list.get(1));
                vst.setText(list.get(8));
                vname.setText(list.get(2));
                vaddress.setText(list.get(5));
                vemail.setText(list.get(3));
                vpno.setText(list.get(4));
                vdate.setText(list.get(7));
                vcomplain.setText(list.get(6));
                name = vname.getText().toString();
                email = vemail.getText().toString();
                phno = vpno.getText().toString();
                address = vaddress.getText().toString();
                issueddate = vdate.getText().toString();
                assigneddt = vdt.getText().toString();
                userid = uid.getText().toString();
                complainid = cid.getText().toString();
                description = vcomplain.getText().toString();
               // daystocomplete = subs.toString();

               // Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
              //  String uri = "https://www.google.com/maps/dir/?api=1&"+mapUri;
              String uri= "https://www.google.com/maps/dir/Current+Location/" +address;
                //days.setText(uri);

                databaseReference2 = FirebaseDatabase.getInstance().getReference("complains").child(userid).child(complainid).child("status");
                if (category.equals("WaterSupply")) {
                    databaseReference1 = FirebaseDatabase.getInstance().getReference("water").child(complainid).child("status");
                } else if (category.equals("StrayAnimal")) {
                    databaseReference1 = FirebaseDatabase.getInstance().getReference("animal").child(complainid).child("status");
                } else if (category.equals("Drainage")) {
                    databaseReference1 = FirebaseDatabase.getInstance().getReference("drainage").child(complainid).child("status");
                } else if (category.equals("StreetLight")) {
                    databaseReference1 = FirebaseDatabase.getInstance().getReference("street").child(complainid).child("status");
                }



                msgtxt = "Uid:" + userid + "\nCid:" + complainid + "\nName:" + name + "\nEmail:" + email + "\nArea:" + uri +
                        "\nComplain:" + description + "\nAssigned Date:" + assigneddt + "\nPno:" + phno + "\nIssused date:" +
                        issueddate + "\nNo of days:" + subs +"\nUrl:"+ url;
            }
        }



        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(vrundainfo.this, assignments.class);
                startActivity(in);
              //  Intent intent = new Intent(vrundainfo.this, assignments.class);
               // startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);


            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = complainid.toString();
                if (flag==0){
                tos = workeremail.getText().toString();
                subs = days.getText().toString();
                databaseReference.child("assignments").child(id).child("email").setValue(email);
                databaseReference.child("assignments").child(id).child("complain").setValue(description);
                databaseReference.child("assignments").child(id).child("category").setValue(category);
                databaseReference.child("assignments").child(id).child("assigned_date").setValue(assigneddt);
                databaseReference.child("assignments").child(id).child("name").setValue(name);
                databaseReference.child("assignments").child(id).child("area").setValue(address);
                databaseReference.child("assignments").child(id).child("pno").setValue(phno);
                databaseReference.child("assignments").child(id).child("uid").setValue(userid);
                databaseReference.child("assignments").child(id).child("complainid").setValue(complainid);
                databaseReference.child("assignments").child(id).child("issued_date").setValue(issueddate);
                databaseReference.child("assignments").child(id).child("no_of_days").setValue(subs);
                databaseReference.child("assignments").child(id).child("status").setValue("In Process");
                databaseReference1.setValue("In Process");
                databaseReference2.setValue("In Process");
                databaseReference3.child(complainid).child("worker").setValue(type);
                databaseReference4.child(complainid).child("email").setValue(email);
                databaseReference4.child(complainid).child("complain").setValue(description);
                databaseReference4.child(complainid).child("category").setValue(category);
                databaseReference4.child(complainid).child("assigned_date").setValue(assigneddt);
                databaseReference4.child(complainid).child("name").setValue(name);
                databaseReference4.child(complainid).child("area").setValue(address);
                databaseReference4.child(complainid).child("pno").setValue(phno);
                databaseReference4.child(complainid).child("uid").setValue(userid);
                databaseReference4.child(complainid).child("no_of_days").setValue(subs);
                databaseReference4.child(complainid).child("issued_date").setValue(issueddate);
                databaseReference4.child(complainid).child("remark").setValue(" ");
                databaseReference4.child(complainid).child("status").setValue("In Process");
//                i = i + 1;
  //              databaseReference.child("no").setValue(i);

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{tos});
                // email.putExtra(Intent.EXTRA_SU BJECT, subs);
                email.putExtra(Intent.EXTRA_TEXT, msgtxt);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose app to send"));
                flag=1;
                //  adapter.notifyDataSetChanged();
               bundle.clear();
                complain.clear();
                list.clear();
                bundle4.remove("name");
                bundle5.remove("type");
                bundle.remove("worker");
                bundle.remove("category");
                complain.remove("complain");
            }
            else {
                    Toast.makeText(vrundainfo.this,"Already assigned.",Toast.LENGTH_SHORT).show();


                }

            }
        });

    }



    public void onBackPressed() {
        Intent intent = new Intent(this.getApplicationContext(), adminmain.class);
        bundle.clear();
        complain.clear();
        list.clear();
        bundle4.remove("name");
        bundle5.remove("type");
        bundle.remove("worker");
        bundle.remove("category");
        bundle.remove("u");
        complain.remove("complain");
//        adapter.notifyDataSetChanged();
        finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // This method is called when the second activity finishes
 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setContentView(R.layout.activity_vrundainfo);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    //int sss= getIntent().getIntExtra("FocusScore", -1);
                    Bundle extras = data.getExtras();
                   // int sss = extras.getInt("FocusScore");

                    // get String data from Intent
                    int returnString = extras.getInt("keyName");
                    String s =Integer.toString(returnString);
                    // set text view with string
                   TextView workern = (TextView) findViewById(R.id.wn);
                    workern.setText(s);
                }
            }
        }
    }*/
}

