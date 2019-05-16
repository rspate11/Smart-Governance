package com.android7.worker;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    Button up,nav;
    EditText messageArea,st,re,area;
    ScrollView scrollView;
    Firebase reference1, reference2;
    TextView id,name;
    Bundle bundle,bundle1,bundle2,bundle3;
    String wname,cid,status,name2,cat2,uid,remark,area1;
    Date date;
    SimpleDateFormat simpleDateFormat;
    String DateTostr;
    SharedPreferenceUtils sharedPreferenceUtils;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        up = (Button) findViewById(R.id.up);
        nav = (Button) findViewById(R.id.nav);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        id=(TextView) findViewById(R.id.id);
        area=(EditText) findViewById(R.id.area);
        re=(EditText) findViewById(R.id.re);;
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm a");
        UserDetails.msgtime = DateFormat.getDateTimeInstance().format(date).toString();

        bundle = getIntent().getExtras();
        sharedPreferenceUtils =SharedPreferenceUtils.getInstance();
         cat2 = sharedPreferenceUtils.getString(Chat.this,"cat");
         name2 = sharedPreferenceUtils.getString(Chat.this,"n");
         uid = sharedPreferenceUtils.getString(Chat.this,"uid");
        hideKeyboard(Chat.this);

        if (bundle != null  ) {
            bundle1 = bundle.getBundle("i");
            bundle2 = bundle.getBundle("a");
            bundle3=bundle.getBundle("r");
            //wname= bundle1.getString("wn");
            cid= bundle1.getString("id");
            remark=bundle3.getString("remark");
            area1= bundle1.getString("area");
            id.setText(cid);
            re.setText(remark);
            area.setText(area1);
//            name.setText(name2);
            UserDetails.chatWith="Admin";

        }



        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address= area.getText().toString();
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(mapIntent);
                }
                catch(ActivityNotFoundException innerEx)
                {
                    Toast.makeText(Chat.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(Chat.this,e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        });



        UserDetails.username= name2;

        //databaseReference =  FirebaseDatabase.getInstance().getReference("complains").child(uid).child(cid).child("status");
       // databaseReference1=  FirebaseDatabase.getInstance().getReference("worker").child(cat2).child(name2).child("assignments").child(cid).child("status");
        databaseReference3 =  FirebaseDatabase.getInstance().getReference("assign").child(cid);

              /*  if (cat2.equals("WaterSupply")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("water").child(cid).child("status");
        } else if (cat2.equals("StrayAnimal")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("animal").child(cid).child("status");
        } else if (cat2.equals("Drainage")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("drainage").child(cid).child("status");
        } else if (cat2.equals("StreetLight")) {
            databaseReference2 = FirebaseDatabase.getInstance().getReference("street").child(cid).child("status");
        }*/


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

                String remark = re.getText().toString();
                hideKeyboard(Chat.this);
                databaseReference3.child("remark").setValue(remark);
                Toast.makeText(Chat.this,"Remark is updated.",Toast.LENGTH_SHORT).show();



            }
        });



      /*  Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i2=new Intent(Chat.this,workerlogin.class);
                i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                sharedPreferenceUtils.logout(Chat.this);
                Toast.makeText(Chat.this, "Logged out...", Toast.LENGTH_SHORT).show();


            }
        });*/


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

    public static void hideKeyboard(Context ctx){
        InputMethodManager inputmanager = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        //check if no view has focus
        View v = ((Activity)ctx).getCurrentFocus();
        if(v==null)
            return;

        inputmanager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }


}
