package com.example.android.smartgovernance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class login extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    DatabaseReference databaseReference4;
    String e;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;



    public login() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
        String id = sharedPreferenceUtils.getString(getActivity(), "key");
        String id1 =sharedPreferenceUtils.getString(getActivity(), "key1");

        if(id != null){
            Toast.makeText(getActivity(),"You have already logged in.",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity(), complain.class);
            startActivity(i);

        }
        if(id1 != null){
            Toast.makeText(getActivity(),"You have already logged in.",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity(), adminmain.class);
            startActivity(i);

        }

        view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {


        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.editText);
        password = (EditText) view.findViewById(R.id.editText2);
        loginButton = (Button) view.findViewById(R.id.login1);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.login);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
    }


    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change

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
                hideKeyboard(getActivity());
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
            case R.id.login:

                Intent in = new Intent(getActivity(),signup.class);
                startActivity(in);
                break;
        }

    }

    // Check Validation before login
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
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        } else {

            mAuth.signInWithEmailAndPassword(getEmailId, getPassword)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String currentUserUid = user.getUid().toString();

                                SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();


                                Log.d(currentUserUid, " ");

                             //  String id= sharedPreferenceUtils.getString(getActivity(),"key");


                             //  if(id.equals(null)) {

                                    if (getEmailId.equals("admin@gmail.com")) {
                                        sharedPreferenceUtils.setString(getActivity(),"key1",currentUserUid);
                                        Intent in1 = new Intent(getActivity(), adminmain.class);


                     /*                   DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                        Query applesQuery = ref.child("water").orderByChild("status").equalTo("Complete");

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
                                        });


                                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                        Query applesQuery1 = ref1.child("animal").orderByChild("status").equalTo("Complete");

                                        applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot1) {
                                                for (DataSnapshot appleSnapshot1: dataSnapshot1.getChildren()) {
                                                    appleSnapshot1.getRef().removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e(TAG, "onCancelled", databaseError.toException());
                                            }
                                        });


                                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
                                        Query applesQuery2= ref2.child("street").orderByChild("status").equalTo("Complete");

                                        applesQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                                for (DataSnapshot appleSnapshot2: dataSnapshot2.getChildren()) {
                                                    appleSnapshot2.getRef().removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e(TAG, "onCancelled", databaseError.toException());
                                            }
                                        });


                                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference();
                                        Query applesQuery3= ref3.child("drainage").orderByChild("status").equalTo("Complete");

                                        applesQuery3.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot3) {
                                                for (DataSnapshot appleSnapshot3: dataSnapshot3.getChildren()) {
                                                    appleSnapshot3.getRef().removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e(TAG, "onCancelled", databaseError.toException());
                                            }
                                        });*/
                                        startActivity(in1);


                                    } else {


                                        sharedPreferenceUtils.setString(getActivity(),"key",currentUserUid);
                                        Intent in = new Intent(getActivity(), NavigationActivity.class);
                                        startActivity(in);
                                    }

                              // }
                             //  else {

                                   // new CustomToast().Show_Toast(getActivity(), view,
                                     //       "You are already logged in");

                               // }

                                emailid.setText("");
                                password.setText("");
                                //  updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                new CustomToast().Show_Toast(getActivity(), view,
                                        "No internet connection.");
                                //  updateUI(null);
                            }

                            // ...
                        }
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
