package com.example.android.smartgovernance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea,st;
    ScrollView scrollView;
    Firebase reference1, reference2;
    TextView id,name,re;
    Bundle bundle,bundle1,bundle2,bundle3,bundle4;
    String wname,cid,status,cat2,uid;
    Date date;
    SimpleDateFormat simpleDateFormat;
    String DateTostr;
    SharedPreferenceUtils sharedPreferenceUtils;
    Button up;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3,databaseReference4;
    private FirebaseAnalytics mFirebaseAnalytics;
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        up= (Button)findViewById(R.id.up);
        id=(TextView) findViewById(R.id.id);
        re=(TextView) findViewById(R.id.re);
        st=(EditText) findViewById(R.id.st);
        name = (TextView)findViewById(R.id.name);
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm a");
        UserDetails.msgtime = DateFormat.getDateTimeInstance().format(date).toString();

        sharedPreferenceUtils =SharedPreferenceUtils.getInstance();
        cat2 = sharedPreferenceUtils.getString(Chat.this,"cat2");
        uid = sharedPreferenceUtils.getString(Chat.this,"uid");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setMinimumSessionDuration(20000);


        bundle = getIntent().getExtras();

        if (bundle != null  ) {
            bundle1 = bundle.getBundle("id");
            bundle2 = bundle.getBundle("s");
            bundle3 = bundle.getBundle("n");
            bundle4=bundle.getBundle("c");
            wname= bundle1.getString("wn");
            cid= bundle1.getString("cid");
            status= bundle1.getString("st");
            cat2=bundle1.getString("cat");
            id.setText(cid);
            st.setText(status);
            name.setText(wname);
            UserDetails.chatWith=bundle1.getString("wn");

        }
        UserDetails.username="Admin";



        databaseReference =  FirebaseDatabase.getInstance().getReference("complains").child(uid).child(cid).child("status");

        databaseReference3 =  FirebaseDatabase.getInstance().getReference("assign").child(cid);
        if (cat2.equals("WaterSupply")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("water").child(cid);
        } else if (cat2.equals("StrayAnimal")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("animal").child(cid);
        } else if (cat2.equals("Drainage")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("drainage").child(cid);
        } else if (cat2.equals("StreetLight")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("street").child(cid);
        }



        databaseReference3.child("remark").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String remark = dataSnapshot.getValue(String.class);
                re.setText(remark);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://smartgovernance-467b5.firebaseio.com//messages/" + UserDetails.username + "_" + UserDetails.chatWith).child(cid);
        reference2 = new Firebase("https://smartgovernance-467b5.firebaseio.com//messages/" + UserDetails.chatWith + "_" + UserDetails.username).child(cid);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("msgtime",UserDetails.msgtime);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                   // flag=1;
                    messageArea.setText("");

                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String time =map.get("msgtime").toString();

                if(userName.equals(UserDetails.username)){
                    addMessageBox("You"+"("+time+")\n" + message, 1);
                }
                else{
                    addMessageBox(UserDetails.chatWith +"("+time+ ")\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference1=  FirebaseDatabase.getInstance().getReference("worker").child(cat2).child(wname).child("assignments").child(cid);
                String update=st.getText().toString();
                if (update.equals("Complete")) {
                    databaseReference.setValue(update);
                    databaseReference1.removeValue();
                    databaseReference2.removeValue();
                    databaseReference3.child("status").setValue(update);

                    Bundle bundle = new Bundle();
                    String id = "1";
                    String status = "Complete";
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, status);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, cat2);
                    // bundle.putString(FirebaseAnalytics.Param.PRICE, "299.00");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    mFirebaseAnalytics.setUserProperty("Status", "Complete");


                }
                else {
                    databaseReference.setValue(update);
                    databaseReference1.child("status").setValue(update);
                    databaseReference2.child("status").setValue(update);
                    databaseReference3.child("status").setValue(update);

                    Bundle bundle = new Bundle();
                    String id = "1";
                    String status = update.toString();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, status);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, cat2);
                    // bundle.putString(FirebaseAnalytics.Param.PRICE, "299.00");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    mFirebaseAnalytics.setUserProperty("Status", status);


                }
                Toast.makeText(Chat.this,"Status is updated.",Toast.LENGTH_SHORT).show();




            }
        });





    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

 /*   public void onBackPressed() {

     if ((flag==1)){
         String complainid =cid.toString();
         databaseReference4=  FirebaseDatabase.getInstance().getReference("worker").child(cat2).child(wname).child("wemail");

         databaseReference4.addValueEventListener(new ValueEventListener() {

             @Override
             public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                 String email = (String) dataSnapshot.getValue();
                 Toast.makeText(Chat.this,email,Toast.LENGTH_SHORT);
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });


     }
        Intent in = new Intent(Chat.this, adminmain.class);
        startActivity(in);

    }*/

}