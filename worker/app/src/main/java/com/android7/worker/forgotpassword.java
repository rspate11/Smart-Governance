package com.android7.worker;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class forgotpassword extends Fragment implements
        OnClickListener {
    private static View view;

    private static EditText emailId;
    private static TextView submit;

    private static Animation shakeAnimation;
    private static LinearLayout loginLayout;
    private FirebaseAuth mAuth;

    private static FragmentManager fragmentManager;
    public forgotpassword() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgotpassword, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = (EditText) view.findViewById(R.id.registered_emailid);
        submit = (TextView) view.findViewById(R.id.forgot_button);
        //back = (TextView) view.findViewById(R.id.backToLoginBtn);
        mAuth = FirebaseAuth.getInstance();
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        fragmentManager = getActivity().getSupportFragmentManager();

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

    }

    // Set Listeners over buttons
    private void setListeners() {
        // back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.forgot_button:
                hideKeyboard(getActivity());
                // Call Submit button task
                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);


          //  new CustomToast().Show_Toast(getActivity(), view,
                  //  "Please enter your Email Id.");
        }

        else
            mAuth.sendPasswordResetEmail(getEmailId)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
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