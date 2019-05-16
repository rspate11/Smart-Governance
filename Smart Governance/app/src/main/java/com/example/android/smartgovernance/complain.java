package com.example.android.smartgovernance;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class complain  extends AppCompatActivity{



    android.support.v7.widget.Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    viewPagerAdapter adapter;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        getLayoutInflater().inflate(R.layout.activity_complain, frameLayout);
        setTitle("Smart Governance");

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);


        adapter=new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new departments(),"Department Info");
        adapter.addFragments(new complainForm(),"Complain Form");
        adapter.addFragments(new status(),"Status");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    public void onBackPressed() {
        Intent in = new Intent(this,NavigationActivity.class);
        startActivity(in);
    }


}


