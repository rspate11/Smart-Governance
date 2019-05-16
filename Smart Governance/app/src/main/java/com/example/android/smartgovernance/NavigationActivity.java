package com.example.android.smartgovernance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
     Toolbar toolbar;
     Context context;
     ActionBarDrawerToggle actionBarDrawerToggle;
     FragmentTransaction fragmentTransaction;
     NavigationView navigationView;
     FragmentManager fragmentManager;
     View navHeader;
     TextView name, website;
    DatabaseReference databaseReference;
    FirebaseUser user;
    UserInfo info;
    String uid;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        fragmentManager=getSupportFragmentManager();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new Home());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Smart Governance");
        LayoutInflater inflater=getLayoutInflater();

        navHeader = inflater.inflate(R.layout.nav_header_main,null, false);

         name = (TextView) findViewById(R.id.name);
        website = (TextView)findViewById(R.id.website);
     //   user= FirebaseAuth.getInstance().getCurrentUser();
     //   uid= user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("signup");

      /*  String key1;
        SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
        key1 = sharedPreferenceUtils.getString(NavigationActivity.this, "key");*/
       // if(key1.equals(null))

           // txtName.setText("riddhi");




           // txtName.setText("vrunda");

       // loadNavHeader();



        navigationView=(NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                switch (item.getItemId()) {
                    case R.id.home_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Home());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();


                        break;

                    case R.id.login:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new login());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.dept:

                        //startActivity(new Intent(getApplicationContext(), complain.class));

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new BlankFragment());
                        fragmentTransaction.commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout:


                           Intent i2=new Intent(NavigationActivity.this,NavigationActivity.class);
                           i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                           SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
                            sharedPreferenceUtils.logout(NavigationActivity.this);
                            startActivity(i2);
                            Toast.makeText(NavigationActivity.this, "Logged out...", Toast.LENGTH_SHORT).show();



                        break;




                }
return true;

            }
        });


    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            NavigationActivity.this.finish();
        }
    }

    void loadNavHeader() {

        mAuth = FirebaseAuth.getInstance();
        name.setText(" ");
        // String name=user.getEmail();

    }


    @Override
    public void onPostCreate( Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


}
