package com.example.android.smartgovernance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private List<Main> mList = new ArrayList<>();
    private List<String> List = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new Adapter(mList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));




         Main m = new Main("Dr. Vinod R. Das", "Municipal Commissioner", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

         m = new Main("Shri P.J Aundhiya", "Dy.Muni.Commissioner(Health)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

         m = new Main("Shri Jitesh R.Trivedi", "Deputy Ex.Engineer (Streetlights)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

         m = new Main("Shri Kaushik Parmar", "Deputy Ex.Engineer (Watersupply)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);


        m = new Main("Shri P.J Aundhiya", "Dy.Muni.Commissioner(Health)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

        m = new Main("Shri Jitesh R.Trivedi", "Deputy Ex.Engineer (Streetlights)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

        m = new Main("Shri Kaushik Parmar", "Deputy Ex.Engineer (Watersupply)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);


        m = new Main("Shri P.J Aundhiya", "Dy.Muni.Commissioner(Health)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

        m = new Main("Shri Jitesh R.Trivedi", "Deputy Ex.Engineer (Streetlights)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

        m = new Main("Shri Kaushik Parmar", "Deputy Ex.Engineer (Watersupply)", "9725200509", "78vrundapatel@gmail.com" );
        mList.add(m);

        mAdapter.notifyDataSetChanged();


    }



}


