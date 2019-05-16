package com.example.android.smartgovernance;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    private Button btnSignIn, btnSignUp, btnResetPassword;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;
    Firebase mRootref;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2,databaseReference3;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    UserInfo info;
    String uid;

    String namestr;
    String areastr;
    String pnostr;
    String emailstr;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    private EditText inputEmail, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Smart Governance");
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid= user.getUid();
        databaseReference2= FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("signup");
//        mRootref = new Firebase("https://smartgovernance-467b5.firebaseio.com/signup");
        btnSignUp = (Button) findViewById(R.id.su);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EditText name = (EditText) findViewById(R.id.name);
                EditText email = (EditText) findViewById(R.id.email);
                EditText pno = (EditText)findViewById(R.id.pno);
                EditText add = (EditText) findViewById(R.id.add);
                EditText pass1 = (EditText) findViewById(R.id.pass);
                EditText pass2 = (EditText) findViewById(R.id.cpass);



                namestr = name.getText().toString();
                areastr = add.getText().toString();
                pnostr = pno.getText().toString();
                emailstr = email.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String id = user.getUid().toString();
                final String pass1str = pass1.getText().toString();
                String pass2str = pass2.getText().toString();

                // String id = databaseReference1.push().getKey();

                if (TextUtils.isEmpty(emailstr)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

               else if (TextUtils.isEmpty(pass1str)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (pass1str.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {


                    auth.createUserWithEmailAndPassword(emailstr, pass1str)
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(signup.this, "Signed up successfully" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(signup.this, "Authentication failed",
                                                Toast.LENGTH_SHORT).show();
                                        // verify();
                                    } else {
                                        Toast.makeText(signup.this, "Authentication successful",
                                                Toast.LENGTH_SHORT).show();
                                        mAuth = FirebaseAuth.getInstance();
                                        FirebaseUser user2 = mAuth.getCurrentUser();
                                        String id2 = user2.getUid().toString();


                                        databaseReference1.child(id2).child("UserId").setValue(id2);
                                        databaseReference1.child(id2).child("Name").setValue(namestr);
                                        databaseReference1.child(id2).child("Address").setValue(areastr);
                                        databaseReference1.child(id2).child("Pno").setValue(pnostr);
                                        databaseReference1.child(id2).child("Email").setValue(emailstr);
                                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.main_container1, new login());
                                        fragmentTransaction.commit();
                                        finish();
                                    }
                                }
                            });
                }


            }
        });
    }

}
