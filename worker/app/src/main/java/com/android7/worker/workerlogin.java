package com.android7.worker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class workerlogin extends AppCompatActivity implements View.OnClickListener {

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    SharedPreferenceUtils sharedPreferenceUtils;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DatabaseReference databaseReference1;
    Bundle bundle = new Bundle();
    String category,name,id;
  public workerlogin()
    {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workerlogin);
        SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
         id = sharedPreferenceUtils.getString(workerlogin.this, "key");


        initViews();
        setListeners();

    }

    private void initViews() {


        fragmentManager = workerlogin.this.getSupportFragmentManager();

        emailid = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        loginButton = (Button) findViewById(R.id.login1);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
       // signUp = (TextView) findViewById(R.id.login);
        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        shakeAnimation = AnimationUtils.loadAnimation(workerlogin.this,
                R.anim.shake);
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login1:
                hideKeyboard(workerlogin.this);
                checkValidation();


                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.main_container,
                                new forgotpassword(),
                                Utils.forgotpassword).commit();
                break;

        }

    }

    private void checkValidation() {
        // Get email id and password
        final String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            Toast.makeText(workerlogin.this,"Enter correct email id",Toast.LENGTH_SHORT).show();
        } else {



            mAuth.signInWithEmailAndPassword(getEmailId, getPassword)
                    .addOnCompleteListener(workerlogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                String currentUserUid = user.getUid();

                                //  String id= sharedPreferenceUtils.getString(getActivity(),"key");
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                String  userid = user.getUid();
                                databaseReference1 = FirebaseDatabase.getInstance().getReference("WorkerInfo").child(userid);

                                databaseReference1.child("category").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        category = dataSnapshot.getValue(String.class);

                                       // cat.setText(category);
                                        //  databaseReferencecat =  FirebaseDatabase.getInstance().getReference("worker").child(category);
                                       bundle.putString("category",category);
                                        Toast.makeText(workerlogin.this,"name"+category,Toast.LENGTH_SHORT);
                                        sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                                        sharedPreferenceUtils.setString(workerlogin.this,"cat",category);

                                    }


                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                databaseReference1.child("name").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        name = dataSnapshot.getValue(String.class);
                                        bundle.putString("name",name);
                                        sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                                        sharedPreferenceUtils.setString(workerlogin.this,"name",name);
                                        //   uid.setText(name);
                                        //  databaseReferencename = databaseReferencecat.child(name);
                                        Toast.makeText(workerlogin.this,"name"+name,Toast.LENGTH_SHORT);


                                    }


                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                   Intent in = new Intent(workerlogin.this, assignments.class);
                                    in.putExtra("login",bundle);
                                //Toast.makeText(workerlogin.this,"name"+name,Toast.LENGTH_SHORT).show();
                                  in.putExtra("n",bundle);
                                    startActivity(in);

                                sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                                sharedPreferenceUtils.setString(workerlogin.this,"key",userid);
                                //Toast.makeText(workerlogin.this,"name"+name,Toast.LENGTH_SHORT).show();


                              /*  DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                              Query applesQuery = ref.child("worker").child(category).child(name).child("assignments").orderByChild("status").equalTo("Complete");

                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e(TAG, "onCancelled", databaseError.toException());
                                    }
                                });*/


                            }

                                // }
                                 else {

                                Toast.makeText(workerlogin.this,"Error",Toast.LENGTH_SHORT).show();
                                }

                                emailid.setText("");
                                password.setText("");


                            }

                            // ...

                    });


        }
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
