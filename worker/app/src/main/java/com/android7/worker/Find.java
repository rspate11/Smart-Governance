package com.android7.worker;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.security.AccessController.getContext;

public class Find extends AppCompatActivity{
    TextView vname , vaddress,vemail,vpno,vurl,vcategory,vdate,vcomplain,uid,vst,vdt,name,vre,vct;
    Spinner spinner;
    EditText cid,re;
    ScrollView scrollView;
    DatabaseReference databaseReference,databaseReference1;
    String category,wnm,rid,n;
    Button search,update;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        layout = (LinearLayout)findViewById(R.id.layout1);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        cid=(EditText) findViewById(R.id.id);
        re=(EditText) findViewById(R.id.re);
        uid=(TextView)findViewById(R.id.uid);
        vst=(TextView)findViewById(R.id.vst);
        vname = (TextView)findViewById(R.id.vname);
        vemail = (TextView)findViewById(R.id.vemail);
        vaddress = (TextView)findViewById(R.id.vaddress);
        vpno = (TextView)findViewById(R.id.vpno);
        vcomplain= (TextView)findViewById(R.id.vcomplain);
        vdt= (TextView)findViewById(R.id.vdt);
        vdate = (TextView)findViewById(R.id.vdate);
        vre = (TextView)findViewById(R.id.vre);
        vct = (TextView)findViewById(R.id.vct);
        search = (Button) findViewById(R.id.search);
        update= (Button) findViewById(R.id.update);



        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                rid=cid.getText().toString();
                databaseReference1 = FirebaseDatabase.getInstance().getReference("assign").child(rid);
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        {
                          //  for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                AssDetails userdetails = dataSnapshot.getValue(AssDetails.class);
                                String name = userdetails.getName();
                                String email = userdetails.getEmail();
                                String area = userdetails.getArea();
                                String issued_date = userdetails.getIssued_date();
                                String category = userdetails.getCategory();
                                String complain = userdetails.getComplain();
                                String pno = userdetails.getPno();
                                String assigned_date = userdetails.getAssigned_date();
                                String uid1 = userdetails.getUid();
                                String remark= userdetails.getRemark();
                                String status = userdetails.getStatus();
                                uid.setText(uid1);
                                vct.setText(category);
                                vst.setText(status);
                                vname.setText(name);
                                vaddress.setText(area);
                                vemail.setText(email);
                                vpno.setText(pno);
                                vdate.setText(issued_date);
                                vcomplain.setText(complain);
                                vre.setText(remark);
                                vdt.setText(assigned_date);

                            hideKeyboard(Find.this);


                            //}

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }






        });



        update.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               String remark=re.getText().toString();
               /* TextView textView = new TextView(Find.this);
                textView.setText(remark);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, 0, 10);
                textView.setLayoutParams(lp);*/
                hideKeyboard(Find.this);
                databaseReference1.child("remark").setValue(remark);
                Toast.makeText(Find.this,"Remark is updated.",Toast.LENGTH_SHORT).show();
                //layout.addView(textView);
                //scrollView.fullScroll(View.FOCUS_DOWN);


            }

        });

    }


    public static void hideKeyboard(Context ctx){
        InputMethodManager inputmanager = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        //check if no view has focus
        View v = ((Activity)ctx).getCurrentFocus();
        if(v==null)
            return;

        inputmanager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

}