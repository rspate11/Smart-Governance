package com.example.android.smartgovernance;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Find extends AppCompatActivity {
    TextView vname , vaddress,vemail,vpno,vurl,vcategory,vdate,vcomplain,uid,vst,vdt,name,vct,vre;
    Spinner spinner;
    EditText cid,st;
    DatabaseReference databaseReference,databaseReference1,databaseReference4,databaseReference2,databaseReference3,databaseReference5;
    String wnm,rid,n,uid1,category;
    Button search,up;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        cid=(EditText) findViewById(R.id.id);
        name=(TextView)findViewById(R.id.name);
        uid=(TextView)findViewById(R.id.uid);
        vst=(TextView)findViewById(R.id.vst);
        vname = (TextView)findViewById(R.id.vname);
        vemail = (TextView)findViewById(R.id.vemail);
        vaddress = (TextView)findViewById(R.id.vaddress);
        vpno = (TextView)findViewById(R.id.vpno);
        vcomplain= (TextView)findViewById(R.id.vcomplain);
        vdt= (TextView)findViewById(R.id.vdt);
        vdate = (TextView)findViewById(R.id.vdate);
        vct= (TextView)findViewById(R.id.vct);
        vre = (TextView)findViewById(R.id.vre);
        search = (Button) findViewById(R.id.search);
        st=(EditText) findViewById(R.id.st);
        up= (Button)findViewById(R.id.up);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setMinimumSessionDuration(20000);

        ;



        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                hideKeyboard(Find.this);
                rid=cid.getText().toString();
                databaseReference1 = FirebaseDatabase.getInstance().getReference("info").child(rid);
                databaseReference = FirebaseDatabase.getInstance().getReference("assign").child(rid);
                databaseReference1.child("worker").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         n = dataSnapshot.getValue(String.class);
                        name.setText(n);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        {
                           // for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                AssDetails userdetails = dataSnapshot.getValue(AssDetails.class);
                                String name1 = userdetails.getName();
                                String email = userdetails.getEmail();
                                String area = userdetails.getArea();
                                String issued_date = userdetails.getIssued_date();
                                 category = userdetails.getCategory();
                                String complain = userdetails.getComplain();
                                String pno = userdetails.getPno();
                                String assigned_date = userdetails.getAssigned_date();
                                uid1 = userdetails.getUid();
                                String remark= userdetails.getRemark();
                                String status = userdetails.getStatus();
                                uid.setText(uid1);
                                vct.setText(category);
                                vst.setText(status);
                                st.setText(status);
                                vname.setText(name1);
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




        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rid.equals(null)) {
                    hideKeyboard(Find.this);
                    String w =name.getText().toString();
                    databaseReference4 = FirebaseDatabase.getInstance().getReference("assign").child(rid).child("status");
                    databaseReference5 = FirebaseDatabase.getInstance().getReference("complains").child(uid1).child(rid).child("status");
                    databaseReference2 = FirebaseDatabase.getInstance().getReference("worker").child(category).child(w).child("assignments").child(rid);
                    if (category.equals("WaterSupply")) {
                        databaseReference3 = FirebaseDatabase.getInstance().getReference("water").child(rid);
                    } else if (category.equals("StrayAnimal")) {
                        databaseReference3 = FirebaseDatabase.getInstance().getReference("animal").child(rid);
                    } else if (category.equals("Drainage")) {
                        databaseReference3 = FirebaseDatabase.getInstance().getReference("drainage").child(rid);
                    } else if (category.equals("StreetLight")) {
                        databaseReference3 = FirebaseDatabase.getInstance().getReference("street").child(rid);
                    }



                    String update = st.getText().toString();

                    if (w.equals("Complete")) {
                    databaseReference5.setValue(update);
                    databaseReference4.setValue(update);
                    databaseReference2.removeValue();
                    databaseReference3.removeValue();
                    Bundle bundle = new Bundle();
                    String id = "1";
                    String status = "Complete";
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, status);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category);
                    // bundle.putString(FirebaseAnalytics.Param.PRICE, "299.00");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        mFirebaseAnalytics.setUserProperty("Status", "Complete");
                    }
                    else {
                        databaseReference5.setValue(update);
                        databaseReference4.setValue(update);
                        databaseReference2.child("status").setValue(update);
                        databaseReference3.child("status").setValue(update);
                        Bundle bundle = new Bundle();
                        String id = "1";
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, w);
                        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category);
                        // bundle.putString(FirebaseAnalytics.Param.PRICE, "299.00");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        mFirebaseAnalytics.setUserProperty("Status", w);



                    }
                    Toast.makeText(Find.this, "Status is updated.", Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(Find.this, "Enter complain id.", Toast.LENGTH_SHORT).show();
                }

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